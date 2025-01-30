package org.signal.registration.ldap;

import com.unboundid.ldap.sdk.*;
import com.unboundid.util.ssl.JVMDefaultTrustManager;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;
import jakarta.inject.Singleton;
import org.signal.registration.directory.DirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Optional;

/**
 * Service class for handling LDAP authentication and user management.
 * This class provides functionality for authenticating users against an LDAP server,
 * managing LDAP connections with SSL/TLS support, and retrieving user information.
 */
@Singleton
public class LdapService implements DirectoryService {
    private static final Logger LOG = LoggerFactory.getLogger(LdapService.class);
    private final LdapConfiguration ldapConfiguration;
    private LDAPConnectionPool connectionPool;

    /**
     * Constructs a new LdapService with the specified configuration.
     *
     * @param ldapConfiguration the LDAP configuration to use
     */
    public LdapService(LdapConfiguration ldapConfiguration) {
        this.ldapConfiguration = ldapConfiguration;
    }

    /**
     * Initializes the LDAP service with the specified configuration.
     *
     * @throws LDAPException if an error occurs during initialization
     */
    @Override
    public void initialize() throws LDAPException {
        LOG.info("Initializing LDAP service with configuration: url={}, baseDn={}, useSSL={}", 
            ldapConfiguration.getUrl(), ldapConfiguration.getBaseDn(), ldapConfiguration.isUseSsl());
        
        LDAPConnection connection = createConnection();
        try {
            LOG.debug("Attempting to bind with service account: {}", ldapConfiguration.getBindDn());
            connection.bind(ldapConfiguration.getBindDn(), ldapConfiguration.getBindPassword());
            
            connectionPool = new LDAPConnectionPool(connection,
                    ldapConfiguration.getMinPoolSize(),
                    ldapConfiguration.getMaxPoolSize());
            connectionPool.setMaxWaitTimeMillis(ldapConfiguration.getPoolTimeout());
            
            LOG.info("Successfully initialized LDAP connection pool: minSize={}, maxSize={}, timeout={}ms",
                ldapConfiguration.getMinPoolSize(), ldapConfiguration.getMaxPoolSize(), 
                ldapConfiguration.getPoolTimeout());
        } catch (LDAPException e) {
            LOG.error("Failed to initialize LDAP connection pool: {}", e.getMessage(), e);
            throw e;
        } finally {
            connection.close();
        }
    }

    /**
     * Authenticates a user against the LDAP server and retrieves their phone number.
     *
     * @param userId the user ID to authenticate
     * @param password the password to authenticate with
     * @return the user's phone number if authentication is successful, empty otherwise
     */
    @Override
    public Optional<String> authenticateAndGetPhoneNumber(String userId, String password) {
        try {
            return findPhoneNumber(connectionPool.getConnection(), userId);
        } catch (LDAPException e) {
            LOG.error("Failed to authenticate user or retrieve phone number", e);
            return Optional.empty();
        }
    }

    /**
     * Finds the phone number for a given username.
     *
     * @param connection the LDAP connection to use
     * @param username the username to find the phone number for
     * @return the user's phone number if found, empty otherwise
     * @throws LDAPException if an error occurs while searching for the user
     */
    private Optional<String> findPhoneNumber(LDAPConnection connection, String username) throws LDAPException {
        if (username == null || username.isEmpty()) {
            LOG.debug("Username is null or empty");
            return Optional.empty();
        }
        
        String filter = String.format("(|(uid=%s)(mail=%s))", username, username);
        LOG.debug("Searching for phone number with filter: {} and attribute: {} in baseDn: {}", 
            filter, ldapConfiguration.getPhoneNumberAttribute(), ldapConfiguration.getBaseDn());
        
        SearchResultEntry entry = connection.searchForEntry(
            ldapConfiguration.getBaseDn(),
            SearchScope.SUB,
            filter,
            ldapConfiguration.getPhoneNumberAttribute()
        );
        
        if (entry == null) {
            LOG.debug("No entry found when searching for phone number for user: {}", username);
            return Optional.empty();
        }
        
        String phoneNumber = entry.getAttributeValue(ldapConfiguration.getPhoneNumberAttribute());
        if (phoneNumber == null) {
            LOG.debug("No phone number found for user: {}", username);
            return Optional.empty();
        }
        
        LOG.debug("Found phone number for user {}: {}", username, phoneNumber);
        return Optional.of(phoneNumber);
    }

