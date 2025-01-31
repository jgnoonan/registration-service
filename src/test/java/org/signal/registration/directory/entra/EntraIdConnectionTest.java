package org.signal.registration.directory.entra;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserCollectionPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.Disabled;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class EntraIdConnectionTest {
    private static final Logger logger = LoggerFactory.getLogger(EntraIdConnectionTest.class);
    
    private static final String TEST_USER_EMAIL = "jgnoonan@comcast.net";
    private static final String TEST_USER_PASSWORD = System.getenv("ENTRA_TEST_USER_PASSWORD");
    
    private EntraIdDirectoryService directoryService;
    private EntraIdConfiguration config;

    @BeforeEach
    void setUp() {
        // Initialize configuration
        config = new EntraIdConfiguration();
        config.setEnabled(true);
        
        // Use the correct tenant ID and client ID
        String tenantId = "2f8b8c95-8509-4822-a393-dc8f12d2c829";
        String clientId = "bcd5ed86-07a1-4528-9c44-32e86b3e79ee";
        String clientSecret = System.getenv("ENTRA_CLIENT_SECRET");
        
        // Check for placeholder values
        if (clientId != null && clientId.equals("your-client-id")) {
            logger.warn("ENTRA_CLIENT_ID contains placeholder value. Please set actual client ID.");
            config.setEnabled(false);
            return;
        }
        if (clientSecret != null && clientSecret.equals("your-client-secret")) {
            logger.warn("ENTRA_CLIENT_SECRET contains placeholder value. Please set actual client secret.");
            config.setEnabled(false);
            return;
        }
        
        // If any required environment variables are missing, disable the service
        if (clientId == null || clientSecret == null) {
            logger.warn("Missing required environment variables for Entra ID tests. Please set:\n" +
                       "  ENTRA_CLIENT_ID\n" +
                       "  ENTRA_CLIENT_SECRET");
            config.setEnabled(false);
            return;
        }
        
        config.setTenantId(tenantId);
        config.setClientId(clientId);
        config.setClientSecret(clientSecret);
        
        // Initialize service
        directoryService = new EntraIdDirectoryService(config);
        try {
            directoryService.initialize();
        } catch (Exception e) {
            logger.error("Failed to initialize directory service", e);
            config.setEnabled(false);
        }
    }

    @Test
    @DisplayName("Test authenticating with valid credentials")
    @EnabledIfEnvironmentVariable(named = "ENTRA_CLIENT_ID", matches = ".+")
    @EnabledIfEnvironmentVariable(named = "ENTRA_CLIENT_SECRET", matches = ".+")
    void testAuthenticateWithValidCredentials() {
        assumeTrue(config.isEnabled(), "Entra ID integration is not enabled. Please check environment variables.");

        final String testUserEmail = TEST_USER_EMAIL;
        final String testUserPassword = TEST_USER_PASSWORD;

        final Optional<String> phoneNumber = directoryService.authenticateAndGetPhoneNumber(testUserEmail, testUserPassword);
        assertTrue(phoneNumber.isPresent(), "Should get a phone number for valid credentials");
    }

    @Test
    @DisplayName("Test successful authentication and phone number retrieval")
    @EnabledIfEnvironmentVariable(named = "ENTRA_CLIENT_ID", matches = ".+")
    @EnabledIfEnvironmentVariable(named = "ENTRA_CLIENT_SECRET", matches = ".+")
    void testAuthenticateAndGetPhoneNumber() {
        assumeTrue(config.isEnabled(), "Entra ID integration is not enabled. Please check environment variables.");
        
        Optional<String> phoneNumber = directoryService.authenticateAndGetPhoneNumber(
            TEST_USER_EMAIL,
            TEST_USER_PASSWORD
        );
        
        assertTrue(phoneNumber.isPresent(), "Phone number should be retrieved successfully");
        String number = phoneNumber.get();
        assertTrue(number.startsWith("+"), "Phone number should be in E.164 format");
        assertTrue(number.length() >= 10, "Phone number should have at least 10 digits");
        
        logger.info("Successfully retrieved phone number: {}", number);
    }

    @Test
    @DisplayName("Test authentication with invalid credentials")
    void testInvalidCredentials() {
        assumeTrue(config.isEnabled(), "Entra ID integration is not enabled. Please check environment variables.");
        
        // Test with invalid password - should return empty Optional
        Optional<String> result = directoryService.authenticateAndGetPhoneNumber(
            TEST_USER_EMAIL,
            "wrongpassword"
        );
        assertFalse(result.isPresent(), "Authentication should fail with invalid password");
    }

    @ParameterizedTest
    @DisplayName("Test invalid email formats")
    @ValueSource(strings = {
        "",
        "notanemail",
        "@nodomain",
        "missing@",
        "spaces in@email.com"
    })
    void testInvalidEmailFormats(String invalidEmail) {
        assumeTrue(config.isEnabled(), "Entra ID integration is not enabled. Please check environment variables.");
        
        String testUserPassword = TEST_USER_PASSWORD;
        Optional<String> result = directoryService.authenticateAndGetPhoneNumber(
            invalidEmail,
            testUserPassword
        );
        assertFalse(result.isPresent(), "Should reject invalid email format");
    }

    @Test
    @DisplayName("Test service with empty credentials")
    void testEmptyCredentials() {
        assumeTrue(config.isEnabled(), "Entra ID integration is not enabled. Please check environment variables.");
        
        Optional<String> result = directoryService.authenticateAndGetPhoneNumber("", "");
        assertFalse(result.isPresent(), "Should reject empty credentials");
    }

    @Test
    @DisplayName("Test service initialization and cleanup")
    void testServiceLifecycle() {
        assumeTrue(config.isEnabled(), "Entra ID integration is not enabled. Please check environment variables.");
        
        // Test cleanup
        directoryService.cleanup();
        
        // Verify service is disabled after cleanup
        String testUserPassword = TEST_USER_PASSWORD;
        Optional<String> result = directoryService.authenticateAndGetPhoneNumber(
            TEST_USER_EMAIL,
            testUserPassword
        );
        assertFalse(result.isPresent(), "Service should be disabled after cleanup");
        
        // Re-initialize
        try {
            directoryService.initialize();
        } catch (Exception e) {
            fail("Failed to re-initialize service: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test connection to Microsoft Graph")
    @EnabledIfEnvironmentVariable(named = "ENTRA_CLIENT_ID", matches = ".+")
    @EnabledIfEnvironmentVariable(named = "ENTRA_CLIENT_SECRET", matches = ".+")
    void testConnection() {
        assumeTrue(config.isEnabled(), "Entra ID integration is not enabled. Please check environment variables.");
        
        // Create the credential
        final ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .tenantId("2f8b8c95-8509-4822-a393-dc8f12d2c829")
                .clientId(System.getenv("ENTRA_CLIENT_ID"))
                .clientSecret(System.getenv("ENTRA_CLIENT_SECRET"))
                .build();

        // Create the auth provider
        final TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(credential);

        // Build the Graph client
        GraphServiceClient graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();

        // Try to get users
        UserCollectionPage users = graphClient.users()
                .buildRequest()
                .select("displayName,mail,mobilePhone")
                .get();

        // Print user information
        for (User user : users.getCurrentPage()) {
            System.out.println("Found user:");
            System.out.println("  Display Name: " + user.displayName);
            System.out.println("  Email: " + user.mail);
            System.out.println("  Mobile: " + user.mobilePhone);
            System.out.println();
        }

        assertNotNull(users, "Should be able to retrieve users");
    }

    @Test
    @DisplayName("Test listing users in tenant")
    @EnabledIfEnvironmentVariable(named = "ENTRA_CLIENT_ID", matches = ".+")
    @EnabledIfEnvironmentVariable(named = "ENTRA_CLIENT_SECRET", matches = ".+")
    void testListUsers() {
        assumeTrue(config.isEnabled(), "Entra ID integration is not enabled. Please check environment variables.");
        
        // Create the credential
        final ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                .tenantId("2f8b8c95-8509-4822-a393-dc8f12d2c829")
                .clientId(System.getenv("ENTRA_CLIENT_ID"))
                .clientSecret(System.getenv("ENTRA_CLIENT_SECRET"))
                .build();

        // Create the auth provider
        final TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(credential);

        // Build the Graph client
        GraphServiceClient graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();

        // List users
        UserCollectionPage users = graphClient.users()
                .buildRequest()
                .select("displayName,userPrincipalName,mail")
                .get();

        // Log users for inspection
        users.getCurrentPage().forEach(user -> {
            logger.info("Found user: displayName={}, userPrincipalName={}, mail={}", 
                user.displayName, user.userPrincipalName, user.mail);
        });
    }
}
