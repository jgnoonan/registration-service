/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.sender.twilio;

import com.twilio.exception.ApiException;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiExceptionsTest {

  @Test
  void extractErrorCode() {
    final ApiException apiException = new ApiException("Test error", 404);
    assertEquals(404, ApiExceptions.extractErrorCode(apiException));
  }

  @Test
  void convertToStatusRuntimeException() {
    final ApiException apiException = new ApiException("Test error", 404);
    final StatusRuntimeException statusRuntimeException = ApiExceptions.convertToStatusRuntimeException(apiException);
    assertEquals(Status.NOT_FOUND.getCode(), statusRuntimeException.getStatus().getCode());
  }
}
