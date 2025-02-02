package org.signal.registration.ldap.controller;

import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;
import jakarta.inject.Inject;
import org.signal.registration.ldap.LdapService;
import org.signal.registration.ldap.dto.ValidationRequest;
import org.signal.registration.ldap.dto.ValidationResponse;
import org.signal.registration.ldap.util.ValidationCodeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Controller handling LDAP validation requests from the iOS client.
 * Provides endpoints for validating user credentials and retrieving phone numbers.
 */
@Controller("/validate")
@ExecuteOn(TaskExecutors.IO)
@Requires(property = "micronaut.config.registration.directory.type", value = "LDAP")
public class LdapValidationController {

    private static final Logger logger = LoggerFactory.getLogger(LdapValidationController.class);
    private final LdapService ldapService;
    private final ValidationCodeUtil validationCodeUtil;

    @Inject
    public LdapValidationController(LdapService ldapService, ValidationCodeUtil validationCodeUtil) {
        this.ldapService = ldapService;
        this.validationCodeUtil = validationCodeUtil;
    }

    /**
     * Validates user credentials and returns the associated phone number if successful.
     *
     * @param request The validation request containing userId and password
     * @return HTTP response with validation result
     */
    @Post
    public HttpResponse<ValidationResponse> validateCredentials(@Body ValidationRequest request) {
        try {
            if (request.getUserId() == null || request.getPassword() == null) {
                return HttpResponse.badRequest(ValidationResponse.error("User ID and password are required"));
            }

            Optional<String> phoneNumberOpt = ldapService.authenticateAndGetPhoneNumber(request.getUserId(), request.getPassword());
            
            if (phoneNumberOpt.isPresent()) {
                String phoneNumber = phoneNumberOpt.get();
                String validationCode = validationCodeUtil.generateValidationCode(phoneNumber);
                ValidationResponse response = ValidationResponse.success(phoneNumber);
                response.setValidationCode(validationCode);
                return HttpResponse.ok(response);
            } else {
                return HttpResponse.ok(ValidationResponse.error("Invalid credentials"));
            }

        } catch (Exception e) {
            logger.error("Error during LDAP validation", e);
            return HttpResponse.serverError(ValidationResponse.error("An error occurred during validation"));
        }
    }
}
