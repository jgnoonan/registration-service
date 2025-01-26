/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.analytics.gcp.pubsub;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.signal.registration.analytics.AttemptAnalyzedEvent;
import org.signal.registration.rpc.ClientType;
import org.signal.registration.rpc.MessageTransport;
import org.signal.registration.sender.AttemptData;
import org.signal.registration.sender.MessageTransportFailure;
import org.signal.registration.session.FailedSendReason;
import org.signal.registration.session.SessionMetadata;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GcpPubSubAttemptAnalyzedEventListenerTest {

  private AttemptAnalyzedPubSubClient pubSubClient;
  private Executor executor;
  private MeterRegistry meterRegistry;
  private GcpPubSubAttemptAnalyzedEventListener listener;

  private static final MessageTransport MESSAGE_TRANSPORT = MessageTransport.MESSAGE_TRANSPORT_SMS;
  private static final ClientType CLIENT_TYPE = ClientType.CLIENT_TYPE_UNSPECIFIED;
  private static final int ATTEMPT_ID = 1;
  private static final BigDecimal ONE_MILLION = new BigDecimal("1e6");
  private static final Instant CURRENT_TIME = Instant.now();

  @BeforeEach
  void setUp() {
    pubSubClient = mock(AttemptAnalyzedPubSubClient.class);
    executor = mock(Executor.class);
    meterRegistry = mock(MeterRegistry.class);
    listener = new GcpPubSubAttemptAnalyzedEventListener(pubSubClient, executor, meterRegistry);
  }

  @Test
  void onApplicationEvent() {
    final UUID sessionId = UUID.randomUUID();
    final long attemptId = 97L;
    final String senderName = "test";
    final String region = "XX";

    final AttemptPendingAnalysis pendingAnalysis = AttemptPendingAnalysis.newBuilder()
        .setSessionId(UUIDUtil.uuidToByteString(sessionId))
        .setAttemptId((int) attemptId)
        .setSenderName(senderName)
        .setRegion(region)
        .setMessageTransport(MESSAGE_TRANSPORT)
        .setClientType(CLIENT_TYPE)
        .setTimestampEpochMillis(CURRENT_TIME.toEpochMilli())
        .build();

    final Money price = new Money(new BigDecimal("0.04"), Currency.getInstance("USD"));
    final Money estimatedPrice = new Money(new BigDecimal("0.045"), Currency.getInstance("CAD"));
    final String mcc = "017";
    final String mnc = "029";

    final AttemptAnalysis analysis = new AttemptAnalysis(
        Optional.of(price),
        Optional.of(estimatedPrice),
        Optional.of(mcc),
        Optional.of(mnc));

    listener.onApplicationEvent(new AttemptAnalyzedEvent(pendingAnalysis, analysis));

    verify(pubSubClient).send(any(byte[].class));
  }

  @ParameterizedTest
  @MethodSource
  void buildPubSubMessage(final ClientType clientType,
      final MessageTransport messageTransport,
      final String expectedClientType,
      final String expectedMessageTransport) {

    final UUID sessionId = UUID.randomUUID();
    final long attemptId = 97L;
    final String senderName = "test";
    final String region = "XX";

    final AttemptPendingAnalysis pendingAnalysis = AttemptPendingAnalysis.newBuilder()
        .setSessionId(UUIDUtil.uuidToByteString(sessionId))
        .setAttemptId((int) attemptId)
        .setSenderName(senderName)
        .setRegion(region)
        .setMessageTransport(messageTransport)
        .setClientType(clientType)
        .setTimestampEpochMillis(CURRENT_TIME.toEpochMilli())
        .build();

    final Money price = new Money(new BigDecimal("0.04"), Currency.getInstance("USD"));
    final Money estimatedPrice = new Money(new BigDecimal("0.045"), Currency.getInstance("CAD"));
    final String mcc = "017";
    final String mnc = "029";

    final AttemptAnalysis analysis = new AttemptAnalysis(
        Optional.of(price),
        Optional.of(estimatedPrice),
        Optional.of(mcc),
        Optional.of(mnc));

    final AttemptAnalyzedPubSubMessage expectedPubSubMessage = AttemptAnalyzedPubSubMessage.newBuilder()
        .setSessionId(sessionId.toString())
        .setAttemptId((int) attemptId)
        .setSenderName(senderName)
        .setMessageTransport(expectedMessageTransport)
        .setClientType(expectedClientType)
        .setRegion(region)
        .setTimestamp(CURRENT_TIME.toString())
        .setPriceMicros(price.amount().multiply(ONE_MILLION).longValueExact())
        .setCurrency(price.currency().getCurrencyCode())
        .setSenderMcc(mcc)
        .setSenderMnc(mnc)
        .setEstimatedPriceMicros(estimatedPrice.amount().multiply(ONE_MILLION).longValueExact())
        .setEstimatedPriceCurrency(estimatedPrice.currency().getCurrencyCode())
        .build();

    assertEquals(expectedPubSubMessage,
        GcpPubSubAttemptAnalyzedEventListener.buildPubSubMessage(new AttemptAnalyzedEvent(pendingAnalysis, analysis)));
  }

  private static Stream<Arguments> buildPubSubMessage() {
    return Stream.of(
        Arguments.of(ClientType.CLIENT_TYPE_IOS, MessageTransport.MESSAGE_TRANSPORT_SMS, "ios", "sms"),
        Arguments.of(ClientType.CLIENT_TYPE_ANDROID_WITH_FCM, MessageTransport.MESSAGE_TRANSPORT_SMS, "android-with-fcm", "sms"),
        Arguments.of(ClientType.CLIENT_TYPE_ANDROID_WITHOUT_FCM, MessageTransport.MESSAGE_TRANSPORT_SMS, "android-without-fcm", "sms"),
        Arguments.of(ClientType.CLIENT_TYPE_UNSPECIFIED, MessageTransport.MESSAGE_TRANSPORT_SMS, "unrecognized", "sms"),
        Arguments.of(ClientType.CLIENT_TYPE_IOS, MessageTransport.MESSAGE_TRANSPORT_VOICE, "ios", "voice"),
        Arguments.of(ClientType.CLIENT_TYPE_IOS, MessageTransport.MESSAGE_TRANSPORT_UNSPECIFIED, "ios", "unrecognized")
    );
  }
}
