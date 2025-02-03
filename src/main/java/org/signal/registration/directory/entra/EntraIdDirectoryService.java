package org.signal.registration.directory.entra;

import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAccount;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.aad.msal4j.IConfidentialClientApplication;
import com.microsoft.aad.msal4j.IPublicClientApplication;
import com.microsoft.aad.msal4j.MsalException;
import com.microsoft.aad.msal4j.MsalServiceException;
import com.microsoft.aad.msal4j.PublicClientApplication;
import com.microsoft.aad.msal4j.SilentParameters;
import com.microsoft.aad.msal4j.UserNamePasswordParameters;
import com.microsoft.graph.authentication.BaseAuthenticationProvider;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.User;
import com.microsoft.graph.models.ObjectIdentity;
import com.microsoft.graph.requests.GraphServiceClient;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;
import org.signal.registration.directory.DirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

/**
 * Microsoft Entra ID implementation of the DirectoryService interface
 */
@Singleton
public class EntraIdDirectoryService implements DirectoryService {
    private static final Logger logger = LoggerFactory.getLogger(EntraIdDirectoryService.class);
    private static final String GRAPH_DEFAULT_SCOPE = "https://graph.microsoft.com/.default";
    
    private final EntraIdConfiguration config;
    private volatile GraphServiceClient graphClient;
    private volatile boolean initialized = false;
    private final Object initLock = new Object();

    public EntraIdDirectoryService(EntraIdConfiguration config) {
        logger.info("Creating EntraIdDirectoryService");
        this.config = config;
        logger.info("Configuration received - Tenant ID: {}, Client ID: {}, Test User: {}", 
                   config.getTenantId(), config.getClientId(), config.getTestUser());
    }

    @PostConstruct
    public void initialize() {
        logger.info("Initializing EntraIdDirectoryService");
        initializeIfNeeded();
    }

    private void initializeIfNeeded() {
        if (!initialized) {
            synchronized (initLock) {
                if (!initialized) {
                    try {
                        logger.info("Initializing Microsoft Entra ID service with configuration: tenantId={}, clientId={}", 
                            config.getTenantId(), config.getClientId());

                        // Create the credential for accessing Microsoft Graph API
                        logger.debug("Creating client credentials for Microsoft Graph API");
                        final TokenCredential credential = new ClientSecretCredentialBuilder()
                            .authorityHost("https://login.microsoftonline.com")
                            .tenantId(config.getTenantId())
                            .clientId(config.getClientId())
                            .clientSecret(config.getClientSecret())
                            .build();
                        logger.info("Client credentials built successfully");

                        // Create an auth provider for Microsoft Graph
                        logger.debug("Creating token credential auth provider");
                        final TokenCredentialAuthProvider authProvider = new TokenCredentialAuthProvider(
                            Collections.singletonList(GRAPH_DEFAULT_SCOPE), credential);
                        logger.info("Auth provider created successfully");

                        // Initialize Microsoft Graph client
                        logger.debug("Building Microsoft Graph client");
                        graphClient = GraphServiceClient.builder()
                            .authenticationProvider(authProvider)
                            .buildClient();
                        logger.info("Graph client built successfully");

                        // Test the connection by making a simple request
                        logger.info("Testing connection to Microsoft Entra ID using client credentials");
                        try {
                          // Just verify we can get an access token
                          graphClient.users()
                              .buildRequest()
                              .select("id")
                              .top(1)
                              .get();
                          logger.info("Successfully tested connection to Microsoft Entra ID");
                        } catch (Exception e) {
                          logger.error("Failed to test connection to Microsoft Entra ID", e);
                          throw new RuntimeException("Failed to initialize Microsoft Entra ID service", e);
                        }

                        initialized = true;
                        logger.info("EntraIdDirectoryService initialized successfully");
                    } catch (Exception e) {
                        logger.error("Failed to initialize Microsoft Entra ID service", e);
                        throw new RuntimeException("Failed to initialize Microsoft Entra ID service", e);
                    }
                }
            }
        }
    }

