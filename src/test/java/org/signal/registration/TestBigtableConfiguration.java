package org.signal.registration;

import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import org.mockito.Mockito;

@Factory
@Requires(env = "test")
public class TestBigtableConfiguration {

    @Singleton
    BigtableDataClient bigtableDataClient() {
        return Mockito.mock(BigtableDataClient.class);
    }
}
