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
    private static final String INVALID_USER = "nonexistent@valuelabs.com";
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
     * Tests authentication failure with invalid username.
     * Verifies that authentication fails when a non-existent username is provided.
     */
    @Test
    public void testInvalidUsername() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(INVALID_USER, VALID_PASSWORD);
        assertTrue(result.isEmpty(), "Authentication should fail for invalid username");
    }

    /**
     * Tests authentication failure with invalid password.
     * Verifies that authentication fails when an incorrect password is provided.
     */
    @Test
    public void testInvalidPassword() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(VALID_USER, INVALID_PASSWORD);
        assertTrue(result.isEmpty(), "Authentication should fail for invalid password");
    }

    /**
     * Tests authentication with null username.
     * Verifies that authentication fails gracefully when username is null.
     */
    @Test
    public void testNullUsername() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(null, VALID_PASSWORD);
        assertTrue(result.isEmpty(), "Authentication should be skipped for null username");
    }

    /**
     * Tests authentication with null password.
     * Verifies that authentication fails gracefully when password is null.
     */
    @Test
    public void testNullPassword() {
        Optional<String> result = ldapService.authenticateAndGetPhoneNumber(VALID_USER, null);
        assertTrue(result.isEmpty(), "Authentication should be skipped for null password");
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
