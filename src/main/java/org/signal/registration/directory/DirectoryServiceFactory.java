package org.signal.registration.directory;

import jakarta.inject.Singleton;
import org.signal.registration.directory.entra.EntraIdConfiguration;
import org.signal.registration.directory.entra.EntraIdDirectoryService;
import org.signal.registration.ldap.LdapConfiguration;
import org.signal.registration.ldap.LdapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * Creates and returns the appropriate DirectoryService implementation based on configuration
     *
     * @return the configured DirectoryService implementation
     */
    public DirectoryService createDirectoryService() {
        if (entraConfig.isEnabled()) {
            logger.info("Using Microsoft Entra ID directory service");
            return new EntraIdDirectoryService(entraConfig);
        }

        logger.info("Using LDAP directory service");
        return new LdapService(ldapConfig);
    }
}
