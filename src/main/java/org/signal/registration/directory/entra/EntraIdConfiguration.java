package org.signal.registration.directory.entra;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Value;

/**
 * Configuration properties for Microsoft Entra ID integration (formerly Azure AD)
 */
@ConfigurationProperties("micronaut.config.registration.entra-id")
public class EntraIdConfiguration {
    @Value("${micronaut.config.registration.entra-id.enabled:false}")
    private boolean enabled;

    @Value("${micronaut.config.registration.entra-id.tenant-id:#{null}}")
    private String tenantId;

    @Value("${micronaut.config.registration.entra-id.client-id:#{null}}")
    private String clientId;

    @Value("${micronaut.config.registration.entra-id.client-secret:#{null}}")
    private String clientSecret;

    @Value("${micronaut.config.registration.entra-id.certificate-path:#{null}}")
    private String certificatePath;

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
}
