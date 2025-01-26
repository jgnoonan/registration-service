package org.signal.registration.ldap;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFException;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

@Factory
@Requires(env = "test")
public class EmbeddedLdapConfiguration {

    @Bean
    @Singleton
    public InMemoryDirectoryServer inMemoryDirectoryServer() throws LDAPException, LDIFException {
        InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=test,dc=com");
        config.addAdditionalBindCredentials("cn=admin,dc=test,dc=com", "test");
        config.setListenerConfigs(InMemoryListenerConfig.createLDAPConfig("LDAP", 11389));
        
        InMemoryDirectoryServer server = new InMemoryDirectoryServer(config);
        
        // Add test data
        server.add("dn: dc=test,dc=com",
                   "objectClass: top",
                   "objectClass: domain",
                   "dc: test");
        
        server.add("dn: cn=test,dc=test,dc=com",
                   "objectClass: top",
                   "objectClass: person",
                   "objectClass: organizationalPerson",
                   "objectClass: inetOrgPerson",
                   "cn: test",
                   "sn: test",
                   "uid: test",
                   "mail: test@test.com",
                   "mobile: +1234567890",
                   "userPassword: test");
        
        server.startListening();
        return server;
    }
}
