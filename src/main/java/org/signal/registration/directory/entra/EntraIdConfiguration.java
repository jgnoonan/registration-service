package org.signal.registration.directory.entra;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration properties for Microsoft Entra ID integration (formerly Azure AD)
 */
@Singleton
@ConfigurationProperties("micronaut.config.registration.directory.entra-id")
public class EntraIdConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(EntraIdConfiguration.class);

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

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getTestUser() {
        return testUser;
    }

    public void setTestUser(String testUser) {
        this.testUser = testUser;
    }

    public String getTestPassword() {
        return testPassword;
    }

    public void setTestPassword(String testPassword) {
        this.testPassword = testPassword;
    }

    public String getPhoneNumberAttribute() {
        return phoneNumberAttribute;
    }

    public void setPhoneNumberAttribute(String phoneNumberAttribute) {
        this.phoneNumberAttribute = phoneNumberAttribute;
    }
}
