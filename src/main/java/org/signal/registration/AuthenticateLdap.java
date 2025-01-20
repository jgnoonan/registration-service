package org.signal.registration;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.inject.Inject;
import org.signal.registration.ldap.LdapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

@Controller("/authenticate")
public class AuthenticateLdap {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticateLdap.class);

    @Inject
    private LdapService ldapService;

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> authenticate(@Body AuthenticationRequest request) {
        LOG.info("Authenticating user: {}", request.getUserID());
        try {
            Optional<String> phoneNumber = ldapService.authenticateAndGetPhoneNumber(request.getUserID(), request.getPassword());
            if (phoneNumber.isPresent()) {
                LOG.info("Authentication successful for user: {}", request.getUserID());
                return HttpResponse.ok(Map.of("phoneNumber", phoneNumber.get()));
            } else {
                LOG.warn("Authentication failed for user: {}", request.getUserID());
                return HttpResponse.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials"));
            }
        } catch (Exception e) {
            LOG.error("Error during authentication for user: {}", request.getUserID(), e);
            return HttpResponse.serverError(Map.of("error", "Internal server error"));
        }
    }

    public static class AuthenticationRequest {
        private String userID;
        private String password;

        // Getters and setters
        public String getUserID() {
            return userID;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
