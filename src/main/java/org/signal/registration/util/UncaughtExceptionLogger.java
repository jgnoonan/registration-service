/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.util;

import io.micronaut.context.annotation.Context;
import jakarta.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.annotation.PostConstruct;

@Context
class UncaughtExceptionLogger {

  private static final Logger logger = LoggerFactory.getLogger(UncaughtExceptionLogger.class);

  @PostConstruct
  void register() {
    @jakarta.annotation.Nullable final Thread.UncaughtExceptionHandler current = Thread.getDefaultUncaughtExceptionHandler();

    if (current != null) {
      logger.warn("Uncaught exception handler already exists: {}", current);
      return;
    }

    // Log metadata (assuming logs are sent as JSON) will automatically include the current thread and exception details
    Thread.setDefaultUncaughtExceptionHandler((t, e) -> logger.error("Uncaught exception", e));
  }
}
