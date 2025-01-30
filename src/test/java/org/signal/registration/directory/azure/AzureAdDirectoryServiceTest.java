package org.signal.registration.directory.entra;

import com.microsoft.graph.models.User;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.UserRequest;
import com.microsoft.graph.requests.UserRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EntraIdDirectoryServiceTest {

    @Mock
    private EntraIdConfiguration config;

    @Mock
    private GraphServiceClient graphClient;

    @Mock
    private UserRequestBuilder userRequestBuilder;

    @Mock
    private UserRequest userRequest;

    private EntraIdDirectoryService directoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(config.isEnabled()).thenReturn(true);
        directoryService = new EntraIdDirectoryService(config);
    }

    @Test
    void testAuthenticateAndGetPhoneNumber_Success() {
        // Arrange
        String userId = "test.user@example.com";
        String expectedPhone = "+1234567890";
        
        User user = new User();
        user.mobilePhone = expectedPhone;

        when(graphClient.users(userId)).thenReturn(userRequestBuilder);
        when(userRequestBuilder.buildRequest()).thenReturn(userRequest);
        when(userRequest.select(anyString())).thenReturn(userRequest);
        when(userRequest.get()).thenReturn(user);

        // Act
        Optional<String> result = directoryService.authenticateAndGetPhoneNumber(userId, "password");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedPhone, result.get());
    }

    @Test
    void testAuthenticateAndGetPhoneNumber_UserNotFound() {
        // Arrange
        String userId = "nonexistent@example.com";
        
        when(graphClient.users(userId)).thenReturn(userRequestBuilder);
        when(userRequestBuilder.buildRequest()).thenReturn(userRequest);
        when(userRequest.select(anyString())).thenReturn(userRequest);
        when(userRequest.get()).thenReturn(null);

        // Act
        Optional<String> result = directoryService.authenticateAndGetPhoneNumber(userId, "password");

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testAuthenticateAndGetPhoneNumber_ServiceDisabled() {
        // Arrange
        when(config.isEnabled()).thenReturn(false);

        // Act
        Optional<String> result = directoryService.authenticateAndGetPhoneNumber("user@example.com", "password");

        // Assert
        assertFalse(result.isPresent());
        verifyNoInteractions(graphClient);
    }
}
