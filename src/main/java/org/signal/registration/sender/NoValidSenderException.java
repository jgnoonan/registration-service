package org.signal.registration.sender;

/**
 * Indicates that no valid sender was available to handle a verification code send request.
 */
public class NoValidSenderException extends RuntimeException {
    public NoValidSenderException(String message) {
        super(message);
    }

    public NoValidSenderException(String message, Throwable cause) {
        super(message, cause);
    }
}
