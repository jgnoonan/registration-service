package org.signal.registration.ldap.util;

import jakarta.inject.Singleton;

@Singleton
public class ValidationCodeUtil {
    
    /**
     * Extracts the last 6 digits from a phone number to use as a validation code.
     * If the phone number has fewer than 6 digits, it will pad with zeros at the start.
     *
     * @param phoneNumber The phone number to extract digits from
     * @return A 6-digit validation code
     */
    public String generateValidationCode(String phoneNumber) {
        // Remove any non-digit characters
        String digitsOnly = phoneNumber.replaceAll("\\D", "");
        
        // Get last 6 digits, or pad with zeros if needed
        int length = digitsOnly.length();
        if (length >= 6) {
            return digitsOnly.substring(length - 6);
        } else {
            return "0".repeat(6 - length) + digitsOnly;
        }
    }
}
