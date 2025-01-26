package org.signal.registration.ldap;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFReader;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Factory
@Requires(env = "test")
public class LdapTestServer {
    private static final Logger LOG = LoggerFactory.getLogger(LdapTestServer.class);

    @Value("${micronaut.config.registration.ldap.baseDn}")
    private String baseDn;

    @Value("${test.testUser}")
    private String testUser;

    @Value("${test.testUserPassword}")
    private String testUserPassword;

    @Bean
    @Singleton
    public InMemoryDirectoryServer directoryServer() throws LDAPException {
        LOG.info("Setting up LDAP test server with baseDn: {}, testUser: {}", baseDn, testUser);
        
        // Create server configuration
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig(baseDn);
        config.setSchema(null); // Disable schema validation
        config.setListenerConfigs(InMemoryListenerConfig.createLDAPConfig("LDAP", 11389));
        
        // Set admin bind credentials
        String adminDn = "cn=admin," + baseDn;
        config.addAdditionalBindCredentials(adminDn, testUserPassword);
        
        // Create and start the server
        InMemoryDirectoryServer server = new InMemoryDirectoryServer(config);

        // Create LDIF content for test user
        String ldifContent = String.format("""
            dn: ou=users,%s
            objectClass: organizationalUnit
            objectClass: top
            ou: users

            dn: %s
            objectClass: simpleSecurityObject
            objectClass: organizationalRole
            cn: admin
            description: LDAP administrator
            userPassword: %s

            dn: uid=%s,ou=users,%s
            objectClass: inetOrgPerson
            cn: Raja
            sn: Raja
            uid: %s
            mail: %s
            homePhone: +919703804045
            mobile: +919703804045
            userPassword: %s
            """,
            baseDn,
            adminDn,
            testUserPassword,
            testUser.split("@")[0],
            baseDn,
            testUser.split("@")[0],
            testUser,
            testUserPassword
        );

        LOG.debug("LDIF content: {}", ldifContent);

        // Import LDIF content
        server.importFromLDIF(true, new LDIFReader(
            new ByteArrayInputStream(ldifContent.getBytes(StandardCharsets.UTF_8))
        ));

        // Start the server
        server.startListening();
        LOG.info("LDAP test server started on port 11389");

        return server;
    }
}
