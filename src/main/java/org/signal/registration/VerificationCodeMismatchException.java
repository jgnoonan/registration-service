package org.signal.registration;

/**
 * Exception thrown when a provided verification code does not match the expected value.
 */
public class VerificationCodeMismatchException extends Exception {
    public VerificationCodeMismatchException() {
        super("Verification code does not match");
    }

    public VerificationCodeMismatchException(String message) {
        super(message);
    }

    public VerificationCodeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
