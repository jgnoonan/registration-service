package org.signal.registration.util;

import com.google.api.gax.rpc.ApiException;
import com.google.cloud.Timestamp;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import java.time.Instant;

public class TestGoogleApiUtil {
    public static StatusRuntimeException convertToStatusRuntimeException(final ApiException apiException) {
        return Status.fromCodeValue(apiException.getStatusCode().getCode().getHttpStatusCode())
                .withDescription(apiException.getMessage())
                .asRuntimeException();
    }

    public static Timestamp timestampFromInstant(final Instant instant) {
        return Timestamp.ofTimeSecondsAndNanos(
            instant.getEpochSecond(),
            instant.getNano()
        );
    }
}
