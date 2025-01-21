package org.signal.registration.cli;

import java.util.Hashtable;

import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.Context;
import javax.naming.directory.*;

import picocli.CommandLine;

@CommandLine.Command(
    name = "test-ldap",
    description = "Test LDAP authentication and retrieve the associated phone number"
)
public class TestLdap implements Runnable {

    @CommandLine.Option(names = {"--ldapUserId"}, required = true, description = "LDAP User ID (email)")
    private String ldapUserId;

    @CommandLine.Option(names = {"--ldapPassword"}, required = true, description = "LDAP password")
    private String ldapPassword;

    @Override
    public void run() {
        try {
            // Example LDAP authentication logic
            String phoneNumber = authenticateAndRetrievePhoneNumber(ldapUserId, ldapPassword);
            System.out.println("LDAP authentication successful. Associated phone number: " + phoneNumber);
        } catch (Exception e) {
            System.err.println("LDAP authentication failed: " + e.getMessage());
        }
    }

   private String authenticateAndRetrievePhoneNumber(String userId, String password) throws Exception {
      Hashtable<String, String> env = new Hashtable<>();
      env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
      env.put(Context.PROVIDER_URL, "ldap://localhost:389");
      env.put(Context.SECURITY_AUTHENTICATION, "simple");
      env.put(Context.SECURITY_PRINCIPAL, userId);
      env.put(Context.SECURITY_CREDENTIALS, password);

      DirContext ctx = null;
      try {
          ctx = new InitialDirContext(env);

          // Perform LDAP search to retrieve the phone number
          String searchFilter = "(uid=" + userId + ")";
          String searchBase = "dc=valuelabs,dc=com";

          SearchControls searchControls = new SearchControls();
          searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

          NamingEnumeration<SearchResult> results = ctx.search(searchBase, searchFilter, searchControls);

          if (results.hasMore()) {
              Attributes attributes = results.next().getAttributes();
              return attributes.get("telephoneNumber").get().toString();
          } else {
              throw new Exception("No user found");
          }
      } finally {
          if (ctx != null) {
              ctx.close();
          }
      }
    }
}
