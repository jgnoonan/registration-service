package org.signal.registration;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.signal.registration.ratelimit.RateLimiter;
import org.signal.registration.session.RegistrationSession;

import java.time.Clock;

@Factory
@Requires(env = "test")
public class TestRateLimiterConfiguration {

    private final Clock clock;

    public TestRateLimiterConfiguration(Clock clock) {
        this.clock = clock;
    }

    @Singleton
    @Named("send-sms-verification-code")
    RateLimiter<RegistrationSession> sendSmsVerificationCodeRateLimiter() {
        return new TestRateLimiter<>(clock);
    }

    @Singleton
    @Named("send-voice-verification-code")
    RateLimiter<RegistrationSession> sendVoiceVerificationCodeRateLimiter() {
        return new TestRateLimiter<>(clock);
    }

    @Singleton
    @Named("check-verification-code")
    RateLimiter<RegistrationSession> checkVerificationCodeRateLimiter() {
        return new TestRateLimiter<>(clock);
    }
}
