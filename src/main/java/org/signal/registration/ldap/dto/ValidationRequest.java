package org.signal.registration.ldap.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

/**
 * DTO for LDAP validation requests from the iOS client.
 */
@Serdeable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationRequest {
    @JsonProperty("userId")
    private String userId;
    
    @JsonProperty("password")
    private String password;

    public ValidationRequest() {
    }

    public ValidationRequest(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
