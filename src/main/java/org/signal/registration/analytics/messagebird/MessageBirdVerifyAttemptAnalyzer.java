/*
 * Copyright 2023 Signal Messenger, LLC
 * SPDX-License-Identifier: AGPL-3.0-only
 */

package org.signal.registration.analytics.messagebird;

import com.google.common.annotations.VisibleForTesting;
import com.messagebird.MessageBirdClient;
import com.messagebird.exceptions.MessageBirdException;
import com.messagebird.exceptions.NotFoundException;
import com.messagebird.objects.MessageResponse;
import io.micronaut.context.event.ApplicationEventPublisher;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import java.net.URI;
import java.time.Clock;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.signal.registration.analytics.AttemptAnalyzedEvent;
import org.signal.registration.analytics.AttemptPendingAnalysis;
import org.signal.registration.analytics.AttemptPendingAnalysisRepository;
import org.signal.registration.sender.MessageTransport;
import org.signal.registration.sender.messagebird.verify.MessageBirdVerifySender;
import org.signal.registration.util.CompletionExceptions;
import org.signal.registration.util.MessageTransports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Analyzes verification attempts from {@link MessageBirdVerifySender}.
 */
@Singleton
class MessageBirdVerifyAttemptAnalyzer extends AbstractMessageBirdAttemptAnalyzer {

  private final MessageBirdClient messageBirdClient;
  private final MessageBirdSmsAttemptAnalyzer smsAttemptAnalyzer;
  private final MessageBirdVoiceAttemptAnalyzer voiceAttemptAnalyzer;
  private final Executor executor;

  private static final Logger logger = LoggerFactory.getLogger(MessageBirdVerifyAttemptAnalyzer.class);

  protected MessageBirdVerifyAttemptAnalyzer(final AttemptPendingAnalysisRepository repository,
      final ApplicationEventPublisher<AttemptAnalyzedEvent> attemptAnalyzedEventPublisher,
      final Clock clock,
      final MessageBirdPriceEstimator messageBirdPriceEstimator,
      final MessageBirdClient messageBirdClient,
      final MessageBirdSmsAttemptAnalyzer smsAttemptAnalyzer,
      final MessageBirdVoiceAttemptAnalyzer voiceAttemptAnalyzer,
      @Named(TaskExecutors.IO) final Executor executor) {

    super(repository, attemptAnalyzedEventPublisher, clock, messageBirdPriceEstimator);

    this.messageBirdClient = messageBirdClient;
    this.smsAttemptAnalyzer = smsAttemptAnalyzer;
    this.voiceAttemptAnalyzer = voiceAttemptAnalyzer;
    this.executor = executor;
  }

  @Override
  @Scheduled(fixedDelay = "${analytics.messagebird.verify.analysis-interval:4h}")
  protected void analyzeAttempts() {
    super.analyzeAttempts();
  }

  @Override
  protected String getSenderName() {
    return MessageBirdVerifySender.SENDER_NAME;
  }

  @Override
  protected CompletableFuture<MessageResponse.Recipients> getRecipients(final AttemptPendingAnalysis attemptPendingAnalysis) {
    final MessageTransport messageTransport =
        MessageTransports.getSenderMessageTransportFromRpcTransport(attemptPendingAnalysis.getMessageTransport());

    return CompletableFuture.supplyAsync(() -> {
          try {
            return messageBirdClient.getVerifyObject(attemptPendingAnalysis.getRemoteId());
          } catch (final MessageBirdException e) {
            throw CompletionExceptions.wrap(e);
          }
        }, executor)
        .thenApply(verify -> getMessageId(verify.getMessages().getHref()))
        .thenCompose(messageId -> switch (messageTransport) {
          case SMS -> smsAttemptAnalyzer.getRecipients(messageId);
          case VOICE -> voiceAttemptAnalyzer.getRecipients(messageId);
        })
        .whenComplete((ignored, throwable) -> {
          if (throwable != null && !(CompletionExceptions.unwrap(throwable) instanceof NotFoundException)) {
            logger.warn("Unexpected exception while analyzing attempt", throwable);
          }
        });
  }

  @VisibleForTesting
  static String getMessageId(final String messageHref) {
    final URI messageUri = URI.create(messageHref);
    final int lastSlash = messageUri.getPath().lastIndexOf('/');

    if (lastSlash == -1) {
      throw new IllegalArgumentException("Path does not contain a slash: " + messageHref);
    } else if (lastSlash == messageUri.getPath().length() - 1) {
      throw new IllegalArgumentException("Path ends with a trailing slash: " + messageHref);
    }

    return messageUri.getPath().substring(lastSlash + 1);
  }
}
