package org.signal.registration.sender.twilio;

import com.twilio.exception.ApiException;
import com.twilio.http.TwilioRestClient;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class ApiExceptions {
    public static StatusRuntimeException convertToStatusRuntimeException(final ApiException apiException) {
        Status status;
        switch (apiException.getStatusCode()) {
            case 404:
                status = Status.NOT_FOUND;
                break;
            case 400:
                status = Status.INVALID_ARGUMENT;
                break;
            case 401:
            case 403:
                status = Status.PERMISSION_DENIED;
                break;
            case 429:
                status = Status.RESOURCE_EXHAUSTED;
                break;
            case 503:
                status = Status.UNAVAILABLE;
                break;
            default:
                status = Status.UNKNOWN;
        }
        return status.withDescription(apiException.getMessage())
                    .asRuntimeException();
    }

    public static int extractErrorCode(final ApiException apiException) {
        return apiException.getStatusCode();
    }
}
