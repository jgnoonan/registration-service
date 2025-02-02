package org.signal.registration;

import com.google.cloud.firestore.Firestore;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import org.mockito.Mockito;

@Factory
@Requires(env = "test")
public class TestFirestoreConfiguration {

    @Singleton
    @Primary
    Firestore firestore() {
        return Mockito.mock(Firestore.class);
    }
}
