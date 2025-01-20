package org.signal.registration.ldap;

import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.controls.SimplePagedResultsControl;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class LdapService {

    private final LdapConfiguration ldapConfiguration;

    public LdapService(LdapConfiguration ldapConfiguration) {
        this.ldapConfiguration = ldapConfiguration;
    }

    public void configure(String url, String baseDn, String userFilter, String bindDn, String bindPassword) {
        ldapConfiguration.setUrl(url);
        ldapConfiguration.setBaseDn(baseDn);
        ldapConfiguration.setUserFilter(userFilter);
        ldapConfiguration.setBindDn(bindDn);
        ldapConfiguration.setBindPassword(bindPassword);
    }

    public Optional<String> authenticateAndGetPhoneNumber(String username, String password) {
        LDAPConnection connection = null;
        try {
            // Connect to the LDAP server
            connection = new LDAPConnection(ldapConfiguration.getUrl(), 389); // Adjust the port if needed (e.g., 636 for LDAPS)
            connection.bind("uid=" + username + "," + ldapConfiguration.getBaseDn(), password);

            // Search for the user
            SearchResult searchResult = connection.search(
                ldapConfiguration.getBaseDn(),
                SearchScope.SUB,
                ldapConfiguration.getUserFilter().replace("{0}", username),
                "telephoneNumber"
            );

            if (!searchResult.getSearchEntries().isEmpty()) {
                return Optional.ofNullable(searchResult.getSearchEntries().get(0).getAttributeValue("telephoneNumber"));
            }
        } catch (LDAPException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return Optional.empty();
    }
}
