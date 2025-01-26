package org.signal.registration.ldap;

import io.micronaut.context.annotation.Property;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for LDAP authentication functionality.
 * These tests verify the LDAP authentication process, including successful authentication,
 * failed authentication scenarios, and proper handling of edge cases.
 */
@MicronautTest(environments = {"test"}, startApplication = false)
@Property(name = "test.useLdap", value = "true")
public class LdapAuthenticationTest {
    private static final Logger LOG = LoggerFactory.getLogger(LdapAuthenticationTest.class);
    private static final String VALID_USER = "raja@valuelabs.com";
    private static final String VALID_PASSWORD = "Rat3onal";
    private static final String EXPECTED_PHONE = "+919703804045";
    private static final String INVALID_USER = "nonexistent@test.com";
    private static final String INVALID_PASSWORD = "wrongPassword";

    @Inject
    private LdapService ldapService;

    /**
     * Test setup method that runs before each test.
     * Initializes the LDAP service with test configuration.
     */
    @BeforeEach
    public void setup() throws Exception {
        ldapService.initialize();
    }

    /**
     * Tests successful authentication with valid credentials.
     * Verifies that a user with correct username and password can authenticate.
     */
    @Test
    public void testSuccessfulAuthentication() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(VALID_USER, VALID_PASSWORD);
        assertTrue(result.isPresent(), "Authentication should succeed for valid credentials");
        assertEquals(EXPECTED_PHONE, result.get(), "Should return correct phone number");
    }

    /**
     * Tests failed authentication with invalid username.
     * Verifies that authentication fails when username doesn't exist.
     */
    @Test
    public void testFailedAuthenticationInvalidUser() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(INVALID_USER, VALID_PASSWORD);
        assertFalse(result.isPresent(), "Authentication should fail for invalid username");
    }

    /**
     * Tests failed authentication with invalid password.
     * Verifies that authentication fails when password is incorrect.
     */
    @Test
    public void testFailedAuthenticationInvalidPassword() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(VALID_USER, INVALID_PASSWORD);
        assertFalse(result.isPresent(), "Authentication should fail for invalid password");
    }

    /**
     * Tests authentication with null credentials.
     * Verifies that authentication fails gracefully when credentials are null.
     */
    @Test
    public void testNullCredentials() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(null, null);
        assertFalse(result.isPresent(), "Authentication should fail for null credentials");
    }

    /**
     * Cleanup method that runs after each test.
     * Ensures proper cleanup of LDAP resources.
     */
    @AfterEach
    public void cleanup() {
        ldapService.cleanup();
    }
}
