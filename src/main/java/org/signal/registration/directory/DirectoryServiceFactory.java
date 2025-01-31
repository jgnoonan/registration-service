package org.signal.registration.directory;

import jakarta.inject.Singleton;
import org.signal.registration.directory.entra.EntraIdConfiguration;
import org.signal.registration.directory.entra.EntraIdDirectoryService;
import org.signal.registration.ldap.LdapConfiguration;
import org.signal.registration.ldap.LdapService;
import org.signal.registration.directory.DirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

/**
 * Factory for creating the appropriate DirectoryService implementation
 */
@Singleton
public class DirectoryServiceFactory {
    private static final Logger logger = LoggerFactory.getLogger(DirectoryServiceFactory.class);

    private final LdapConfiguration ldapConfig;
    private final EntraIdConfiguration entraConfig;

    public DirectoryServiceFactory(LdapConfiguration ldapConfig, EntraIdConfiguration entraConfig) {
        this.ldapConfig = ldapConfig;
        this.entraConfig = entraConfig;
    }

    /**
     * Creates and returns the appropriate DirectoryService implementation based on configuration.
     * Only one directory service can be active at a time.
     *
     * @return the configured DirectoryService implementation
     * @throws IllegalStateException if both LDAP and Entra ID are enabled or if no services are enabled
     */
    public DirectoryService createDirectoryService() {
        boolean ldapEnabled = ldapConfig != null && ldapConfig.isEnabled();
        boolean entraEnabled = entraConfig != null && entraConfig.isEnabled();

        if (ldapEnabled && entraEnabled) {
            logger.error("Both LDAP and Microsoft Entra ID are enabled. Only one directory service can be active at a time.");
            throw new IllegalStateException("Both LDAP and Microsoft Entra ID are enabled. Please enable only one directory service.");
        }

        if (entraEnabled) {
            logger.info("Using Microsoft Entra ID directory service");
            return new EntraIdDirectoryService(entraConfig);
        }

        if (ldapEnabled) {
            logger.info("Using LDAP directory service");
            return new LdapService(ldapConfig);
        }

        logger.info("No directory service is enabled");
        return new NoOpDirectoryService();
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
