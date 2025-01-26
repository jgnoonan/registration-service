package org.signal.registration.ldap.service;

import io.grpc.stub.StreamObserver;
import jakarta.inject.Singleton;
import org.signal.registration.ldap.LdapService;
import org.signal.registration.ldap.rpc.LdapValidationServiceGrpc;
import org.signal.registration.ldap.rpc.ValidateCredentialsRequest;
import org.signal.registration.ldap.rpc.ValidateCredentialsResponse;
import org.signal.registration.ldap.rpc.ValidateCredentialsError;
import org.signal.registration.ldap.rpc.ValidateCredentialsErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Singleton
public class LdapValidationGrpcService extends LdapValidationServiceGrpc.LdapValidationServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(LdapValidationGrpcService.class);
    private final LdapService ldapService;

    public LdapValidationGrpcService(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    @Override
    public void validateCredentials(ValidateCredentialsRequest request,
                                  StreamObserver<ValidateCredentialsResponse> responseObserver) {
        try {
            if (request.getUserId() == null || request.getPassword() == null) {
                responseObserver.onNext(ValidateCredentialsResponse.newBuilder()
                        .setError(ValidateCredentialsError.newBuilder()
                                .setErrorType(ValidateCredentialsErrorType.VALIDATE_CREDENTIALS_ERROR_TYPE_INVALID_CREDENTIALS)
                                .setMessage("User ID and password are required")
                                .build())
                        .build());
                responseObserver.onCompleted();
                return;
            }

            Optional<String> phoneNumberOpt = ldapService.authenticateAndGetPhoneNumber(
                    request.getUserId(),
                    request.getPassword()
            );

            if (phoneNumberOpt.isPresent()) {
                responseObserver.onNext(ValidateCredentialsResponse.newBuilder()
                        .setPhoneNumber(phoneNumberOpt.get())
                        .build());
            } else {
                responseObserver.onNext(ValidateCredentialsResponse.newBuilder()
                        .setError(ValidateCredentialsError.newBuilder()
                                .setErrorType(ValidateCredentialsErrorType.VALIDATE_CREDENTIALS_ERROR_TYPE_INVALID_CREDENTIALS)
                                .setMessage("Invalid credentials")
                                .build())
                        .build());
            }
        } catch (Exception e) {
            logger.error("Error validating credentials", e);
            responseObserver.onNext(ValidateCredentialsResponse.newBuilder()
                    .setError(ValidateCredentialsError.newBuilder()
                            .setErrorType(ValidateCredentialsErrorType.VALIDATE_CREDENTIALS_ERROR_TYPE_SERVER_ERROR)
                            .setMessage("Internal server error")
                            .build())
                    .build());
        }
        responseObserver.onCompleted();
    }
}
