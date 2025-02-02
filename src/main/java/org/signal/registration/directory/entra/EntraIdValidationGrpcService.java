package org.signal.registration.directory.entra;

import io.grpc.stub.StreamObserver;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import org.signal.registration.directory.DirectoryService;
import org.signal.registration.directory.DirectoryTypeConfiguration;
import org.signal.registration.ldap.rpc.LdapValidationServiceGrpc;
import org.signal.registration.ldap.rpc.ValidateCredentialsRequest;
import org.signal.registration.ldap.rpc.ValidateCredentialsResponse;
import org.signal.registration.ldap.rpc.ValidateCredentialsError;
import org.signal.registration.ldap.rpc.ValidateCredentialsErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Singleton
@Requires(property = "micronaut.config.registration.directory.type", value = DirectoryTypeConfiguration.ENTRA_ID)
public class EntraIdValidationGrpcService extends LdapValidationServiceGrpc.LdapValidationServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(EntraIdValidationGrpcService.class);
    private final DirectoryService directoryService;

    public EntraIdValidationGrpcService(DirectoryService directoryService) {
        this.directoryService = directoryService;
        logger.info("Created EntraIdValidationGrpcService");
    }

    @Override
    public void validateCredentials(ValidateCredentialsRequest request,
                                  StreamObserver<ValidateCredentialsResponse> responseObserver) {
        try {
            logger.info("Validating Entra ID credentials for user: {}", request.getUserId());
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

            Optional<String> phoneNumberOpt = directoryService.authenticateAndGetPhoneNumber(
                    request.getUserId(),
                    request.getPassword()
            );

            if (phoneNumberOpt.isPresent()) {
                logger.info("Successfully validated Entra ID credentials for user: {}", request.getUserId());
                responseObserver.onNext(ValidateCredentialsResponse.newBuilder()
                        .setPhoneNumber(phoneNumberOpt.get())
                        .build());
            } else {
                logger.warn("Failed to validate Entra ID credentials for user: {}", request.getUserId());
                responseObserver.onNext(ValidateCredentialsResponse.newBuilder()
                        .setError(ValidateCredentialsError.newBuilder()
                                .setErrorType(ValidateCredentialsErrorType.VALIDATE_CREDENTIALS_ERROR_TYPE_INVALID_CREDENTIALS)
                                .setMessage("Invalid credentials or phone number not found")
                                .build())
                        .build());
            }
            responseObserver.onCompleted();
        } catch (Exception e) {
            logger.error("Error validating Entra ID credentials for user: " + request.getUserId(), e);
            responseObserver.onNext(ValidateCredentialsResponse.newBuilder()
                    .setError(ValidateCredentialsError.newBuilder()
                            .setErrorType(ValidateCredentialsErrorType.VALIDATE_CREDENTIALS_ERROR_TYPE_SERVER_ERROR)
                            .setMessage("Internal server error: " + e.getMessage())
                            .build())
                    .build());
            responseObserver.onCompleted();
        }
    }
}
