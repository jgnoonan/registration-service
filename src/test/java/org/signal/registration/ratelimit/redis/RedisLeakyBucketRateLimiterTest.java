/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.ratelimit.redis;

import io.lettuce.core.FlushMode;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisException;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.signal.registration.ratelimit.LeakyBucketRateLimiterConfiguration;
import org.signal.registration.ratelimit.RateLimitExceededException;
import org.signal.registration.util.CompletionExceptions;
import redis.embedded.RedisServer;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest(environments = "test", startApplication = false)
class RedisLeakyBucketRateLimiterTest {

  @Inject
  private RedisClient redisClient;

  @Inject
  private RedisLeakyBucketRateLimiter<String> rateLimiter;

  @Inject
  private Clock clock;

  @Inject
  private LeakyBucketRateLimiterConfiguration configuration;

  @Inject
  private SimpleMeterRegistry meterRegistry;

  @MockBean(Clock.class)
  Clock clock() {
    return mock(Clock.class);
  }

  private static final Instant CURRENT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

  private static final int MAX_PERMITS = 2;
  private static final Duration PERMIT_REGENERATION_PERIOD = Duration.ofMinutes(1);
  private static final Duration MIN_DELAY = Duration.ofSeconds(5);

  @BeforeEach
  void setUp() {
    when(clock.instant()).thenReturn(CURRENT_TIME);
    redisClient.connect().sync().flushdb();}

  @AfterEach
  void tearDown() {
    redisClient.shutdown();
  }

  @Test
  void getTimeOfNextAction() {
    assertEquals(Optional.of(CURRENT_TIME), rateLimiter.getTimeOfNextAction("test").join(),
        "Action for an empty bucket should be permitted immediately");

    rateLimiter.checkRateLimit("test").join();

    assertEquals(Optional.of(CURRENT_TIME.plus(MIN_DELAY)), rateLimiter.getTimeOfNextAction("test").join(),
        "Action for a just-used bucket should be permitted after cooldown");

    when(clock.instant()).thenReturn(CURRENT_TIME.plus(MIN_DELAY));

    assertEquals(Optional.of(CURRENT_TIME.plus(MIN_DELAY)), rateLimiter.getTimeOfNextAction("test").join());

    rateLimiter.checkRateLimit("test").join();

    // Allow for some floating point error
    final long deviationFromExpectedMillis =
        Math.abs(rateLimiter.getTimeOfNextAction("test").join().orElseThrow().toEpochMilli() -
            CURRENT_TIME.plus(PERMIT_REGENERATION_PERIOD).toEpochMilli());

    assertTrue(deviationFromExpectedMillis <= 1);
  }

  @Test
  void checkRateLimit() {
    assertDoesNotThrow(() -> rateLimiter.checkRateLimit("test").join(),
        "Checking a rate limit for a fresh key should succeed");

    {
      final CompletionException completionException =
          assertThrows(CompletionException.class, () -> rateLimiter.checkRateLimit("test").join(),
              "Checking a rate limit twice immediately should trigger the cooldown period");

      assertTrue(CompletionExceptions.unwrap(completionException) instanceof RateLimitExceededException);

      final RateLimitExceededException rateLimitExceededException =
          (RateLimitExceededException) CompletionExceptions.unwrap(completionException);

      assertEquals(Optional.of(MIN_DELAY), rateLimitExceededException.getRetryAfterDuration());
    }

    when(clock.instant()).thenReturn(CURRENT_TIME.plus(MIN_DELAY));

    assertDoesNotThrow(() -> rateLimiter.checkRateLimit("test").join(),
        "Checking a rate limit after cooldown has elapsed should succeed");

    when(clock.instant()).thenReturn(CURRENT_TIME.plus(MIN_DELAY.multipliedBy(2)));

    {
      final CompletionException completionException =
          assertThrows(CompletionException.class, () -> rateLimiter.checkRateLimit("test").join(),
              "Checking a rate limit before permits have generated should not succeed");

      assertInstanceOf(RateLimitExceededException.class, CompletionExceptions.unwrap(completionException));

      final RateLimitExceededException rateLimitExceededException =
          (RateLimitExceededException) CompletionExceptions.unwrap(completionException);

      assertTrue(rateLimitExceededException.getRetryAfterDuration().isPresent());

      final Duration retryAfterDuration = rateLimitExceededException.getRetryAfterDuration().get();

      // Allow for some floating point error
      final long deviationFromExpectedMillis =
          Math.abs(retryAfterDuration.toMillis() - PERMIT_REGENERATION_PERIOD.minus(MIN_DELAY.multipliedBy(2)).toMillis());

      assertTrue(deviationFromExpectedMillis <= 1);
    }
  }

  @Test
  void checkRateLimitRedisException() {
    final RedisFuture<Object> failedFuture = mock(RedisFuture.class);
    when(failedFuture.toCompletableFuture()).thenReturn(CompletableFuture.failedFuture(new RedisException("Test")));

    final RedisAsyncCommands<String, String> failureProneCommands = mock(RedisAsyncCommands.class);
    when(failureProneCommands.evalsha(anyString(), any(ScriptOutputType.class), any(String[].class),
        any(String[].class))).thenReturn(failedFuture);

    final StatefulRedisConnection<String, String> failureProneConnection = mock(StatefulRedisConnection.class);
    when(failureProneConnection.async()).thenReturn(failureProneCommands);

    final RedisLeakyBucketRateLimiter<String> failOpenLimiter = new RedisLeakyBucketRateLimiter<>(
        failureProneConnection, clock, configuration, meterRegistry) {
      @Override
      protected String getBucketName(final String key) {
        return "test-bucket:" + key;
      }

      @Override
      protected boolean shouldFailOpen() {
        return true;
      }
    };

    assertDoesNotThrow(() -> failOpenLimiter.checkRateLimit("fail-open").join());

    final RedisLeakyBucketRateLimiter<String> failClosedLimiter = new RedisLeakyBucketRateLimiter<>(
        failureProneConnection, clock, configuration, meterRegistry) {
      @Override
      protected String getBucketName(final String key) {
        return "test-bucket:" + key;
      }

      @Override
      protected boolean shouldFailOpen() {
        return false;
      }
    };

    final CompletionException completionException =
        assertThrows(CompletionException.class, () -> failClosedLimiter.checkRateLimit("fail-closed").join());

    assertTrue(CompletionExceptions.unwrap(completionException) instanceof RateLimitExceededException);
  }
}
