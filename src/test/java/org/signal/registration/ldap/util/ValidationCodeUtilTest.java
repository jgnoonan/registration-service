package org.signal.registration.ldap.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ValidationCodeUtilTest {

    private ValidationCodeUtil validationCodeUtil;

    @BeforeEach
    void setUp() {
        validationCodeUtil = new ValidationCodeUtil();
    }

    @Test
    void testGenerateValidationCodeWithLongNumber() {
        String phoneNumber = "+1234567890";
        String validationCode = validationCodeUtil.generateValidationCode(phoneNumber);
        assertEquals("567890", validationCode);
    }

    @Test
    void testGenerateValidationCodeWithShortNumber() {
        String phoneNumber = "12345";
        String validationCode = validationCodeUtil.generateValidationCode(phoneNumber);
        assertEquals("012345", validationCode);
    }

    @Test
    void testGenerateValidationCodeWithNonDigits() {
        String phoneNumber = "+1 (234) 567-890";
        String validationCode = validationCodeUtil.generateValidationCode(phoneNumber);
        assertEquals("567890", validationCode);
    }
}
