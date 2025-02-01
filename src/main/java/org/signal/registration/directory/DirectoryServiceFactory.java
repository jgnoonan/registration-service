package org.signal.registration.directory;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.signal.registration.directory.entra.EntraIdConfiguration;
import org.signal.registration.directory.entra.EntraIdDirectoryService;
import org.signal.registration.ldap.LdapConfiguration;
import org.signal.registration.ldap.LdapService;
import org.signal.registration.directory.DirectoryService;
import org.signal.registration.directory.DirectoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

/**
 * Factory for creating the appropriate DirectoryService implementation
 */
@jakarta.inject.Singleton
public class DirectoryServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryServiceFactory.class);

    private final LdapConfiguration ldapConfig;
    private final EntraIdConfiguration entraConfig;
    private final DirectoryType directoryType;

    public DirectoryServiceFactory(LdapConfiguration ldapConfig, 
                                 EntraIdConfiguration entraConfig,
                                 DirectoryType directoryType) {
        this.ldapConfig = ldapConfig;
        this.entraConfig = entraConfig;
        this.directoryType = directoryType;
    }

    /**
     * Creates and returns the appropriate DirectoryService implementation based on configuration.
     *
     * @return the configured DirectoryService implementation
     */
    public DirectoryService createDirectoryService() {
        switch (directoryType) {
            case ENTRA_ID:
                logger.info("Using Microsoft Entra ID directory service");
                return new EntraIdDirectoryService(entraConfig);
            case LDAP:
                logger.info("Using LDAP directory service");
                return new LdapService(ldapConfig);
            case NONE:
            default:
                logger.info("No directory service is enabled");
                return new NoOpDirectoryService();
        }
    }

    /**
     * A no-op implementation of DirectoryService for when no directory service is enabled.
     */
    private static class NoOpDirectoryService implements DirectoryService {
        @Override
        public void initialize() {
            logger.info("Using no-op directory service");
        }

        @Override
        public Optional<String> authenticateAndGetPhoneNumber(String userId, String password) {
            return Optional.empty();
        }

        @Override
        public void cleanup() {
            // No-op
        }
    }
}