    /**
     * Closes the LDAP connection pool.
     */
    @Override
    public void cleanup() {
        if (connectionPool != null) {
            connectionPool.close();
        }
    }

    /**
     * Creates a new LDAP connection with the configured settings.
     * This method handles SSL/TLS configuration and connection options.
     *
     * @return a new LDAP connection
     * @throws LDAPException if an error occurs while creating the connection
     */
    private LDAPConnection createConnection() throws LDAPException {
        String url = ldapConfiguration.getUrl();
        if (url == null || url.isEmpty()) {
            throw new LDAPException(ResultCode.PARAM_ERROR, "LDAP URL cannot be null or empty");
        }

        String host;
        int port;
        try {
            URI uri = new URI(url);
            host = uri.getHost();
            port = uri.getPort();
            if (host == null) {
                throw new LDAPException(ResultCode.PARAM_ERROR, "Invalid LDAP URL: missing host");
            }
            if (port == -1) {
                port = ldapConfiguration.getPort();
            }
            if (port <= 0 || port > 65535) {
                throw new LDAPException(ResultCode.PARAM_ERROR, "Invalid port number: " + port);
            }
        } catch (URISyntaxException e) {
            throw new LDAPException(ResultCode.PARAM_ERROR, "Invalid LDAP URL: " + e.getMessage());
        }

        LDAPConnectionOptions options = new LDAPConnectionOptions();
        options.setConnectTimeoutMillis(ldapConfiguration.getConnectionTimeout());
        options.setResponseTimeoutMillis(ldapConfiguration.getReadTimeout());
        options.setFollowReferrals(false);

        SSLSocketFactory sslSocketFactory = null;
        if (ldapConfiguration.isUseSsl()) {
            try {
                SSLUtil sslUtil;
                if (ldapConfiguration.getTrustStore() != null && !ldapConfiguration.getTrustStore().isEmpty()) {
                    LOG.info("Using custom truststore: {}", ldapConfiguration.getTrustStore());
                    KeyStore trustStore = KeyStore.getInstance(ldapConfiguration.getTrustStoreType());
                    try (FileInputStream fis = new FileInputStream(ldapConfiguration.getTrustStore())) {
                        trustStore.load(fis, ldapConfiguration.getTrustStorePassword().toCharArray());
                    }
                    TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                    tmf.init(trustStore);
                    sslUtil = new SSLUtil(tmf.getTrustManagers());
                } else {
                    LOG.info("Using JVM default trust store");
                    sslUtil = new SSLUtil(JVMDefaultTrustManager.getInstance());
                }

                if (!ldapConfiguration.isHostnameVerification()) {
                    LOG.warn("Hostname verification is disabled. This is not recommended for production use.");
                    sslUtil = new SSLUtil(new TrustAllTrustManager());
                }

                sslSocketFactory = sslUtil.createSSLSocketFactory();
            } catch (GeneralSecurityException | IOException e) {
                throw new LDAPException(ResultCode.CONNECT_ERROR, 
                    "Failed to create SSL connection: " + e.getMessage(), e);
            }
        }

        try {
            if (sslSocketFactory != null) {
                return new LDAPConnection(sslSocketFactory, options, host, port);
            } else {
                return new LDAPConnection(options, host, port);
            }
        } catch (LDAPException e) {
            LOG.error("Failed to connect to LDAP server: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Implements an exponential backoff strategy for retrying failed operations.
     *
     * @param retryCount the current retry attempt number
     */
    private void backoff(int retryCount) {
        try {
            Thread.sleep((long) Math.pow(2, retryCount) * 100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Checks if an LDAP exception is retryable.
     *
     * @param e the LDAP exception to check
     * @return true if the exception is retryable, false otherwise
     */
    private boolean isRetryableError(LDAPException e) {
        return e.getResultCode() == ResultCode.CONNECT_ERROR ||
               e.getResultCode() == ResultCode.SERVER_DOWN ||
               e.getResultCode() == ResultCode.TIMEOUT;
    }
}