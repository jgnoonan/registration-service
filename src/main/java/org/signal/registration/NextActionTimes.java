package org.signal.registration;

import java.time.Instant;
import java.util.Optional;

/**
 * Holds the next available times for various verification actions in a registration session.
 */
public record NextActionTimes(
    Optional<Instant> nextSms,
    Optional<Instant> nextVoiceCall,
    Optional<Instant> nextCodeCheck
) {}
