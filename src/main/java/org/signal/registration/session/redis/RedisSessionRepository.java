/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.session.redis;

import com.google.common.annotations.VisibleForTesting;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import org.signal.registration.sender.VerificationCodeSender;
import org.signal.registration.session.RegistrationSession;
import org.signal.registration.session.SessionNotFoundException;
import org.signal.registration.session.SessionRepository;

/**
 * A session repository that stores session data in a single (i.e. non-clustered) Redis instance.
 */
@Singleton
class RedisSessionRepository implements SessionRepository {

  private final StatefulRedisConnection<byte[], byte[]> redisConnection;

  private final Map<String, VerificationCodeSender> sendersByCanonicalName;

  private final String createSessionScriptSha;
  private final String setVerifiedCodeScript;

  private static final String KEY_E164 = "e164";
  private static final String KEY_SESSION_DATA = "session-data";
  private static final String KEY_VERIFIED_CODE = "verified-code";

  @VisibleForTesting
  static final String KEY_SENDER_CLASS = "sender";

  RedisSessionRepository(final StatefulRedisConnection<byte[], byte[]> redisConnection,
      final List<VerificationCodeSender> senders) throws IOException {

    this.redisConnection = redisConnection;

    this.sendersByCanonicalName = senders.stream()
        .collect(Collectors.toMap(sender -> sender.getClass().getCanonicalName(), sender -> sender));

    try (final InputStream scriptInputStream = Objects.requireNonNull(getClass().getResourceAsStream("create-session.lua"))) {
      this.createSessionScriptSha = redisConnection.sync().scriptLoad(scriptInputStream.readAllBytes());
    }

    try (final InputStream scriptInputStream = Objects.requireNonNull(getClass().getResourceAsStream("set-verified-code.lua"))) {
      this.setVerifiedCodeScript = redisConnection.sync().scriptLoad(scriptInputStream.readAllBytes());
    }
  }

  @Override
  public CompletableFuture<UUID> createSession(final Phonenumber.PhoneNumber phoneNumber,
      final VerificationCodeSender sender,
      final Duration ttl,
      final byte[] sessionData) {

    final UUID sessionId = UUID.randomUUID();

    return redisConnection.async().evalsha(createSessionScriptSha, ScriptOutputType.VALUE,
            new byte[][] { getSessionKey(sessionId) },
            PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164).getBytes(StandardCharsets.UTF_8),
            sender.getClass().getCanonicalName().getBytes(StandardCharsets.UTF_8),
            sessionData,
            String.valueOf(ttl.getSeconds()).getBytes(StandardCharsets.UTF_8))
        .thenApply(ignored -> sessionId)
        .toCompletableFuture();
  }

  @Override
  public CompletableFuture<RegistrationSession> getSession(final UUID sessionId) {
    return redisConnection.async().hgetall(getSessionKey(sessionId))
        .thenApply(rawSessionMap -> {
          if (rawSessionMap != null && !rawSessionMap.isEmpty()) {
            final Map<String, byte[]> sessionMap = rawSessionMap.entrySet().stream()
                .collect(Collectors.toMap(entry -> new String(entry.getKey(), StandardCharsets.UTF_8),
                    Map.Entry::getValue));

            final String senderCanonicalName = new String(sessionMap.get(KEY_SENDER_CLASS), StandardCharsets.UTF_8);
            final VerificationCodeSender sender = sendersByCanonicalName.get(senderCanonicalName);

            if (sender == null) {
              throw new IllegalArgumentException("Could not find a verification code sender with class " + senderCanonicalName);
            }

            try {
              final String e164String = new String(sessionMap.get(KEY_E164), StandardCharsets.UTF_8);
              final String verifiedCode = sessionMap.containsKey(KEY_VERIFIED_CODE) ?
                  new String(sessionMap.get(KEY_VERIFIED_CODE), StandardCharsets.UTF_8) : null;

              return new RegistrationSession(PhoneNumberUtil.getInstance().parse(e164String, null),
                  sender,
                  sessionMap.get(KEY_SESSION_DATA),
                  verifiedCode);
            } catch (final NumberParseException e) {
              throw new IllegalArgumentException("Could not parse previously-stored phone number", e);
            }
          } else {
            throw new CompletionException(new SessionNotFoundException());
          }
        })
        .toCompletableFuture();
  }

  @Override
  public CompletableFuture<Void> setSessionVerified(final UUID sessionId, final String verificationCode) {
    return redisConnection.async().evalsha(setVerifiedCodeScript, ScriptOutputType.BOOLEAN,
            new byte[][] { getSessionKey(sessionId) },
            verificationCode.getBytes(StandardCharsets.UTF_8))
        .thenAccept(sessionFound -> {
          if (!(Boolean) sessionFound) {
            throw new CompletionException(new SessionNotFoundException());
          }
        })
        .toCompletableFuture();
  }

  @VisibleForTesting
  static byte[] getSessionKey(final UUID uuid) {
    return ("registration-session::" + uuid).getBytes(StandardCharsets.UTF_8);
  }
}
