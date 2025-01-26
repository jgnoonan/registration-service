/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.ratelimit;

import com.google.i18n.phonenumbers.Phonenumber;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.signal.registration.session.RegistrationSession;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Factory
@Requires(env = "test")
public class TestRateLimiters {

    @Singleton
    @Primary
    @Named("session-creation")
    public RateLimiter<Phonenumber.PhoneNumber> sessionCreationRateLimiter() {
        return new NoopRateLimiter<>();
    }

    @Singleton
    @Primary
    @Named("send-sms-verification-code")
    public RateLimiter<RegistrationSession> sendSmsVerificationCodeRateLimiter() {
        return new NoopRateLimiter<>();
    }

    @Singleton
    @Primary
    @Named("send-voice-verification-code")
    public RateLimiter<RegistrationSession> sendVoiceVerificationCodeRateLimiter() {
        return new NoopRateLimiter<>();
    }

    @Singleton
    @Primary
    @Named("check-verification-code")
    public RateLimiter<RegistrationSession> checkVerificationCodeRateLimiter() {
        return new NoopRateLimiter<>();
    }

    private static class NoopRateLimiter<T> implements RateLimiter<T> {
        @Override
        public CompletableFuture<Void> checkRateLimit(final T key) {
            return CompletableFuture.completedFuture(null);
        }

        @Override
        public CompletableFuture<Optional<Instant>> getTimeOfNextAction(T key) {
            return CompletableFuture.completedFuture(Optional.of(Instant.now()));
        }
    }
}
