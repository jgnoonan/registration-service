package org.signal.registration.ldap;

import io.micronaut.context.annotation.Property;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(environments = {"test"}, startApplication = false)
@Property(name = "test.useLdap", value = "true")
public class LdapAuthenticationTest {
    private static final Logger LOG = LoggerFactory.getLogger(LdapAuthenticationTest.class);
    private static final String VALID_USER = "raja@valuelabs.com";
    private static final String VALID_PASSWORD = "Rat3onal";
    private static final String EXPECTED_PHONE = "+919703804045";
    private static final String INVALID_USER = "nonexistent@valuelabs.com";
    private static final String INVALID_PASSWORD = "wrongPassword";

    @Inject
    private LdapService ldapService;

    @BeforeEach
    void setup() throws Exception {
        ldapService.initialize();
    }

    @Test
    void testSuccessfulAuthentication() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(VALID_USER, VALID_PASSWORD);
        assertTrue(result.isPresent(), "Authentication should succeed for valid credentials");
        assertEquals(EXPECTED_PHONE, result.get(), "Should return correct phone number");
    }

    @Test
    void testInvalidUsername() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(INVALID_USER, VALID_PASSWORD);
        assertTrue(result.isEmpty(), "Authentication should fail for invalid username");
    }

    @Test
    void testInvalidPassword() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(VALID_USER, INVALID_PASSWORD);
        assertTrue(result.isEmpty(), "Authentication should fail for invalid password");
    }

    @Test
    void testNullUsername() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(null, VALID_PASSWORD);
        assertTrue(result.isEmpty(), "Authentication should be skipped for null username");
    }

    @Test
    void testNullPassword() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(VALID_USER, null);
        assertTrue(result.isEmpty(), "Authentication should be skipped for null password");
    }
}
