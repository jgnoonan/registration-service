/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.sender;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

/**
 * A test implementation of VerificationCodeGenerator that uses the last 6 digits of the phone number as the verification code.
 */
@Singleton
@Primary
@Requires(env = "test")
public class TestVerificationCodeGenerator extends VerificationCodeGenerator {
    private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();

    @Override
    public String generateVerificationCode() {
        throw new UnsupportedOperationException("This method should not be called in test environment. Use generateVerificationCodeForNumber instead.");
    }

    public String generateVerificationCodeForNumber(Phonenumber.PhoneNumber phoneNumber) {
        String fullNumber = PHONE_NUMBER_UTIL.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
        // Remove the '+' and take last 6 digits, padding with zeros if needed
        String numberOnly = fullNumber.substring(1);
        return String.format("%06d", Long.parseLong(numberOnly.substring(Math.max(0, numberOnly.length() - 6))));
    }
}
