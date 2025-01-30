package org.signal.registration.directory;

import java.util.Optional;

/**
 * Common interface for directory service implementations (LDAP, Azure AD, etc.)
 */
public interface DirectoryService {
    /**
     * Authenticates a user and retrieves their associated phone number
     *
     * @param userId the user identifier
     * @param password the user's password
     * @return Optional containing the phone number if authentication succeeds, empty otherwise
     */
    Optional<String> authenticateAndGetPhoneNumber(String userId, String password);

    /**
     * Initializes the directory service
     *
     * @throws Exception if initialization fails
     */
    void initialize() throws Exception;

    /**
     * Cleans up any resources used by the directory service
     */
    void cleanup();
}
