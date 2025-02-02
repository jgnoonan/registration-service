package org.signal.registration.directory;

import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;
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
@Factory
public class DirectoryServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryServiceFactory.class);

    private final DirectoryType directoryType;

    public DirectoryServiceFactory(DirectoryType directoryType) {
        this.directoryType = directoryType;
    }

    /**
     * Creates and returns the appropriate DirectoryService implementation based on configuration.
     *
     * @return the configured DirectoryService implementation
     */
    @Singleton
    public DirectoryService createDirectoryService(Optional<LdapConfiguration> ldapConfig, Optional<EntraIdConfiguration> entraConfig) {
        logger.info("Creating directory service with type: {}", directoryType);
        logger.info("LDAP config present: {}", ldapConfig.isPresent());
        logger.info("Entra ID config present: {}", entraConfig.isPresent());

        switch (directoryType) {
            case ENTRA_ID:
                if (entraConfig.isEmpty()) {
                    logger.warn("Entra ID selected but configuration not available, falling back to NONE");
                    return new NoOpDirectoryService();
                }
                logger.info("Using Microsoft Entra ID directory service");
                EntraIdConfiguration config = entraConfig.get();
                logger.info("Entra ID Configuration - Tenant ID: {}, Client ID: {}, Test User: {}", 
                          config.getTenantId(), config.getClientId(), config.getTestUser());
                return new EntraIdDirectoryService(config);
            case LDAP:
                if (ldapConfig.isEmpty()) {
                    logger.warn("LDAP selected but configuration not available, falling back to NONE");
                    return new NoOpDirectoryService();
                }
                logger.info("Using LDAP directory service");
                return new LdapService(ldapConfig.get());
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
