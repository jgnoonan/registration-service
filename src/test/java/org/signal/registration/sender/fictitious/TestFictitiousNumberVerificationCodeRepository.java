/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.sender.fictitious;

import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import com.google.i18n.phonenumbers.Phonenumber;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Primary
@Requires(env = "test")
public class TestFictitiousNumberVerificationCodeRepository implements FictitiousNumberVerificationCodeRepository {
    private final Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    @Override
    public CompletableFuture<Void> storeVerificationCode(final Phonenumber.PhoneNumber phoneNumber,
                                                        final String verificationCode,
                                                        final Duration ttl) {
        verificationCodes.put(phoneNumber.toString(), verificationCode);
        return CompletableFuture.completedFuture(null);
    }
}
