package org.signal.registration.ldap;

import com.unboundid.ldap.sdk.*;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocketFactory;
import java.security.GeneralSecurityException;
import java.util.Optional;

@Singleton
public class LdapService {
    private static final Logger LOG = LoggerFactory.getLogger(LdapService.class);
    private final LdapConfiguration ldapConfiguration;
    private LDAPConnectionPool connectionPool;

    public LdapService(LdapConfiguration ldapConfiguration) {
        this.ldapConfiguration = ldapConfiguration;
    }

    @PostConstruct
    public void initialize() {
        try {
            LDAPConnection connection = createConnection();
            connectionPool = new LDAPConnectionPool(connection, 
                ldapConfiguration.getMinPoolSize(), 
                ldapConfiguration.getMaxPoolSize());
            connectionPool.setMaxWaitTimeMillis(ldapConfiguration.getPoolTimeout());
            LOG.info("LDAP connection pool initialized successfully");
        } catch (LDAPException e) {
            LOG.error("Failed to initialize LDAP connection pool", e);
            throw new RuntimeException("Failed to initialize LDAP connection pool", e);
        }
    }

    private LDAPConnection createConnection() throws LDAPException {
        String[] hostParts = ldapConfiguration.getUrl().split("://")[1].split(":");
        String host = hostParts[0];
        int port = hostParts.length > 1 ? Integer.parseInt(hostParts[1]) : ldapConfiguration.getPort();

        LDAPConnection connection;
        if (ldapConfiguration.isUseSsl()) {
            try {
                SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
                SSLSocketFactory sslSocketFactory = sslUtil.createSSLSocketFactory();
                connection = new LDAPConnection(sslSocketFactory, host, port);
            } catch (GeneralSecurityException e) {
                throw new LDAPException(ResultCode.CONNECT_ERROR, 
                    "Failed to create SSL connection", e);
            }
        } else {
            connection = new LDAPConnection(host, port);
        }

        connection.setConnectTimeout(ldapConfiguration.getConnectionTimeout());
        connection.setResponseTimeoutMillis(ldapConfiguration.getReadTimeout());
        return connection;
    }

    public Optional<String> authenticateAndGetPhoneNumber(String username, String password) {
        LDAPConnection connection = null;
        int retryCount = 0;
        
        while (retryCount < ldapConfiguration.getMaxRetries()) {
            try {
                connection = connectionPool.getConnection();
                
                // Bind with service account first
                connection.bind(ldapConfiguration.getBindDn(), 
                    ldapConfiguration.getBindPassword());
                
                // Search for user DN
                String userDn = findUserDn(connection, username);
                if (userDn == null) {
                    LOG.warn("User not found: {}", username);
                    return Optional.empty();
                }
                
                // Authenticate user
                try {
                    connection.bind(userDn, password);
                } catch (LDAPException e) {
                    LOG.warn("Authentication failed for user: {}", username, e);
                    return Optional.empty();
                }
                
                // Search for phone number
                return findPhoneNumber(connection, username);
                
            } catch (LDAPException e) {
                LOG.error("LDAP operation failed (attempt {}/{})", 
                    retryCount + 1, ldapConfiguration.getMaxRetries(), e);
                if (!isRetryableError(e) || retryCount >= ldapConfiguration.getMaxRetries() - 1) {
                    throw new RuntimeException("LDAP operation failed", e);
                }
                retryCount++;
                try {
                    Thread.sleep(1000 * retryCount); // Exponential backoff
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted during retry", ie);
                }
            } finally {
                if (connection != null) {
                    connectionPool.releaseConnection(connection);
                }
            }
        }
        return Optional.empty();
    }

    private String findUserDn(LDAPConnection connection, String username) 
            throws LDAPException {
        SearchResult searchResult = connection.search(
            ldapConfiguration.getBaseDn(),
            SearchScope.SUB,
            ldapConfiguration.getUserFilter().replace("{0}", 
                Filter.encodeValue(username)),
            "dn"
        );
        
        if (!searchResult.getSearchEntries().isEmpty()) {
            return searchResult.getSearchEntries().get(0).getDN();
        }
        return null;
    }

    private Optional<String> findPhoneNumber(LDAPConnection connection, String username) 
            throws LDAPException {
        SearchResult searchResult = connection.search(
            ldapConfiguration.getBaseDn(),
            SearchScope.SUB,
            ldapConfiguration.getUserFilter().replace("{0}", 
                Filter.encodeValue(username)),
            ldapConfiguration.getPhoneNumberAttribute()
        );
        
        if (!searchResult.getSearchEntries().isEmpty()) {
            String phoneNumber = searchResult.getSearchEntries().get(0)
                .getAttributeValue(ldapConfiguration.getPhoneNumberAttribute());
            LOG.debug("Found phone number for user: {}", username);
            return Optional.ofNullable(phoneNumber);
        }
        return Optional.empty();
    }

    private boolean isRetryableError(LDAPException e) {
        return e.getResultCode() == ResultCode.CONNECT_ERROR ||
               e.getResultCode() == ResultCode.SERVER_DOWN ||
               e.getResultCode() == ResultCode.TIMEOUT;
    }

    @PreDestroy
    public void cleanup() {
        if (connectionPool != null) {
            connectionPool.close();
        }
    }
}