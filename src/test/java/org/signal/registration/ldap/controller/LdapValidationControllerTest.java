package org.signal.registration.ldap.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.signal.registration.ldap.LdapService;
import org.signal.registration.ldap.dto.ValidationRequest;
import org.signal.registration.ldap.dto.ValidationResponse;
import org.signal.registration.ldap.util.ValidationCodeUtil;
import org.signal.registration.sender.prescribed.PrescribedVerificationCodeSender;
import org.signal.registration.sender.prescribed.PrescribedVerificationCodeRepository;
import org.signal.registration.sender.SenderSelectionStrategy;
import org.signal.registration.session.SessionRepository;
import org.signal.registration.session.RegistrationSession;
import org.signal.registration.ratelimit.RateLimiter;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

import java.time.Clock;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the LDAP validation controller.
 * Verifies the validation endpoint behavior for various scenarios.
 */
@MicronautTest(environments = "test", startApplication = false)
public class LdapValidationControllerTest {

    @Inject
    private LdapValidationController controller;

    private LdapService ldapService;
    private ValidationCodeUtil validationCodeUtil;
    private PrescribedVerificationCodeSender prescribedVerificationCodeSender;
    private PrescribedVerificationCodeRepository prescribedVerificationCodeRepository;
    private SenderSelectionStrategy senderSelectionStrategy;
    private SessionRepository sessionRepository;
    private Clock clock;

    @BeforeEach
    void setup() {
        ldapService = mock(LdapService.class);
        validationCodeUtil = mock(ValidationCodeUtil.class);
        prescribedVerificationCodeSender = mock(PrescribedVerificationCodeSender.class);
        prescribedVerificationCodeRepository = mock(PrescribedVerificationCodeRepository.class);
        senderSelectionStrategy = mock(SenderSelectionStrategy.class);
        sessionRepository = mock(SessionRepository.class);
        clock = Clock.systemUTC();
    }

    @MockBean(LdapService.class)
    LdapService ldapService() {
        return ldapService;
    }

    @MockBean(ValidationCodeUtil.class)
    ValidationCodeUtil validationCodeUtil() {
        return validationCodeUtil;
    }

    @MockBean(PrescribedVerificationCodeSender.class)
    PrescribedVerificationCodeSender prescribedVerificationCodeSender() {
        return prescribedVerificationCodeSender;
    }

    @MockBean(PrescribedVerificationCodeRepository.class)
    PrescribedVerificationCodeRepository prescribedVerificationCodeRepository() {
        return prescribedVerificationCodeRepository;
    }

    @MockBean(SenderSelectionStrategy.class)
    SenderSelectionStrategy senderSelectionStrategy() {
        return senderSelectionStrategy;
    }

    @MockBean(SessionRepository.class)
    SessionRepository sessionRepository() {
        return sessionRepository;
    }

    @MockBean(Clock.class)
    Clock clock() {
        return clock;
    }

    @Test
    void testSuccessfulValidation() {
        ValidationRequest request = new ValidationRequest("test@example.com", "password123");
        String phoneNumber = "+1234567890";
        String expectedValidationCode = "567890";
        
        when(ldapService.authenticateAndGetPhoneNumber(request.getUserId(), request.getPassword()))
            .thenReturn(Optional.of(phoneNumber));
            
        when(validationCodeUtil.generateValidationCode(phoneNumber))
            .thenReturn(expectedValidationCode);

        HttpResponse<ValidationResponse> response = controller.validateCredentials(request);

        assertEquals(HttpStatus.OK, response.status());
        ValidationResponse validationResponse = response.body();
        assertNotNull(validationResponse);
        assertTrue(validationResponse.isSuccess());
        assertEquals(phoneNumber, validationResponse.getPhoneNumber());
        assertEquals(expectedValidationCode, validationResponse.getValidationCode());
        assertNull(validationResponse.getError());
    }

    @Test
    void testFailedValidation() {
        ValidationRequest request = new ValidationRequest("test@example.com", "wrongpassword");
        
        when(ldapService.authenticateAndGetPhoneNumber(request.getUserId(), request.getPassword()))
            .thenReturn(Optional.empty());

        HttpResponse<ValidationResponse> response = controller.validateCredentials(request);

        assertEquals(HttpStatus.OK, response.status());
        ValidationResponse validationResponse = response.body();
        assertNotNull(validationResponse);
        assertFalse(validationResponse.isSuccess());
        assertNull(validationResponse.getPhoneNumber());
        assertNull(validationResponse.getValidationCode());
        assertEquals("Invalid credentials", validationResponse.getError());
    }

    @Test
    void testMissingCredentials() {
        ValidationRequest request = new ValidationRequest(null, null);

        HttpResponse<ValidationResponse> response = controller.validateCredentials(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.status());
        ValidationResponse validationResponse = response.body();
        assertNotNull(validationResponse);
        assertFalse(validationResponse.isSuccess());
        assertNull(validationResponse.getPhoneNumber());
        assertNull(validationResponse.getValidationCode());
        assertEquals("User ID and password are required", validationResponse.getError());
    }
}
