package org.signal.registration.ldap;

import com.unboundid.ldap.sdk.*;
import com.unboundid.util.ssl.JVMDefaultTrustManager;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustStoreTrustManager;
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

    public void initialize() throws LDAPException {
        LDAPConnection connection = createConnection();
        try {
            connection.bind(ldapConfiguration.getBindDn(), ldapConfiguration.getBindPassword());
            connectionPool = new LDAPConnectionPool(connection,
                    ldapConfiguration.getMinPoolSize(),
                    ldapConfiguration.getMaxPoolSize());
            connectionPool.setMaxWaitTimeMillis(ldapConfiguration.getPoolTimeout());
        } finally {
            connection.close();
        }
    }

    private LDAPConnection createConnection() throws LDAPException {
        String[] hostParts = ldapConfiguration.getUrl().split("://")[1].split(":");
        String host = hostParts[0];
        int port = hostParts.length > 1 ? Integer.parseInt(hostParts[1]) : ldapConfiguration.getPort();

        LDAPConnectionOptions options = new LDAPConnectionOptions();
        options.setConnectTimeoutMillis(ldapConfiguration.getConnectionTimeout());
        options.setResponseTimeoutMillis(ldapConfiguration.getReadTimeout());

        LDAPConnection connection;
        if (ldapConfiguration.isUseSsl()) {
            try {
                // Use configured trust store instead of trusting all
                SSLUtil sslUtil;
                if (ldapConfiguration.getTrustStorePath() != null) {
                    sslUtil = new SSLUtil(new TrustStoreTrustManager(
                        ldapConfiguration.getTrustStorePath(),
                        ldapConfiguration.getTrustStorePassword().toCharArray(),
                        "JKS",
                        true
                    ));
                } else {
                    // Fallback to default system trust store
                    sslUtil = new SSLUtil(new JVMDefaultTrustManager());
                }
                SSLSocketFactory sslSocketFactory = sslUtil.createSSLSocketFactory();
                connection = new LDAPConnection(sslSocketFactory, options, host, port);
            } catch (GeneralSecurityException e) {
                throw new LDAPException(ResultCode.CONNECT_ERROR, 
                    "Failed to create SSL connection", e);
            }
        } else {
            connection = new LDAPConnection(options, host, port);
        }

        return connection;
    }

    public Optional<String> authenticateAndGetPhoneNumber(String username, String password) {
        int retryCount = 0;
        while (true) {
            try {
                return tryAuthenticateAndGetPhoneNumber(username, password);
            } catch (LDAPException e) {
                if (!isRetryableError(e) || retryCount >= ldapConfiguration.getMaxRetries()) {
                    LOG.error("LDAP authentication failed", e);
                    return Optional.empty();
                }
                backoff(retryCount++);
            }
        }
    }

    private Optional<String> tryAuthenticateAndGetPhoneNumber(String username, String password) throws LDAPException {
        LDAPConnection connection = connectionPool.getConnection();
        try {
            String userDn = findUserDn(connection, username);
            if (userDn == null) {
                return Optional.empty();
            }

            // Verify password by binding
            connection.bind(userDn, password);

            // Get phone number
            return findPhoneNumber(connection, username);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    private String findUserDn(LDAPConnection connection, String username) throws LDAPException {
        SearchResultEntry entry = connection.searchForEntry(
            ldapConfiguration.getBaseDn(),
            SearchScope.SUB,
            String.format(ldapConfiguration.getUserFilter(), username),
            "dn"
        );
        return entry != null ? entry.getDN() : null;
    }

    private Optional<String> findPhoneNumber(LDAPConnection connection, String username) throws LDAPException {
        SearchResultEntry entry = connection.searchForEntry(
            ldapConfiguration.getBaseDn(),
            SearchScope.SUB,
            String.format(ldapConfiguration.getUserFilter(), username),
            ldapConfiguration.getPhoneNumberAttribute()
        );
        if (entry == null) {
            return Optional.empty();
        }
        String phoneNumber = entry.getAttributeValue(ldapConfiguration.getPhoneNumberAttribute());
        return Optional.ofNullable(phoneNumber);
    }

    private void backoff(int retryCount) {
        try {
            Thread.sleep((long) Math.pow(2, retryCount) * 100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private boolean isRetryableError(LDAPException e) {
        return e.getResultCode() == ResultCode.CONNECT_ERROR ||
               e.getResultCode() == ResultCode.SERVER_DOWN ||
               e.getResultCode() == ResultCode.TIMEOUT;
    }

    public void cleanup() {
        if (connectionPool != null) {
            connectionPool.close();
        }
    }
}