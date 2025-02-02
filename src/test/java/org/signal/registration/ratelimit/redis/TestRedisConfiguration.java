package org.signal.registration.ratelimit.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import org.signal.registration.ratelimit.LeakyBucketRateLimiterConfiguration;
import org.mockito.Mockito;

import java.time.Clock;
import java.time.Duration;
import java.util.Map;

@Factory
@Requires(env = "test")
public class TestRedisConfiguration implements TestPropertyProvider {

    private final Clock clock = Mockito.mock(Clock.class);

    @Override
    public Map<String, String> getProperties() {
        return Map.of(
            "redis.enabled", "false"
        );
    }

    @Singleton
    @Named("test")
    @Requires(env = "test")
    StatefulRedisConnection<String, String> redisConnection() {
        return Mockito.mock(StatefulRedisConnection.class);
    }

    @Singleton
    Clock clock() {
        return clock;
    }

    @Singleton
    @Primary
    RedisLeakyBucketRateLimiter<String> redisLeakyBucketRateLimiter(
            @Named("test") final StatefulRedisConnection<String, String> connection,
            @Named("test-bucket") final LeakyBucketRateLimiterConfiguration configuration,
            final MeterRegistry meterRegistry) {
        return new RedisLeakyBucketRateLimiter<>(connection, clock, configuration, meterRegistry) {
            @Override
            protected String getBucketName(final String key) {
                return key;
            }

            @Override
            protected boolean shouldFailOpen() {
                return false;
            }
        };
    }
}
