/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.sender.twilio;

import com.twilio.exception.ApiException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionsTest {

    @Test
    void testExtractErrorCode() {
        // Test a retriable error code
        ApiException retriableException = new ApiException("Too Many Requests", 20429, null, 500, null);
        assertEquals("20429", ApiExceptions.extractErrorCode(retriableException));

        // Test a non-retriable error code
        ApiException nonRetriableException = new ApiException("Invalid parameter", 60200, null, 400, null);
        assertEquals("60200", ApiExceptions.extractErrorCode(nonRetriableException));

        // Test a non-ApiException
        RuntimeException runtimeException = new RuntimeException("Test exception");
        assertNull(ApiExceptions.extractErrorCode(runtimeException));
    }

    @Test
    void testIsRetriable() {
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
