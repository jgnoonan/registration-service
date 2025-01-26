/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.sender;

import org.signal.registration.session.FailedSendReason;

/**
 * Represents a failure to send a message via a specific transport.
 */
public record MessageTransportFailure(FailedSendReason failedSendReason, String message) {
    public MessageTransportFailure {
        if (message == null) {
            message = failedSendReason.toString();
        }
    }
}
