/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.sender;

import com.google.i18n.phonenumbers.Phonenumber;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Locale;

/**
 * A trivial sender selection strategy that unconditionally selects a
 * {@link LastDigitsOfPhoneNumberVerificationCodeSender}. This strategy is intended only for local testing and should
 * never be used in a production environment.
 */
@Singleton
@Requires(env = {"dev", "test"})
@Requires(missingBeans = SenderSelectionStrategy.class)
public class LastDigitsOfPhoneNumberSenderSelectionStrategy implements SenderSelectionStrategy {

  private final LastDigitsOfPhoneNumberVerificationCodeSender loggingSender;

  public LastDigitsOfPhoneNumberSenderSelectionStrategy(final LastDigitsOfPhoneNumberVerificationCodeSender loggingSender) {
    this.loggingSender = loggingSender;
  }

  @Override
  public VerificationCodeSender chooseVerificationCodeSender(final MessageTransport transport,
      final Phonenumber.PhoneNumber phoneNumber,
      final List<Locale.LanguageRange> languageRanges,
      final ClientType clientType) {

    return loggingSender;
  }
}