    @Override
    public Optional<String> authenticateAndGetPhoneNumber(String userId, String password) {
        initializeIfNeeded();

        if (userId == null || password == null || userId.trim().isEmpty() || password.trim().isEmpty()) {
            logger.error("Invalid credentials: userId or password is null or empty");
            return Optional.empty();
        }

        logger.info("Attempting authentication with UPN: {}", userId);
        try {
            return tryAuthentication(userId, password);
        } catch (Exception e) {
            logger.error("Authentication failed for user: {}", userId);
            logger.error("Error details: {}", e.getMessage());
            throw new RuntimeException("Authentication failed: " + e.getMessage(), e);
        }
    }

    private Optional<String> tryAuthentication(final String userId, final String password) {
        logger.info("Attempting to authenticate and verify user: {}", userId);
        logger.info("Password length: {}, contains spaces: {}, starts/ends with whitespace: {}", 
            password.length(), password.contains(" "), 
            (password.startsWith(" ") || password.endsWith(" ")));

        try {
            // First verify the user exists using client credentials (which we already have in graphClient)
            User user = graphClient.users()
                .buildRequest()
                .filter(String.format("userPrincipalName eq '%s'", userId))
                .select("id,displayName,userPrincipalName,mail,mobilePhone")
                .get()
                .getCurrentPage()
                .stream()
                .findFirst()
                .orElse(null);

            if (user != null) {
                logger.info("Found user: displayName={}, userPrincipalName={}, mail={}", 
                    user.displayName, user.userPrincipalName, user.mail);
                logger.info("User ID: {}", user.id);

                // Now verify password using token endpoint directly
                String tokenEndpoint = String.format("https://login.microsoftonline.com/%s/oauth2/v2.0/token", config.getTenantId());
                logger.info("Using token endpoint: {}", tokenEndpoint);

                // Create URL-encoded form parameters
                String formParams = String.format(
                    "client_id=%s&client_secret=%s&scope=https://graph.microsoft.com/.default&username=%s&password=%s&grant_type=password",
                    URLEncoder.encode(config.getClientId(), StandardCharsets.UTF_8),
                    URLEncoder.encode(config.getClientSecret(), StandardCharsets.UTF_8),
                    URLEncoder.encode(userId, StandardCharsets.UTF_8),
                    URLEncoder.encode(password, StandardCharsets.UTF_8)
                );

                // Create HTTP connection
                HttpURLConnection conn = (HttpURLConnection) new URL(tokenEndpoint).openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                // Write form parameters
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = formParams.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Get response
                int responseCode = conn.getResponseCode();
                logger.info("Token endpoint response code: {}", responseCode);

                // Read error response if available
                if (responseCode != 200) {
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        logger.error("Error response from token endpoint: {}", response.toString());
                    }
                }

                if (responseCode == 200) {
                    logger.info("Successfully authenticated user");
                    return Optional.ofNullable(normalizePhoneNumber(user.mobilePhone));
                } else {
                    logger.error("Failed to authenticate user. Response code: {}", responseCode);
                    throw new RuntimeException("Invalid credentials");
                }
            }
            
            logger.error("User not found: {}", userId);
            throw new RuntimeException("User not found");
        } catch (Exception e) {
            logger.error("Authentication error: {}", e.getMessage());
            throw new RuntimeException("Invalid credentials");
        }
    }

    private String normalizePhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return null;
        
        try {
            // Parse phone number (assume US if no country code provided)
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            PhoneNumber number = phoneUtil.parse(phoneNumber, "US");
            
            // Validate the number
            if (!phoneUtil.isValidNumber(number)) {
                logger.error("Invalid phone number format: {}", phoneNumber);
                return null;
            }
            
            // Format in E.164 format
            return phoneUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            logger.error("Error parsing phone number {}: {}", phoneNumber, e.getMessage());
            return null;
        }
    }

    @Override
    public void cleanup() {
        if (initialized) {
            logger.info("Cleaning up Microsoft Entra ID directory service");
            graphClient = null;
            initialized = false;
        }
    }
}
