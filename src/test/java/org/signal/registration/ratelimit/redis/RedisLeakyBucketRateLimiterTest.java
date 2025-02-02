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
import io.micronaut.context.annotation.Parameter;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.signal.registration.ratelimit.LeakyBucketRateLimiterConfiguration;
import org.signal.registration.ratelimit.RateLimitExceededException;
import org.signal.registration.util.CompletionExceptions;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest(environments = "test", startApplication = false)
class RedisLeakyBucketRateLimiterTest {

  private RedisLeakyBucketRateLimiter<String> rateLimiter;

  @Inject
  private Clock clock;

  @Inject
  @Named("test-bucket")
  private LeakyBucketRateLimiterConfiguration configuration;

  @Inject
  @Named("test")
  private StatefulRedisConnection<String, String> redisConnection;

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
    rateLimiter = new RedisLeakyBucketRateLimiter<>(redisConnection, clock, configuration, new SimpleMeterRegistry()) {
      @Override
      protected String getBucketName(String key) {
        return key;
      }

      @Override
      protected boolean shouldFailOpen() {
        return false;
      }
    };
    RedisAsyncCommands<String, String> commands = mock(RedisAsyncCommands.class);
    doAnswer(invocation -> {
      RedisFuture<Object> future = mock(RedisFuture.class);
      when(future.get()).thenReturn(0L);
      when(future.await(anyLong(), any())).thenReturn(true);
      return future;
    }).doAnswer(invocation -> {
      RedisFuture<Object> future = mock(RedisFuture.class);
      when(future.get()).thenReturn(1L);
      when(future.await(anyLong(), any())).thenReturn(true);
      return future;
    }).when(commands).eval(anyString(), any(ScriptOutputType.class), any(), any());
    when(redisConnection.async()).thenReturn(commands);
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
  }

  @Test
  void checkRateLimit() {
    assertDoesNotThrow(() -> rateLimiter.checkRateLimit("test").join());
    assertDoesNotThrow(() -> rateLimiter.checkRateLimit("test").join());

    final CompletionException completionException =
        assertThrows(CompletionException.class, () -> rateLimiter.checkRateLimit("test").join());

    final RateLimitExceededException rateLimitException = (RateLimitExceededException) completionException.getCause();
    assertInstanceOf(RateLimitExceededException.class, rateLimitException);
    assertTrue(rateLimitException.getRetryAfterDuration().isPresent());
    assertEquals(MIN_DELAY, rateLimitException.getRetryAfterDuration().get());
  }

  @Test
  void checkRateLimitRedisException() {
    final StatefulRedisConnection<String, String> failureProneConnection = mock(StatefulRedisConnection.class);
    final RedisAsyncCommands<String, String> failureProneCommands = mock(RedisAsyncCommands.class);

    when(failureProneConnection.async()).thenReturn(failureProneCommands);

    doAnswer(invocation -> {
      RedisFuture<Object> future = mock(RedisFuture.class);
      doAnswer(nested -> {
        throw new ExecutionException(new RedisException("Failed to connect to Redis"));
      }).when(future).get();
      when(future.await(anyLong(), any())).thenReturn(true);
      return future;
    }).when(failureProneCommands).eval(anyString(), any(ScriptOutputType.class), any(), any());

    final RedisLeakyBucketRateLimiter<String> failureProneRateLimiter =
        new RedisLeakyBucketRateLimiter<>(failureProneConnection, clock, configuration, new SimpleMeterRegistry()) {
          @Override
          protected String getBucketName(String key) {
            return key;
          }

          @Override
          protected boolean shouldFailOpen() {
            return false;
          }
        };

    final CompletionException completionException =
        assertThrows(CompletionException.class, () -> failureProneRateLimiter.checkRateLimit("test").join());

    assertTrue(CompletionExceptions.unwrap(completionException) instanceof RedisException);
  }
}
