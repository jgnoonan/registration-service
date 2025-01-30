package org.signal.registration.ratelimit.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.signal.registration.ratelimit.LeakyBucketRateLimiterConfiguration;
import org.signal.registration.ratelimit.RateLimiter;
import org.signal.registration.session.RegistrationSession;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import java.time.Clock;
import java.time.Duration;

@Factory
@Requires(env = "test")
public class TestRedisConfiguration {

    @Singleton
    RedisClient redisClient() {
        return RedisClient.create(RedisURI.create("localhost", 6379));
    }

    @Singleton
    StatefulRedisConnection<String, String> redisConnection(RedisClient redisClient) {
        return redisClient.connect();
    }

    @Singleton
    MeterRegistry meterRegistry() {
        return new SimpleMeterRegistry();
    }

    @Singleton
    @Named("session-creation")
    LeakyBucketRateLimiterConfiguration sessionCreationConfig() {
        return new LeakyBucketRateLimiterConfiguration(
            "session-creation",
            2,
            Duration.ofMinutes(5),
            Duration.ofSeconds(1)
        );
    }

    @Singleton
    @Named("send-sms-verification-code")
    LeakyBucketRateLimiterConfiguration smsVerificationConfig() {
        return new LeakyBucketRateLimiterConfiguration(
            "send-sms-verification-code",
            2,
            Duration.ofMinutes(5),
            Duration.ofSeconds(1)
        );
    }

    @Singleton
    @Named("send-voice-verification-code")
    LeakyBucketRateLimiterConfiguration voiceVerificationConfig() {
        return new LeakyBucketRateLimiterConfiguration(
            "send-voice-verification-code",
            2,
            Duration.ofMinutes(5),
            Duration.ofSeconds(1)
        );
    }

    @Singleton
    @Named("check-verification-code")
    LeakyBucketRateLimiterConfiguration checkVerificationConfig() {
        return new LeakyBucketRateLimiterConfiguration(
            "check-verification-code",
            2,
            Duration.ofMinutes(5),
            Duration.ofSeconds(1)
        );
    }

    @Singleton
    @Named("session-creation")
    RateLimiter<PhoneNumber> sessionCreationRateLimiter(
            final StatefulRedisConnection<String, String> connection,
            final Clock clock,
            @Named("session-creation") final LeakyBucketRateLimiterConfiguration config,
            final MeterRegistry meterRegistry) {
        return new RedisLeakyBucketRateLimiter<>(connection, clock, config, meterRegistry) {
            @Override
            protected String getBucketName(PhoneNumber key) {
                return "test:session:" + key.getNationalNumber();
            }

            @Override
            protected boolean shouldFailOpen() {
                return false;
            }
        };
    }

    @Singleton
    @Named("send-sms-verification-code")
    RateLimiter<RegistrationSession> sendSmsVerificationCodeRateLimiter(
            final StatefulRedisConnection<String, String> connection,
            final Clock clock,
            @Named("send-sms-verification-code") final LeakyBucketRateLimiterConfiguration config,
            final MeterRegistry meterRegistry) {
        return new RedisLeakyBucketRateLimiter<>(connection, clock, config, meterRegistry) {
            @Override
            protected String getBucketName(RegistrationSession key) {
                return "test:sms:" + key.getPhoneNumber();
            }

            @Override
            protected boolean shouldFailOpen() {
                return false;
            }
        };
    }

    @Singleton
    @Named("send-voice-verification-code")
    RateLimiter<RegistrationSession> sendVoiceVerificationCodeRateLimiter(
            final StatefulRedisConnection<String, String> connection,
            final Clock clock,
            @Named("send-voice-verification-code") final LeakyBucketRateLimiterConfiguration config,
            final MeterRegistry meterRegistry) {
        return new RedisLeakyBucketRateLimiter<>(connection, clock, config, meterRegistry) {
            @Override
            protected String getBucketName(RegistrationSession key) {
                return "test:voice:" + key.getPhoneNumber();
            }

            @Override
            protected boolean shouldFailOpen() {
                return false;
            }
        };
    }

    @Singleton
    @Named("check-verification-code")
    RateLimiter<RegistrationSession> checkVerificationCodeRateLimiter(
            final StatefulRedisConnection<String, String> connection,
            final Clock clock,
            @Named("check-verification-code") final LeakyBucketRateLimiterConfiguration config,
            final MeterRegistry meterRegistry) {
        return new RedisLeakyBucketRateLimiter<>(connection, clock, config, meterRegistry) {
            @Override
            protected String getBucketName(RegistrationSession key) {
                return "test:check:" + key.getPhoneNumber();
            }

            @Override
            protected boolean shouldFailOpen() {
                return false;
            }
        };
    }

    @Singleton
    @Named("test")
    RateLimiter<String> testRateLimiter(
            final StatefulRedisConnection<String, String> connection,
            final Clock clock,
            @Named("session-creation") final LeakyBucketRateLimiterConfiguration config,
            final MeterRegistry meterRegistry) {
        return new RedisLeakyBucketRateLimiter<>(connection, clock, config, meterRegistry) {
            @Override
            protected String getBucketName(String key) {
                return "test:" + key;
            }

            @Override
            protected boolean shouldFailOpen() {
                return false;
            }
        };
    }
}
