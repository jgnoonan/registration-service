package org.signal.registration;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.protobuf.ByteString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.signal.registration.sender.AttemptData;
import org.signal.registration.sender.ClientType;
import org.signal.registration.sender.MessageTransport;
import org.signal.registration.sender.NoValidSenderException;
import org.signal.registration.sender.SenderFraudBlockException;
import org.signal.registration.sender.SenderRejectedTransportException;
import org.signal.registration.sender.SenderSelectionStrategy;
import org.signal.registration.sender.VerificationCodeSender;
import org.signal.registration.session.RegistrationSession;
import org.signal.registration.session.SessionMetadata;
import org.signal.registration.session.SessionRepository;
import org.signal.registration.session.FailedSendReason;
import org.signal.registration.session.RegistrationAttempt;
import org.signal.registration.ratelimit.RateLimiter;
import org.signal.registration.ratelimit.RateLimitExceededException;
import org.signal.registration.ldap.LdapService;
import org.signal.registration.util.UUIDUtil;
import org.signal.registration.util.MessageTransports;
import org.signal.registration.VerificationCodeMismatchException;
import org.signal.registration.session.SessionNotFoundException;
import org.signal.registration.NoVerificationAttemptsException;

import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link RegistrationService} which handles phone number verification and registration sessions.
 */
class RegistrationServiceTest {
    // Service under test
    private RegistrationService registrationService;

    // Mocked dependencies
    private VerificationCodeSender sender;
    private SessionRepository sessionRepository;
    private RateLimiter<Phonenumber.PhoneNumber> sessionCreationRateLimiter;
    private RateLimiter<RegistrationSession> sendSmsVerificationCodeRateLimiter;
    private RateLimiter<RegistrationSession> sendVoiceVerificationCodeRateLimiter;
    private RateLimiter<RegistrationSession> checkVerificationCodeRateLimiter;
    private Clock clock;
    private SenderSelectionStrategy senderSelectionStrategy;
    private LdapService ldapService;

    // Test constants
    private static final String TEST_PHONE_NUMBER = "+919703804045";
    private static final Phonenumber.PhoneNumber PHONE_NUMBER;
    static {
        try {
            PHONE_NUMBER = PhoneNumberUtil.getInstance().parse(TEST_PHONE_NUMBER, null);
        } catch (NumberParseException e) {
            throw new RuntimeException("Failed to parse test phone number", e);
        }
    }
    private static final String SENDER_NAME = "mock-sender";
    private static final Duration SESSION_TTL = Duration.ofSeconds(17);
    private static final String VERIFICATION_CODE = getLast6DigitsOfPhoneNumber(TEST_PHONE_NUMBER);
    private static final byte[] VERIFICATION_CODE_BYTES = VERIFICATION_CODE.getBytes(StandardCharsets.UTF_8);
    private static final List<Locale.LanguageRange> LANGUAGE_RANGES = Locale.LanguageRange.parse("en,de");
    private static final ClientType CLIENT_TYPE = ClientType.IOS;
    private static final SessionMetadata SESSION_METADATA = SessionMetadata.newBuilder()
            .setAccountExistsWithE164(false)
            .build();
    private static final Instant CURRENT_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @BeforeEach
    void setUp() {
        // Initialize mocks
        sender = mock(VerificationCodeSender.class);
        sessionRepository = mock(SessionRepository.class);
        sessionCreationRateLimiter = mock(RateLimiter.class);
        sendSmsVerificationCodeRateLimiter = mock(RateLimiter.class);
        sendVoiceVerificationCodeRateLimiter = mock(RateLimiter.class);
        checkVerificationCodeRateLimiter = mock(RateLimiter.class);
        clock = mock(Clock.class);
        senderSelectionStrategy = mock(SenderSelectionStrategy.class);
        ldapService = mock(LdapService.class);

        // Configure default mock behavior
        when(clock.instant()).thenReturn(CURRENT_TIME);
        when(sender.getName()).thenReturn(SENDER_NAME);
        when(sessionCreationRateLimiter.checkRateLimit(any())).thenReturn(CompletableFuture.completedFuture(null));
        when(sendSmsVerificationCodeRateLimiter.checkRateLimit(any())).thenReturn(CompletableFuture.completedFuture(null));
        when(sendVoiceVerificationCodeRateLimiter.checkRateLimit(any())).thenReturn(CompletableFuture.completedFuture(null));
        when(checkVerificationCodeRateLimiter.checkRateLimit(any())).thenReturn(CompletableFuture.completedFuture(null));

        // Create service instance
        registrationService = new RegistrationService(
                senderSelectionStrategy,
                sessionRepository,
                sessionCreationRateLimiter,
                sendSmsVerificationCodeRateLimiter,
                sendVoiceVerificationCodeRateLimiter,
                checkVerificationCodeRateLimiter,
                List.of(sender),
                clock,
                ldapService,
                true
        );
    }

