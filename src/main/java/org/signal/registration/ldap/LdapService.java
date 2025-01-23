/**
 * Service class for handling LDAP authentication and user management.
 * This class provides functionality for authenticating users against an LDAP server,
 * managing LDAP connections with SSL/TLS support, and retrieving user information.
 */
@Singleton
public class LdapService {
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
     * @param username the username to authenticate
     * @param password the password to authenticate with
     * @return the user's phone number if authentication is successful, empty otherwise
     */
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

    /**
     * Attempts to authenticate a user against the LDAP server and retrieve their phone number.
     *
     * @param username the username to authenticate
     * @param password the password to authenticate with
     * @return the user's phone number if authentication is successful, empty otherwise
     * @throws LDAPException if an error occurs during authentication
     */
    private Optional<String> tryAuthenticateAndGetPhoneNumber(String username, String password) throws LDAPException {
        LOG.debug("Attempting to authenticate and retrieve phone number for user: {}", username);
        LDAPConnection connection = connectionPool.getConnection();
        try {
            String userDn = findUserDn(connection, username);
            if (userDn == null) {
                LOG.info("User DN not found for username: {}", username);
                return Optional.empty();
            }
            LOG.debug("Found user DN: {}", userDn);

            // Verify password by binding
            try {
                LOG.debug("Attempting to bind with user credentials: {}", userDn);
                LDAPConnection userConnection = new LDAPConnection(connection.getSocketFactory(),
                        connection.getConnectionOptions(),
                        connection.getConnectedAddress(),
                        connection.getConnectedPort());
                userConnection.bind(userDn, password);
                LOG.debug("Successfully authenticated user: {}", username);
                userConnection.close();
            } catch (LDAPException e) {
                LOG.info("Authentication failed for user {}: {}", username, e.getMessage());
                return Optional.empty();
            }

            // Get phone number
            Optional<String> phoneNumber = findPhoneNumber(connection, username);
            if (phoneNumber.isPresent()) {
                LOG.debug("Successfully retrieved phone number for user: {}", username);
            } else {
                LOG.warn("No phone number found for authenticated user: {}", username);
            }
            return phoneNumber;
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    /**
     * Finds the DN (Distinguished Name) for a given username.
     *
     * @param connection the LDAP connection to use
     * @param username the username to find the DN for
     * @return the user's DN if found, null otherwise
     * @throws LDAPException if an error occurs while searching for the user
     */
    private String findUserDn(LDAPConnection connection, String username) throws LDAPException {
        if (username == null || username.isEmpty()) {
            LOG.debug("Username is null or empty");
            return null;
        }
        
        String filter = String.format("(|(uid=%s)(mail=%s))", username, username);
        LOG.debug("Searching for user DN with filter: {} in baseDn: {}", filter, ldapConfiguration.getBaseDn());
        
        SearchResultEntry entry = connection.searchForEntry(
            ldapConfiguration.getBaseDn(),
            SearchScope.SUB,
            filter,
            "dn"
        );
        
        if (entry != null) {
            String dn = entry.getDN();
            LOG.debug("Found DN for user {}: {}", username, dn);
            return dn;
        } else {
            LOG.debug("No DN found for user: {}", username);
            return null;
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

    /**
     * Closes the LDAP connection pool.
     */
    public void cleanup() {
        if (connectionPool != null) {
            connectionPool.close();
        }
    }
}