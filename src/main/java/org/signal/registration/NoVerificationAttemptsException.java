package org.signal.registration;

/**
 * Exception thrown when attempting to verify a code for a session that has no remaining verification attempts.
 */
public class NoVerificationAttemptsException extends Exception {
    public NoVerificationAttemptsException() {
        super("No verification attempts remaining for this session");
    }

    public NoVerificationAttemptsException(String message) {
        super(message);
    }
}
