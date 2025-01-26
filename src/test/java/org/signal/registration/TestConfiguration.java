package org.signal.registration;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Replaces;
import jakarta.inject.Named;
import org.signal.registration.ratelimit.RateLimiter;
import org.signal.registration.ratelimit.AllowAllRateLimiter;
import org.signal.registration.ratelimit.SendSmsVerificationCodeRateLimiter;
import org.signal.registration.ratelimit.SendVoiceVerificationCodeRateLimiter;
import org.signal.registration.ratelimit.CheckVerificationCodeRateLimiter;
import org.signal.registration.session.RegistrationSession;

import java.time.Clock;

@Factory
public class TestConfiguration {

    @Bean
    @Replaces(bean = SendSmsVerificationCodeRateLimiter.class)
    @Named("send-sms-verification-code")
    RateLimiter<RegistrationSession> sendSmsVerificationCodeRateLimiter(Clock clock) {
        return new AllowAllRateLimiter<>(clock);
    }

    @Bean
    @Replaces(bean = SendVoiceVerificationCodeRateLimiter.class)
    @Named("send-voice-verification-code")
    RateLimiter<RegistrationSession> sendVoiceVerificationCodeRateLimiter(Clock clock) {
        return new AllowAllRateLimiter<>(clock);
    }

    @Bean
    @Replaces(bean = CheckVerificationCodeRateLimiter.class)
    @Named("check-verification-code")
    RateLimiter<RegistrationSession> checkVerificationCodeRateLimiter(Clock clock) {
        return new AllowAllRateLimiter<>(clock);
    }
}
