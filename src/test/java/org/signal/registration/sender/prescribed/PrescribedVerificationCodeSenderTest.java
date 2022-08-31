/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.sender.prescribed;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.signal.registration.sender.ClientType;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PrescribedVerificationCodeSenderTest {

  private static final Phonenumber.PhoneNumber PRESCRIBED_CODE_NUMBER =
      PhoneNumberUtil.getInstance().getExampleNumber("US");

  private static final Phonenumber.PhoneNumber NON_PRESCRIBED_CODE_NUMBER =
      PhoneNumberUtil.getInstance().getExampleNumber("DE");

  private static final String VERIFICATION_CODE = RandomStringUtils.randomNumeric(6);

  private PrescribedVerificationCodeSender sender;

  @BeforeEach
  void setUp() {
    final PrescribedVerificationCodeConfiguration configuration = new PrescribedVerificationCodeConfiguration();
    configuration.setVerificationCodes(Map.of(PRESCRIBED_CODE_NUMBER, VERIFICATION_CODE));

    sender = new PrescribedVerificationCodeSender(configuration);
  }

  @Test
  void supportsDestination() {
    assertTrue(sender.supportsDestination(PRESCRIBED_CODE_NUMBER, Collections.emptyList(), ClientType.UNKNOWN));
    assertFalse(sender.supportsDestination(NON_PRESCRIBED_CODE_NUMBER, Collections.emptyList(), ClientType.UNKNOWN));
  }

  @Test
  void sendVerificationCode() throws InvalidProtocolBufferException {
    {
      final PrescribedVerificationCodeSessionData sessionData =
          PrescribedVerificationCodeSessionData.parseFrom(
              sender.sendVerificationCode(PRESCRIBED_CODE_NUMBER, Collections.emptyList(), ClientType.UNKNOWN).join());

      assertEquals(VERIFICATION_CODE, sessionData.getVerificationCode());
    }

    {
      final CompletionException completionException = assertThrows(CompletionException.class, () ->
          sender.sendVerificationCode(NON_PRESCRIBED_CODE_NUMBER, Collections.emptyList(), ClientType.UNKNOWN).join());

      assertTrue(completionException.getCause() instanceof IllegalArgumentException);
    }
  }

  @Test
  void checkVerificationCode() {
    final byte[] sessionDataBytes =
        PrescribedVerificationCodeSessionData.newBuilder().setVerificationCode(VERIFICATION_CODE).build().toByteArray();

    assertTrue(sender.checkVerificationCode(VERIFICATION_CODE, sessionDataBytes).join());
    assertFalse(sender.checkVerificationCode(VERIFICATION_CODE + "-incorrect", sessionDataBytes).join());

    final CompletionException completionException =
        assertThrows(CompletionException.class,
            () -> sender.checkVerificationCode(VERIFICATION_CODE, new byte[16]).join());

    assertTrue(completionException.getCause() instanceof InvalidProtocolBufferException);
  }
}