    @Test
    void createSession() {
        // Setup session repository mock
        when(sessionRepository.createSession(any(), any(), any()))
            .thenAnswer(invocation -> {
                Phonenumber.PhoneNumber phoneNumber = invocation.getArgument(0);
                SessionMetadata metadata = invocation.getArgument(1);
                return CompletableFuture.completedFuture(RegistrationSession.newBuilder()
                    .setPhoneNumber(PhoneNumberUtil.getInstance().format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164))
                    .setId(ByteString.copyFrom(UUID.randomUUID().toString().getBytes()))
                    .build());
            });

        // Execute test
        RegistrationSession session = registrationService.createRegistrationSession(PHONE_NUMBER, SESSION_METADATA).join();

        // Verify
        assertNotNull(session);
        assertEquals(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164),
            session.getPhoneNumber());
        assertFalse(session.getId().isEmpty());
        verify(sessionCreationRateLimiter).checkRateLimit(eq(PHONE_NUMBER));
        verify(sessionRepository).createSession(eq(PHONE_NUMBER), eq(SESSION_METADATA), any());
    }

    @Test
    void createSessionRateLimited() {
        // Setup rate limiter to reject
        RateLimitExceededException rateLimitExceededException = new RateLimitExceededException(Duration.ZERO);
        when(sessionCreationRateLimiter.checkRateLimit(any()))
            .thenReturn(CompletableFuture.failedFuture(rateLimitExceededException));

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.createRegistrationSession(PHONE_NUMBER, SESSION_METADATA).join());

        assertEquals(rateLimitExceededException, exception.getCause());
        verify(sessionRepository, never()).createSession(any(), any(), any());
    }

    @Test
    void sendVerificationCode() {
        // Setup
        String remoteId = UUID.randomUUID().toString();
        UUID sessionId = UUID.randomUUID();
        
        // Create initial session
        when(sessionRepository.createSession(any(), any(), any()))
            .thenReturn(CompletableFuture.completedFuture(RegistrationSession.newBuilder()
                .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
                .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
                .build()));
        
        RegistrationSession initialSession = registrationService.createRegistrationSession(PHONE_NUMBER, SESSION_METADATA).join();

        // Setup sender mock
        when(sender.sendVerificationCode(any(MessageTransport.class), any(Phonenumber.PhoneNumber.class), anyList(), any(ClientType.class)))
            .thenReturn(CompletableFuture.completedFuture(new AttemptData(Optional.of(remoteId), VERIFICATION_CODE_BYTES)));

        // Setup sender selection strategy
        when(senderSelectionStrategy.chooseVerificationCodeSender(
            any(MessageTransport.class),
            any(Phonenumber.PhoneNumber.class),
            any(List.class),
            any(ClientType.class),
            any(),
            any()))
            .thenReturn(new SenderSelectionStrategy.SenderSelection(sender, SenderSelectionStrategy.SelectionReason.CONFIGURED));

        // Execute test
        RegistrationSession updatedSession = registrationService.sendVerificationCode(
            MessageTransport.SMS,
            UUIDUtil.uuidFromByteString(initialSession.getId()),
            SENDER_NAME,
            Locale.LanguageRange.parse("en,de"),
            CLIENT_TYPE
        ).join();

        // Verify
        assertNotNull(updatedSession);
        assertEquals(1, updatedSession.getRegistrationAttemptsCount());
        assertEquals(remoteId, updatedSession.getRegistrationAttempts(0).getRemoteId());
        assertEquals(MessageTransport.SMS, updatedSession.getRegistrationAttempts(0).getMessageTransport());
        
        verify(sendSmsVerificationCodeRateLimiter).checkRateLimit(any());
        verify(sendVoiceVerificationCodeRateLimiter, never()).checkRateLimit(any());
        verify(sessionRepository).updateSession(eq(UUIDUtil.uuidFromByteString(initialSession.getId())), any());
    }

    @Test
    void sendVerificationCodeRateLimited() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        RateLimitExceededException rateLimitExceededException = new RateLimitExceededException(Duration.ZERO);
        
        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(RegistrationSession.newBuilder()
                .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
                .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
                .build()));

        when(sendSmsVerificationCodeRateLimiter.checkRateLimit(any()))
            .thenReturn(CompletableFuture.failedFuture(rateLimitExceededException));

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.sendVerificationCode(
                MessageTransport.SMS,
                sessionId,
                SENDER_NAME,
                Locale.LanguageRange.parse("en,de"),
                CLIENT_TYPE
            ).join());

        assertEquals(rateLimitExceededException, exception.getCause());
        verify(sender, never()).sendVerificationCode(any(), any(), any(), any());
        verify(sessionRepository, never()).updateSession(any(), any());
    }

    @Test
    void checkVerificationCode() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        String remoteId = UUID.randomUUID().toString();
        
        RegistrationSession session = RegistrationSession.newBuilder()
            .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
            .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
            .addRegistrationAttempts(RegistrationAttempt.newBuilder()
                .setMessageTransport(MessageTransports.getRpcMessageTransportFromSenderTransport(MessageTransport.SMS))
                .setSenderData(ByteString.copyFrom(VERIFICATION_CODE_BYTES))
                .setRemoteId(remoteId)
                .build())
            .build();

        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(session));

        when(sessionRepository.updateSession(eq(sessionId), any()))
            .thenAnswer(invocation -> CompletableFuture.completedFuture(invocation.getArgument(1)));

        // Execute test
        RegistrationSession verifiedSession = registrationService.checkVerificationCode(sessionId, VERIFICATION_CODE).join();

        // Verify
        assertNotNull(verifiedSession);
        assertEquals(VERIFICATION_CODE, verifiedSession.getVerifiedCode());
        verify(checkVerificationCodeRateLimiter).checkRateLimit(any());
        verify(sessionRepository).updateSession(eq(sessionId), any());
    }

    @Test
    void checkVerificationCodeRateLimited() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        RateLimitExceededException rateLimitExceededException = new RateLimitExceededException(Duration.ZERO);

        when(checkVerificationCodeRateLimiter.checkRateLimit(any()))
            .thenReturn(CompletableFuture.failedFuture(rateLimitExceededException));

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.checkVerificationCode(sessionId, VERIFICATION_CODE).join());

        assertEquals(rateLimitExceededException, exception.getCause());
        verify(sessionRepository, never()).updateSession(any(), any());
    }

    @Test
    void createSessionWithLdapAuthentication() {
        // Setup
        String userId = "testuser";
        String password = "testpass";
        String mobileNumber = TEST_PHONE_NUMBER;  // Using the test phone number
        SessionMetadata ldapMetadata = SessionMetadata.newBuilder()
            .setUsername(userId)
            .setPassword(password)
            .build();

        // Mock LDAP service to return a valid phone number from the 'mobile' attribute
        when(ldapService.authenticateAndGetPhoneNumber(eq(userId), eq(password)))
            .thenReturn(Optional.of(mobileNumber));

        when(sessionRepository.createSession(any(), any(), any()))
            .thenAnswer(invocation -> {
                Phonenumber.PhoneNumber phone = invocation.getArgument(0);
                return CompletableFuture.completedFuture(RegistrationSession.newBuilder()
                    .setPhoneNumber(PhoneNumberUtil.getInstance().format(phone, PhoneNumberUtil.PhoneNumberFormat.E164))
                    .setId(ByteString.copyFrom(UUID.randomUUID().toString().getBytes()))
                    .build());
            });

        // Execute test
        RegistrationSession session = registrationService.createRegistrationSession(PHONE_NUMBER, ldapMetadata).join();

        // Verify
        assertNotNull(session);
        verify(ldapService).authenticateAndGetPhoneNumber(eq(userId), eq(password));
        verify(sessionCreationRateLimiter).checkRateLimit(any());
        verify(sessionRepository).createSession(any(), eq(ldapMetadata), any());
    }

    @Test
    void createSessionWithInvalidLdapCredentials() {
        // Setup
        String userId = "invaliduser";
        String password = "wrongpass";
        SessionMetadata ldapMetadata = SessionMetadata.newBuilder()
            .setUsername(userId)
            .setPassword(password)
            .build();

        // Mock LDAP service to return empty when credentials are invalid
        when(ldapService.authenticateAndGetPhoneNumber(eq(userId), eq(password)))
            .thenReturn(Optional.empty());

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.createRegistrationSession(PHONE_NUMBER, ldapMetadata).join());

        assertTrue(exception.getCause() instanceof AuthenticationException);
        verify(ldapService).authenticateAndGetPhoneNumber(eq(userId), eq(password));
        verify(sessionRepository, never()).createSession(any(), any(), any());
    }

    @Test
    void createSessionWithEmptyLdapPhoneNumber() {
        // Setup
        String userId = "userWithNoPhone";
        String password = "validpass";
        SessionMetadata ldapMetadata = SessionMetadata.newBuilder()
            .setUsername(userId)
            .setPassword(password)
            .build();

        // Mock LDAP service to return empty phone number (mobile attribute is blank)
        when(ldapService.authenticateAndGetPhoneNumber(eq(userId), eq(password)))
            .thenReturn(Optional.empty());

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.createRegistrationSession(PHONE_NUMBER, ldapMetadata).join());

        assertTrue(exception.getCause() instanceof AuthenticationException);
        verify(ldapService).authenticateAndGetPhoneNumber(eq(userId), eq(password));
        verify(sessionRepository, never()).createSession(any(), any(), any());
    }

    @Test
    void createSessionWithLdapConnectionFailure() {
        // Setup
        String userId = "testuser";
        String password = "testpass";
        SessionMetadata ldapMetadata = SessionMetadata.newBuilder()
            .setUsername(userId)
            .setPassword(password)
            .build();

        // Mock LDAP service to simulate connection failure
        when(ldapService.authenticateAndGetPhoneNumber(eq(userId), eq(password)))
            .thenThrow(new RuntimeException("LDAP connection failed"));

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.createRegistrationSession(PHONE_NUMBER, ldapMetadata).join());

        assertTrue(exception.getCause() instanceof RuntimeException);
        assertEquals("LDAP connection failed", exception.getCause().getMessage());
        verify(ldapService).authenticateAndGetPhoneNumber(eq(userId), eq(password));
        verify(sessionRepository, never()).createSession(any(), any(), any());
    }

    @Test
    void sendVerificationCodeTransportNotAllowed() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(RegistrationSession.newBuilder()
                .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
                .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
                .build()));

        when(sender.sendVerificationCode(any(MessageTransport.class), any(Phonenumber.PhoneNumber.class), anyList(), any(ClientType.class)))
            .thenReturn(CompletableFuture.failedFuture(new SenderRejectedTransportException("Transport not allowed")));

        when(senderSelectionStrategy.chooseVerificationCodeSender(
            any(MessageTransport.class),
            any(Phonenumber.PhoneNumber.class),
            any(List.class),
            any(ClientType.class),
            any(),
            any()))
            .thenReturn(new SenderSelectionStrategy.SenderSelection(sender, SenderSelectionStrategy.SelectionReason.CONFIGURED));

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.sendVerificationCode(
                MessageTransport.SMS,
                sessionId,
                SENDER_NAME,
                Locale.LanguageRange.parse("en,de"),
                CLIENT_TYPE
            ).join());

        assertTrue(exception.getCause() instanceof TransportNotAllowedException);
        verify(sessionRepository).updateSession(eq(sessionId), any());
    }

    @Test
    void sendVerificationCodeFraudBlock() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(RegistrationSession.newBuilder()
                .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
                .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
                .build()));

        when(sender.sendVerificationCode(any(MessageTransport.class), any(Phonenumber.PhoneNumber.class), anyList(), any(ClientType.class)))
            .thenReturn(CompletableFuture.failedFuture(new SenderFraudBlockException("Fraud detected")));

        when(senderSelectionStrategy.chooseVerificationCodeSender(
            any(MessageTransport.class),
            any(Phonenumber.PhoneNumber.class),
            any(List.class),
            any(ClientType.class),
            any(),
            any()))
            .thenReturn(new SenderSelectionStrategy.SenderSelection(sender, SenderSelectionStrategy.SelectionReason.CONFIGURED));

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.sendVerificationCode(
                MessageTransport.SMS,
                sessionId,
                SENDER_NAME,
                Locale.LanguageRange.parse("en,de"),
                CLIENT_TYPE
            ).join());

        assertTrue(exception.getCause() instanceof SenderFraudBlockException);
        verify(sessionRepository).updateSession(eq(sessionId), any());
    }

    @Test
    void sendVerificationCodeNoValidSender() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(RegistrationSession.newBuilder()
                .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
                .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
                .build()));

        when(senderSelectionStrategy.chooseVerificationCodeSender(
            any(MessageTransport.class),
            any(Phonenumber.PhoneNumber.class),
            any(List.class),
            any(ClientType.class),
            any(),
            any()))
            .thenReturn(null);

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.sendVerificationCode(
                MessageTransport.SMS,
                sessionId,
                SENDER_NAME,
                Locale.LanguageRange.parse("en,de"),
                CLIENT_TYPE
            ).join());

        assertTrue(exception.getCause() instanceof NoValidSenderException);
        verify(sender, never()).sendVerificationCode(any(), any(), any(), any());
    }

    @Test
    void checkVerificationCodeInvalidCode() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        String remoteId = UUID.randomUUID().toString();
        String wrongCode = "111111";  // Different from last 6 digits of phone number
        
        RegistrationSession session = RegistrationSession.newBuilder()
            .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
            .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
            .addRegistrationAttempts(RegistrationAttempt.newBuilder()
                .setMessageTransport(MessageTransports.getRpcMessageTransportFromSenderTransport(MessageTransport.SMS))
                .setSenderData(ByteString.copyFrom(VERIFICATION_CODE_BYTES))
                .setRemoteId(remoteId)
                .build())
            .build();

        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(session));

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.checkVerificationCode(sessionId, wrongCode).join());

        assertTrue(exception.getCause() instanceof VerificationCodeMismatchException);
        verify(checkVerificationCodeRateLimiter).checkRateLimit(any());
    }

    @Test
    void checkVerificationCodeExpiredSession() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(null));

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.checkVerificationCode(sessionId, VERIFICATION_CODE).join());

        assertTrue(exception.getCause() instanceof SessionNotFoundException);
        verify(checkVerificationCodeRateLimiter, never()).checkRateLimit(any());
    }

    @Test
    void checkVerificationCodeNoAttempts() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        
        RegistrationSession session = RegistrationSession.newBuilder()
            .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
            .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
            .build();

        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(session));

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.checkVerificationCode(sessionId, VERIFICATION_CODE).join());

        assertTrue(exception.getCause() instanceof NoVerificationAttemptsException);
        verify(checkVerificationCodeRateLimiter).checkRateLimit(any());
    }

    @Test
    void sendVoiceVerificationCode() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        String remoteId = UUID.randomUUID().toString();
        
        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(RegistrationSession.newBuilder()
                .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
                .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
                .build()));

        when(sender.sendVerificationCode(any(MessageTransport.class), any(Phonenumber.PhoneNumber.class), anyList(), any(ClientType.class)))
            .thenReturn(CompletableFuture.completedFuture(new AttemptData(Optional.of(remoteId), VERIFICATION_CODE_BYTES)));

        when(senderSelectionStrategy.chooseVerificationCodeSender(
            any(MessageTransport.class),
            any(Phonenumber.PhoneNumber.class),
            any(List.class),
            any(ClientType.class),
            any(),
            any()))
            .thenReturn(new SenderSelectionStrategy.SenderSelection(sender, SenderSelectionStrategy.SelectionReason.CONFIGURED));

        // Execute test
        RegistrationSession updatedSession = registrationService.sendVerificationCode(
            MessageTransport.VOICE,
            sessionId,
            SENDER_NAME,
            Locale.LanguageRange.parse("en,de"),
            CLIENT_TYPE
        ).join();

        // Verify
        assertNotNull(updatedSession);
        assertEquals(1, updatedSession.getRegistrationAttemptsCount());
        assertEquals(MessageTransport.VOICE, updatedSession.getRegistrationAttempts(0).getMessageTransport());
        verify(sendVoiceVerificationCodeRateLimiter).checkRateLimit(any());
        verify(sendSmsVerificationCodeRateLimiter, never()).checkRateLimit(any());
    }

    @Test
    void sessionExpiration() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        Instant expiredTime = CURRENT_TIME.minus(Duration.ofMinutes(11));  // Past the SESSION_TTL_AFTER_LAST_ACTION

        RegistrationSession expiredSession = RegistrationSession.newBuilder()
            .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
            .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
            .setExpirationEpochMillis(expiredTime.toEpochMilli())
            .build();

        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(expiredSession));

        // Execute and verify
        CompletionException exception = assertThrows(CompletionException.class,
            () -> registrationService.sendVerificationCode(
                MessageTransport.SMS,
                sessionId,
                SENDER_NAME,
                Locale.LanguageRange.parse("en,de"),
                CLIENT_TYPE
            ).join());

        assertTrue(exception.getCause() instanceof SessionNotFoundException);
    }

    @Test
    void multipleVerificationAttempts() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        String firstRemoteId = UUID.randomUUID().toString();
        String secondRemoteId = UUID.randomUUID().toString();
        
        RegistrationSession initialSession = RegistrationSession.newBuilder()
            .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
            .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
            .addRegistrationAttempts(RegistrationAttempt.newBuilder()
                .setMessageTransport(MessageTransports.getRpcMessageTransportFromSenderTransport(MessageTransport.SMS))
                .setSenderData(ByteString.copyFrom(VERIFICATION_CODE_BYTES))
                .setRemoteId(firstRemoteId)
                .build())
            .build();

        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(initialSession));

        when(sender.sendVerificationCode(any(MessageTransport.class), any(Phonenumber.PhoneNumber.class), anyList(), any(ClientType.class)))
            .thenReturn(CompletableFuture.completedFuture(new AttemptData(Optional.of(secondRemoteId), VERIFICATION_CODE_BYTES)));

        when(senderSelectionStrategy.chooseVerificationCodeSender(
            any(MessageTransport.class),
            any(Phonenumber.PhoneNumber.class),
            any(List.class),
            any(ClientType.class),
            any(),
            any()))
            .thenReturn(new SenderSelectionStrategy.SenderSelection(sender, SenderSelectionStrategy.SelectionReason.CONFIGURED));

        // Execute test - send a second verification code
        RegistrationSession updatedSession = registrationService.sendVerificationCode(
            MessageTransport.VOICE,
            sessionId,
            SENDER_NAME,
            Locale.LanguageRange.parse("en,de"),
            CLIENT_TYPE
        ).join();

        // Verify
        assertNotNull(updatedSession);
        assertEquals(2, updatedSession.getRegistrationAttemptsCount());
        assertEquals(MessageTransport.SMS, updatedSession.getRegistrationAttempts(0).getMessageTransport());
        assertEquals(MessageTransport.VOICE, updatedSession.getRegistrationAttempts(1).getMessageTransport());
        assertEquals(firstRemoteId, updatedSession.getRegistrationAttempts(0).getRemoteId());
        assertEquals(secondRemoteId, updatedSession.getRegistrationAttempts(1).getRemoteId());
    }

    @Test
    void buildSessionMetadata() {
        // Setup
        UUID sessionId = UUID.randomUUID();
        String remoteId = UUID.randomUUID().toString();
        
        RegistrationSession session = RegistrationSession.newBuilder()
            .setId(ByteString.copyFrom(sessionId.toString().getBytes()))
            .setPhoneNumber(PhoneNumberUtil.getInstance().format(PHONE_NUMBER, PhoneNumberUtil.PhoneNumberFormat.E164))
            .addRegistrationAttempts(RegistrationAttempt.newBuilder()
                .setMessageTransport(MessageTransports.getRpcMessageTransportFromSenderTransport(MessageTransport.SMS))
                .setSenderData(ByteString.copyFrom(VERIFICATION_CODE_BYTES))
                .setRemoteId(remoteId)
                .build())
            .setVerifiedCode(VERIFICATION_CODE)
            .build();

        when(sessionRepository.getSession(eq(sessionId)))
            .thenReturn(CompletableFuture.completedFuture(session));

        // Execute test
        RegistrationSession retrievedSession = registrationService.getRegistrationSession(sessionId).join();

        // Verify
        assertNotNull(retrievedSession);
        assertEquals(session.getPhoneNumber(), retrievedSession.getPhoneNumber());
        assertEquals(session.getVerifiedCode(), retrievedSession.getVerifiedCode());
        assertEquals(1, retrievedSession.getRegistrationAttemptsCount());
        assertEquals(MessageTransport.SMS, retrievedSession.getRegistrationAttempts(0).getMessageTransport());
        assertEquals(remoteId, retrievedSession.getRegistrationAttempts(0).getRemoteId());
    }

    private static String getLast6DigitsOfPhoneNumber(String phoneNumber) {
        return phoneNumber.substring(phoneNumber.length() - 6);
    }
}
