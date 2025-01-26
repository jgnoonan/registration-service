package org.signal.registration.ldap.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

/**
 * DTO for LDAP validation responses to the iOS client.
 */
@Serdeable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationResponse {

    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    
    @JsonProperty("error")
    private String error;

    @JsonProperty("validationCode")
    private String validationCode;

    public ValidationResponse() {
    }

    public ValidationResponse(boolean success, String phoneNumber, String error, String validationCode) {
        this.success = success;
        this.phoneNumber = phoneNumber;
        this.error = error;
        this.validationCode = validationCode;
    }

    public static ValidationResponse success(String phoneNumber) {
        ValidationResponse response = new ValidationResponse();
        response.setSuccess(true);
        response.setPhoneNumber(phoneNumber);
        return response;
    }

    public static ValidationResponse error(String error) {
        ValidationResponse response = new ValidationResponse();
        response.setSuccess(false);
        response.setError(error);
        return response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }
}
