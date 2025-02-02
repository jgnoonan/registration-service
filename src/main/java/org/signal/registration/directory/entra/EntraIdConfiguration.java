package org.signal.registration.directory.entra;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.signal.registration.directory.DirectoryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration properties for Microsoft Entra ID integration (formerly Azure AD)
 */
@Singleton
@ConfigurationProperties("micronaut.config.registration.directory.entra-id")
@Requires(property = "micronaut.config.registration.directory.type", value = "ENTRA_ID")
@Requires(property = "ENTRA_TENANT_ID")
@Requires(property = "ENTRA_CLIENT_ID")
@Requires(property = "ENTRA_CLIENT_SECRET")
public class EntraIdConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(EntraIdConfiguration.class);

    private boolean enabled = true;

    @Value("${micronaut.config.registration.directory.entra-id.tenant-id}")
    private String tenantId;

    @Value("${micronaut.config.registration.directory.entra-id.client-id}")
    private String clientId;

    @Value("${micronaut.config.registration.directory.entra-id.client-secret}")
    private String clientSecret;

    @Value("${micronaut.config.registration.directory.entra-id.test-user}")
    private String testUser;

    @Value("${micronaut.config.registration.directory.entra-id.test-password}")
    private String testPassword;

    @Value("${micronaut.config.registration.directory.entra-id.phone-number-attribute:mobilePhone}")
    private String phoneNumberAttribute;

    public EntraIdConfiguration() {
        LOG.info("Initializing EntraIdConfiguration");
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getTenantId() {
        LOG.debug("Getting tenant ID: {}", tenantId);
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        LOG.info("Setting tenant ID: {}", tenantId);
        this.tenantId = tenantId;
    }

    public String getClientId() {
        LOG.debug("Getting client ID: {}", clientId);
        return clientId;
    }

    public void setClientId(String clientId) {
        LOG.info("Setting client ID: {}", clientId);
        this.clientId = clientId;
    }

    public String getClientSecret() {
        LOG.debug("Getting client secret (length: {})", clientSecret != null ? clientSecret.length() : 0);
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        LOG.info("Setting client secret (length: {})", clientSecret != null ? clientSecret.length() : 0);
        this.clientSecret = clientSecret;
    }

    public String getTestUser() {
        LOG.debug("Getting test user: {}", testUser);
        return testUser;
    }

    public void setTestUser(String testUser) {
        LOG.debug("Setting test user: {}", testUser);
        this.testUser = testUser;
    }

    public String getTestPassword() {
        LOG.debug("Getting test password: {}", testPassword);
        return testPassword;
    }

    public void setTestPassword(String testPassword) {
        LOG.debug("Setting test password: {}", testPassword);
        this.testPassword = testPassword;
    }

    public String getPhoneNumberAttribute() {
        LOG.debug("Getting phone number attribute: {}", phoneNumberAttribute);
        return phoneNumberAttribute;
    }

    public void setPhoneNumberAttribute(String phoneNumberAttribute) {
        LOG.debug("Setting phone number attribute: {}", phoneNumberAttribute);
        this.phoneNumberAttribute = phoneNumberAttribute;
    }
}
