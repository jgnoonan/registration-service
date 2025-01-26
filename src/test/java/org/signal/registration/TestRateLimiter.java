package org.signal.registration;

import org.signal.registration.ratelimit.RateLimiter;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class TestRateLimiter<K> implements RateLimiter<K> {
    private final Clock clock;

    public TestRateLimiter(Clock clock) {
        this.clock = clock;
    }

    @Override
    public CompletableFuture<Optional<Instant>> getTimeOfNextAction(K key) {
        return CompletableFuture.completedFuture(Optional.empty());
    }

    @Override
    public CompletableFuture<Void> checkRateLimit(K key) {
        return CompletableFuture.completedFuture(null);
    }
}
