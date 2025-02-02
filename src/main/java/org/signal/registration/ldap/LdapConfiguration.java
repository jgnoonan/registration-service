package org.signal.registration.ldap;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.signal.registration.directory.DirectoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration class for LDAP authentication settings.
 * This class manages all LDAP-related configuration properties including connection,
 * authentication, SSL/TLS settings, and connection pooling parameters.
 */
@Singleton
@ConfigurationProperties("micronaut.config.registration.directory.ldap")
@Requires(property = "micronaut.config.registration.directory.type", value = "LDAP")
public class LdapConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(LdapConfiguration.class);

    /**
     * The LDAP server URL in the format ldap(s)://hostname:port
     */
    @Value("${micronaut.config.registration.directory.ldap.url}")
    private String url;

    /**
     * The base DN for LDAP searches
     */
    @Value("${micronaut.config.registration.directory.ldap.baseDn}")
    private String baseDn;

    /**
     * The filter used to search for users, {0} is replaced with the username
     */
    @Value("${micronaut.config.registration.directory.ldap.userFilter:(uid={0})}")
    private String userFilter;

    /**
     * The DN used to bind to the LDAP server for searches
     */
    @Value("${micronaut.config.registration.directory.ldap.bindDn:}")
    private String bindDn;

    /**
     * The password used to bind to the LDAP server for searches
     */
    @Value("${micronaut.config.registration.directory.ldap.bindPassword:}")
    private String bindPassword;

    /**
     * The LDAP attribute that contains the user's phone number
     */
    @Value("${micronaut.config.registration.directory.ldap.phoneNumberAttribute:mobile}")
    private String phoneNumberAttribute;

    /**
     * The LDAP server port (default: 636 for LDAPS)
     */
    @Value("${micronaut.config.registration.directory.ldap.port:636}")
    private int port;

    /**
     * Whether to use SSL/TLS for LDAP connections
     */
    @Value("${micronaut.config.registration.directory.ldap.useSsl:false}")
    private boolean useSsl;

    /**
     * Path to the truststore file containing trusted certificates
     */
    @Value("${micronaut.config.registration.directory.ldap.trustStore:}")
    private String trustStore;

    /**
     * Password for the truststore
     */
    @Value("${micronaut.config.registration.directory.ldap.trustStorePassword:}")
    private String trustStorePassword;

    /**
     * Type of truststore (default: JKS)
     */
    @Value("${micronaut.config.registration.directory.ldap.trustStoreType:JKS}")
    private String trustStoreType;

    /**
     * Whether to verify the LDAP server's hostname against its certificate
     */
    @Value("${micronaut.config.registration.directory.ldap.hostnameVerification:true}")
    private boolean hostnameVerification;

    /**
     * Connection timeout in milliseconds
     */
    @Value("${micronaut.config.registration.directory.ldap.connectionTimeout:5000}")
    private int connectionTimeout;

    /**
     * Read timeout in milliseconds
     */
    @Value("${micronaut.config.registration.directory.ldap.readTimeout:30000}")
    private int readTimeout;

    /**
     * Minimum number of connections in the connection pool
     */
    @Value("${micronaut.config.registration.directory.ldap.minPoolSize:3}")
    private int minPoolSize;

    /**
     * Maximum number of connections in the connection pool
     */
    @Value("${micronaut.config.registration.directory.ldap.maxPoolSize:10}")
    private int maxPoolSize;

    /**
     * Connection pool timeout in milliseconds
     */
    @Value("${micronaut.config.registration.directory.ldap.poolTimeout:300000}")
    private long poolTimeout;

    /**
     * Maximum number of retries for failed operations
     */
    @Value("${micronaut.config.registration.directory.ldap.maxRetries:3}")
    private int maxRetries;

    // Existing getters/setters
    /**
     * Returns the LDAP server URL.
     * @return the LDAP server URL
     */
    public String getUrl() {
        LOG.debug("LDAP URL accessed: {}", url);
        return url;
    }

    /**
     * Sets the LDAP server URL.
     * @param url the LDAP server URL
     */
    public void setUrl(String url) {
        LOG.debug("Setting LDAP URL: {}", url);
        this.url = url;
    }

    /**
     * Returns the base DN for LDAP searches.
     * @return the base DN for LDAP searches
     */
    public String getBaseDn() {
        LOG.debug("LDAP BaseDn accessed: {}", baseDn);
        return baseDn;
    }

    /**
     * Sets the base DN for LDAP searches.
     * @param baseDn the base DN for LDAP searches
     */
    public void setBaseDn(String baseDn) {
        LOG.debug("Setting LDAP BaseDn: {}", baseDn);
        this.baseDn = baseDn;
    }

    /**
     * Returns the filter used to search for users.
     * @return the filter used to search for users
     */
    public String getUserFilter() {
        LOG.debug("LDAP UserFilter accessed: {}", userFilter);
        return userFilter;
    }

    /**
     * Sets the filter used to search for users.
     * @param userFilter the filter used to search for users
     */
    public void setUserFilter(String userFilter) {
        LOG.debug("Setting LDAP UserFilter: {}", userFilter);
        this.userFilter = userFilter;
    }

    /**
     * Returns the DN used to bind to the LDAP server for searches.
     * @return the DN used to bind to the LDAP server for searches
     */
    public String getBindDn() {
        LOG.debug("LDAP BindDn accessed: {}", bindDn);
        return bindDn;
    }

    /**
     * Sets the DN used to bind to the LDAP server for searches.
     * @param bindDn the DN used to bind to the LDAP server for searches
     */
    public void setBindDn(String bindDn) {
        LOG.debug("Setting LDAP BindDn: {}", bindDn);
        this.bindDn = bindDn;
    }

    /**
     * Returns the password used to bind to the LDAP server for searches.
     * @return the password used to bind to the LDAP server for searches
     */
    public String getBindPassword() {
        LOG.debug("LDAP BindPassword accessed");
        return bindPassword;
    }

    /**
     * Sets the password used to bind to the LDAP server for searches.
     * @param bindPassword the password used to bind to the LDAP server for searches
     */
    public void setBindPassword(String bindPassword) {
        LOG.debug("Setting LDAP BindPassword");
        this.bindPassword = bindPassword;
    }

    // New getters/setters
    /**
     * Returns the LDAP server port.
     * @return the LDAP server port
     */
    public int getPort() {
        return port;
    }

    /**
     * Sets the LDAP server port.
     * @param port the LDAP server port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Returns whether to use SSL/TLS for LDAP connections.
     * @return whether to use SSL/TLS for LDAP connections
     */
    public boolean isUseSsl() {
        return useSsl;
    }

    /**
     * Sets whether to use SSL/TLS for LDAP connections.
     * @param useSsl whether to use SSL/TLS for LDAP connections
     */
    public void setUseSsl(boolean useSsl) {
        this.useSsl = useSsl;
    }

    /**
     * Returns the path to the truststore file containing trusted certificates.
     * @return the path to the truststore file containing trusted certificates
     */
    public String getTrustStore() {
        return trustStore;
    }

    /**
     * Sets the path to the truststore file containing trusted certificates.
     * @param trustStore the path to the truststore file containing trusted certificates
     */
    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }

    /**
     * Returns the password for the truststore.
     * @return the password for the truststore
     */
    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    /**
     * Sets the password for the truststore.
     * @param trustStorePassword the password for the truststore
     */
    public void setTrustStorePassword(String trustStorePassword) {
        this.trustStorePassword = trustStorePassword;
    }

    /**
     * Returns the type of truststore.
     * @return the type of truststore
     */
    public String getTrustStoreType() {
        return trustStoreType;
    }

    /**
     * Sets the type of truststore.
     * @param trustStoreType the type of truststore
     */
    public void setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

    /**
     * Returns whether to verify the LDAP server's hostname against its certificate.
     * @return whether to verify the LDAP server's hostname against its certificate
     */
    public boolean isHostnameVerification() {
        return hostnameVerification;
    }

    /**
     * Sets whether to verify the LDAP server's hostname against its certificate.
     * @param hostnameVerification whether to verify the LDAP server's hostname against its certificate
     */
    public void setHostnameVerification(boolean hostnameVerification) {
        this.hostnameVerification = hostnameVerification;
    }

    /**
     * Returns the connection timeout in milliseconds.
     * @return the connection timeout in milliseconds
     */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    /**
     * Sets the connection timeout in milliseconds.
     * @param connectionTimeout the connection timeout in milliseconds
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * Returns the read timeout in milliseconds.
     * @return the read timeout in milliseconds
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * Sets the read timeout in milliseconds.
     * @param readTimeout the read timeout in milliseconds
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    /**
     * Returns the minimum number of connections in the connection pool.
     * @return the minimum number of connections in the connection pool
     */
    public int getMinPoolSize() {
        return minPoolSize;
    }

    /**
     * Sets the minimum number of connections in the connection pool.
     * @param minPoolSize the minimum number of connections in the connection pool
     */
    public void setMinPoolSize(int minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    /**
     * Returns the maximum number of connections in the connection pool.
     * @return the maximum number of connections in the connection pool
     */
    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    /**
     * Sets the maximum number of connections in the connection pool.
     * @param maxPoolSize the maximum number of connections in the connection pool
     */
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    /**
     * Returns the connection pool timeout in milliseconds.
     * @return the connection pool timeout in milliseconds
     */
    public long getPoolTimeout() {
        return poolTimeout;
    }

    /**
     * Sets the connection pool timeout in milliseconds.
     * @param poolTimeout the connection pool timeout in milliseconds
     */
    public void setPoolTimeout(long poolTimeout) {
        this.poolTimeout = poolTimeout;
    }

    /**
     * Returns the maximum number of retries for failed operations.
     * @return the maximum number of retries for failed operations
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * Sets the maximum number of retries for failed operations.
     * @param maxRetries the maximum number of retries for failed operations
     */
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    /**
     * Returns the LDAP attribute that contains the user's phone number.
     * @return the LDAP attribute that contains the user's phone number
     */
    public String getPhoneNumberAttribute() {
        LOG.debug("LDAP phone number attribute accessed: {}", phoneNumberAttribute);
        return phoneNumberAttribute;
    }

    /**
     * Sets the LDAP attribute that contains the user's phone number.
     * @param phoneNumberAttribute the LDAP attribute that contains the user's phone number
     */
    public void setPhoneNumberAttribute(String phoneNumberAttribute) {
        LOG.debug("Setting LDAP phone number attribute: {}", phoneNumberAttribute);
        this.phoneNumberAttribute = phoneNumberAttribute;
    }
}