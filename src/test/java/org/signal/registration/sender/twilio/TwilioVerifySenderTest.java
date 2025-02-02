package org.signal.registration.sender.twilio;

import com.twilio.exception.ApiException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.signal.registration.sender.ApiClientInstrumenter;
import org.signal.registration.sender.twilio.ApiExceptions;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class TwilioVerifySenderTest {

    @Inject
    private ApiClientInstrumenter apiClientInstrumenter;

    @Test
    void testApiRetries() {
        // Test a retriable error code
        ApiException retriableException = new ApiException("Too Many Requests", 20429, null, 500, null);
        assertTrue(ApiExceptions.isRetriable(retriableException));

        // Test a non-retriable error code
        ApiException nonRetriableException = new ApiException("Invalid parameter", 60200, null, 400, null);
        assertFalse(ApiExceptions.isRetriable(nonRetriableException));

        // Test a non-ApiException
        RuntimeException runtimeException = new RuntimeException("Test exception");
        assertFalse(ApiExceptions.isRetriable(runtimeException));
    }
}
