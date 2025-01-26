/*
 * Copyright 2022 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.sender;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.signal.registration.sender.VerificationCodeGenerator;

import java.util.Random;

@MicronautTest(environments = "test", startApplication = false)
class VerificationCodeGeneratorTest {

  @Inject
  private VerificationCodeGenerator verificationCodeGenerator;

  @Inject
  private Random random;

  @MockBean(Random.class)
  Random mockRandom() {
    return Mockito.mock(Random.class);
  }

  @Test
  void generateVerificationCode() {
    Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(123456);
    assertEquals("123456", verificationCodeGenerator.generateVerificationCode());
  }
}
