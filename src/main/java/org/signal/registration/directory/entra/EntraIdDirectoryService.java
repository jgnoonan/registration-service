package org.signal.registration.directory.entra;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import org.signal.registration.directory.DirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Microsoft Entra ID implementation of the DirectoryService interface
 */
@Singleton
public class EntraIdDirectoryService implements DirectoryService {
    private static final Logger logger = LoggerFactory.getLogger(EntraIdDirectoryService.class);
    private static final String GRAPH_DEFAULT_SCOPE = "https://graph.microsoft.com/.default";
    
    private final EntraIdConfiguration config;
    private GraphServiceClient graphClient;

    public EntraIdDirectoryService(EntraIdConfiguration config) {
        this.config = config;
    }

    @Override
    public void initialize() throws Exception {
        if (!config.isEnabled()) {
            logger.info("Microsoft Entra ID integration is disabled");
            return;
        }

        logger.info("Initializing Microsoft Entra ID directory service");
        
        // Initialize Graph client with service principal credentials
        TokenCredential credential = createCredential();
        List<String> scopes = Collections.singletonList(GRAPH_DEFAULT_SCOPE);
        this.graphClient = GraphServiceClient
            .builder()
            .authenticationProvider(new TokenCredentialAuthProvider(scopes, credential))
            .buildClient();
            
        logger.info("Microsoft Entra ID directory service initialized successfully");
    }

    @Override
    public Optional<String> authenticateAndGetPhoneNumber(String userId, String password) {
        // Validate email format
        if (!EmailValidator.isValidEmail(userId)) {
            logger.error("Invalid email format provided: {}", userId);
            return Optional.empty();
        }

        try {
            // Use client credentials for authentication
            ClientSecretCredential clientCredential = new ClientSecretCredentialBuilder()
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .tenantId(config.getTenantId())
                .build();

            // Create Graph client with client credentials
            GraphServiceClient graphClient = GraphServiceClient.builder()
                .authenticationProvider(new TokenCredentialAuthProvider(
                    Collections.singletonList(GRAPH_DEFAULT_SCOPE), 
                    clientCredential
                ))
                .buildClient();

            // Search for the user by email
            List<User> users = graphClient.users()
                .buildRequest()
                .filter("mail eq '" + userId + "' or userPrincipalName eq '" + userId + "'")
                .select("displayName,mobilePhone")
                .get()
                .getCurrentPage();

            if (users != null && !users.isEmpty()) {
                User user = users.get(0);
                if (user.mobilePhone != null) {
                    return Optional.of(normalizePhoneNumber(user.mobilePhone));
                }
            }
            
            return Optional.empty();
        } catch (Exception e) {
            logger.error("Failed to authenticate user or retrieve phone number: {}", userId, e);
            return Optional.empty();
        }
    }

    private String normalizePhoneNumber(String phoneNumber) {
        // Remove any non-digit characters except '+'
        String normalized = phoneNumber.replaceAll("[^\\d+]", "");
        
        // Ensure it starts with '+'
        if (!normalized.startsWith("+")) {
            normalized = "+" + normalized;
        }
        
        return normalized;
    }

    @Override
    public void cleanup() {
        logger.info("Cleaning up Microsoft Entra ID directory service");
        graphClient = null;
    }

    private TokenCredential createCredential() {
        logger.info("Using client secret authentication for Microsoft Entra ID");
        return new ClientSecretCredentialBuilder()
            .tenantId(config.getTenantId())
            .clientId(config.getClientId())
            .clientSecret(config.getClientSecret())
            .build();
    }
}
