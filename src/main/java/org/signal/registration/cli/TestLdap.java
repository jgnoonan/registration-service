package org.signal.registration.cli;

import picocli.CommandLine;
import org.signal.registration.ldap.LdapConfiguration;
import org.signal.registration.ldap.LdapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@CommandLine.Command(
    name = "test-ldap",
    description = "Test LDAP authentication and retrieve the associated phone number"
)
public class TestLdap implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(TestLdap.class);

    @CommandLine.Option(names = {"--ldapUrl"}, 
        description = "LDAP URL (default: ldap://localhost:389)")
    private String ldapUrl = "ldap://localhost:389";

    @CommandLine.Option(names = {"--ldapBaseDn"}, required = true, 
        description = "LDAP Base DN")
    private String ldapBaseDn;

    @CommandLine.Option(names = {"--ldapUserFilter"}, 
        description = "LDAP User Filter (default: uid={0})")
    private String ldapUserFilter = "uid={0}";

    @CommandLine.Option(names = {"--ldapBindDn"}, required = true, 
        description = "LDAP Bind DN")
    private String ldapBindDn;

    @CommandLine.Option(names = {"--ldapBindPassword"}, required = true, 
        description = "LDAP Bind Password")
    private String ldapBindPassword;

    @CommandLine.Option(names = {"--ldapUserId"}, required = true, 
        description = "LDAP User ID")
    private String ldapUserId;

    @CommandLine.Option(names = {"--ldapPassword"}, required = true, 
        description = "LDAP password")
    private String ldapPassword;

    @CommandLine.Option(names = {"--useSsl"}, 
        description = "Use SSL/TLS connection")
    private boolean useSsl = false;

    @CommandLine.Option(names = {"--connectionTimeout"}, 
        description = "Connection timeout in milliseconds (default: 5000)")
    private int connectionTimeout = 5000;

    @CommandLine.Option(names = {"--readTimeout"}, 
        description = "Read timeout in milliseconds (default: 30000)")
    private int readTimeout = 30000;

    @Override
    public void run() {
        LOG.info("Starting LDAP test with configuration: url={}, baseDn={}, userFilter={}, useSsl={}", 
            ldapUrl, ldapBaseDn, ldapUserFilter, useSsl);
            
        try {
            LdapConfiguration config = new LdapConfiguration();
            config.setUrl(ldapUrl);
            config.setBaseDn(ldapBaseDn);
            config.setUserFilter(ldapUserFilter);
            config.setBindDn(ldapBindDn);
            config.setBindPassword(ldapBindPassword);
            config.setUseSsl(useSsl);
            config.setConnectionTimeout(connectionTimeout);
            config.setReadTimeout(readTimeout);

            LOG.debug("Initializing LDAP service...");
            LdapService ldapService = new LdapService(config);
            ldapService.initialize();
            LOG.info("LDAP service initialized successfully");

            System.out.println("\nAttempting LDAP authentication...");
            LOG.info("Attempting to authenticate user: {}", ldapUserId);
            
            Optional<String> phoneNumber = ldapService.authenticateAndGetPhoneNumber(
                ldapUserId, ldapPassword);

            if (phoneNumber.isPresent()) {
                LOG.info("Successfully authenticated user {} and retrieved phone number", ldapUserId);
                System.out.println("✓ LDAP authentication successful");
                System.out.println("✓ Phone number found: " + phoneNumber.get());
            } else {
                LOG.warn("Authentication failed or no phone number found for user: {}", ldapUserId);
                System.err.println("✗ LDAP authentication failed or no phone number found");
            }
        } catch (Exception e) {
            LOG.error("LDAP test failed with error: {}", e.getMessage(), e);
            System.err.println("✗ LDAP operation failed: " + e.getMessage());
            LOG.error("Detailed error:", e);
        }
    }
}