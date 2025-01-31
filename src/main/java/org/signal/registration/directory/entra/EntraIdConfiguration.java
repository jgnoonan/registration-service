package org.signal.registration.directory.entra;

import io.micronaut.context.annotation.ConfigurationProperties;

/**
 * Configuration properties for Microsoft Entra ID integration (formerly Azure AD)
 */
@ConfigurationProperties("micronaut.config.registration.entra-id")
public class EntraIdConfiguration {
    private boolean enabled = false;
    private String tenantId;
    private String clientId;
    private String clientSecret;
    private String certificatePath;
    private String testUser;
    private String testPassword;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

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

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
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
}
