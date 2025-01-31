package org.signal.registration.directory.entra;

import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.MsalServiceException;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.UserNamePasswordParameters;
import com.microsoft.graph.authentication.BaseAuthenticationProvider;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserCollectionPage;
import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import okhttp3.Request;
import org.apache.commons.validator.routines.EmailValidator;
import org.signal.registration.directory.DirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Microsoft Entra ID implementation of the DirectoryService interface
 */
@Singleton
public class EntraIdDirectoryService implements DirectoryService {
    private static final Logger logger = LoggerFactory.getLogger(EntraIdDirectoryService.class);
    private static final String GRAPH_DEFAULT_SCOPE = "https://graph.microsoft.com/.default";
    
    private final EntraIdConfiguration config;
    private GraphServiceClient graphClient;
    private boolean initialized = false;

    public EntraIdDirectoryService(EntraIdConfiguration config) {
        this.config = config;
    }

    @PostConstruct
    public void initialize() {
        if (!config.isEnabled()) {
            logger.info("Microsoft Entra ID integration is disabled");
            return;
        }

        try {
            // Create the credential for accessing Microsoft Graph API
            final TokenCredential credential = new ClientSecretCredentialBuilder()
                .authorityHost("https://login.microsoftonline.com")
                .tenantId(config.getTenantId())
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .build();

            // Create an auth provider for Microsoft Graph
            final TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
                Collections.singletonList(GRAPH_DEFAULT_SCOPE), credential);

            // Initialize Microsoft Graph client
            graphClient = GraphServiceClient.builder()
                .authenticationProvider(authProvider)
                .buildClient();

            // Test the connection by making a simple request
            logger.info("Testing connection to Microsoft Entra ID");
            graphClient.users().buildRequest().top(1).get();
            logger.info("Successfully tested connection to Microsoft Entra ID");

            initialized = true;
            logger.info("Microsoft Entra ID directory service initialized successfully");
        } catch (Exception e) {
            logger.error("Failed to initialize Microsoft Entra ID directory service", e);
            throw e;
        }
    }

    @Override
    public Optional<String> authenticateAndGetPhoneNumber(String userId, String password) {
        if (!config.isEnabled() || !initialized) {
            logger.error("Microsoft Entra ID directory service is not enabled or not initialized");
            return Optional.empty();
        }

        if (userId == null || password == null || userId.trim().isEmpty() || password.trim().isEmpty()) {
            logger.error("Invalid email format provided: {}", userId);
            return Optional.empty();
        }

        if (!isValidEmail(userId)) {
            logger.error("Invalid email format provided: {}", userId);
            return Optional.empty();
        }

        try {
            // For guest users (containing #EXT#), use their original email address for authentication
            String authEmail = userId;
            String authority = "https://login.microsoftonline.com/";
            String tenantId = config.getTenantId();
            
            if (userId.contains("#EXT#")) {
                // Convert "user_domain.com#EXT#@tenant.onmicrosoft.com" to "user@domain.com"
                String[] parts = userId.split("#EXT#");
                if (parts.length > 0) {
                    authEmail = parts[0].replace("_", "@");
                }
                logger.info("Guest user detected, using original email: {}", authEmail);
            }
            
            logger.info("Authenticating user with email: {} using tenant: {}", authEmail, tenantId);
            
            // Use the common endpoint for authentication
            String authorityUrl = "https://login.microsoftonline.com/common";
            
            // Create a public client application for interactive authentication
            PublicClientApplication pca = PublicClientApplication.builder(config.getClientId())
                .authority(authorityUrl)
                .validateAuthority(false)  // Disable authority validation for external users
                .build();

            // Set up the parameters for username/password authentication
            Set<String> scopes = new HashSet<>();
            scopes.add("https://graph.microsoft.com/.default");

            UserNamePasswordParameters parameters = UserNamePasswordParameters.builder(
                scopes,
                authEmail,
                password.toCharArray()
            ).build();

            // Acquire token
            CompletableFuture<IAuthenticationResult> future = pca.acquireToken(parameters);
            try {
                IAuthenticationResult result = future.get(30, TimeUnit.SECONDS);  // Add timeout
                if (result == null || result.accessToken() == null) {
                    logger.info("Failed to get access token for user: {}", authEmail);
                    return Optional.empty();
                }
            } catch (Exception e) {
                logger.info("Failed to validate password for user: {}", authEmail);
                return Optional.empty();
            }

            // If we got here, the password is valid. Now search for the user using their email
            logger.info("Searching for user with filter: mail eq '{}' or userPrincipalName eq '{}'", userId, userId);
            List<User> users = graphClient.users()
                .buildRequest()
                .filter("mail eq '" + userId + "' or userPrincipalName eq '" + userId + "'")
                .select("displayName,mobilePhone,businessPhones,userPrincipalName,mail")
                .get()
                .getCurrentPage();

            if (users == null || users.isEmpty()) {
                logger.error("No user found with email: {}", userId);
                return Optional.empty();
            }

            User user = users.get(0);
            logger.info("Found user: displayName={}, userPrincipalName={}, mail={}", 
                user.displayName, user.userPrincipalName, user.mail);

            if (user.mobilePhone != null) {
                logger.info("Found mobile phone number for user");
                return Optional.of(normalizePhoneNumber(user.mobilePhone));
            } else if (user.businessPhones != null && !user.businessPhones.isEmpty()) {
                logger.info("Found business phone number for user");
                return Optional.of(normalizePhoneNumber(user.businessPhones.get(0)));
            }
            
            logger.error("No phone number found for user: {}", userId);
            return Optional.empty();

        } catch (Exception e) {
            logger.error("Failed to authenticate user: {}", userId, e);
            return Optional.empty();
        }
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            logger.error("Invalid email format provided: {}", email);
            return false;
        }

        // Check for standard email format using Apache Commons Validator
        EmailValidator emailValidator = EmailValidator.getInstance();
        
        // If it's a standard email, validate it directly
        if (emailValidator.isValid(email)) {
            return true;
        }
        
        // For Azure AD external users (with #EXT#), extract and validate the original email
        if (email.contains("#EXT#")) {
            String[] parts = email.split("#EXT#");
            if (parts.length > 0) {
                String originalEmail = parts[0].replace("_", "@");
                return emailValidator.isValid(originalEmail);
            }
        }
        
        logger.error("Invalid email format provided: {}", email);
        return false;
    }

    private String normalizePhoneNumber(String phoneNumber) {
        // Remove any non-digit characters except '+'
        String normalized = phoneNumber.replaceAll("[^\\d+]", "");
        
        // Ensure it starts with a '+'
        if (!normalized.startsWith("+")) {
            normalized = "+" + normalized;
        }
        
        return normalized;
    }

    @Override
    public void cleanup() {
        if (initialized) {
            logger.info("Cleaning up Microsoft Entra ID directory service");
            graphClient = null;
            initialized = false;
            config.setEnabled(false);  // Disable the service after cleanup
        }
    }
}
