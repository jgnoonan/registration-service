/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

@Configuration
@Requires(property = "twilio.account-sid")
@Requires(property = "twilio.auth-token")
package org.signal.registration.sender.twilio;

import io.micronaut.context.annotation.Configuration;
import io.micronaut.context.annotation.Requires;
