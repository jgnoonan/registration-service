package org.signal.registration.directory;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

@Factory
public class DirectoryTypeConfiguration {
    public static final String ENTRA_ID = "ENTRA_ID";

    @Value("${micronaut.config.registration.directory.type}")
    private String directoryType;

    @Singleton
    public DirectoryType directoryType() {
        return DirectoryType.valueOf(directoryType);
    }
}
