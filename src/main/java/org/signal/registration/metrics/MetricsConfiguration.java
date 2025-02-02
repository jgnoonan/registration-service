package org.signal.registration.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

@Factory
public class MetricsConfiguration {
    @Singleton
    @Primary
    @Requires(property = "micronaut.metrics.enabled", value = "false")
    public MeterRegistry noopMeterRegistry() {
        return new SimpleMeterRegistry();
    }
}
