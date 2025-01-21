package org.signal.registration.cli;

import io.grpc.CallCredentials;
import io.grpc.Metadata;
import io.grpc.Status;

import java.util.concurrent.Executor;

public class LdapCallCredentials extends CallCredentials {

    private final String userId;
    private final String password;

    public LdapCallCredentials(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    public void applyRequestMetadata(RequestInfo requestInfo, Executor executor, MetadataApplier applier) {
        executor.execute(() -> {
            try {
                // Add LDAP credentials to gRPC metadata
                Metadata headers = new Metadata();
                Metadata.Key<String> userIdKey = Metadata.Key.of("ldap-user-id", Metadata.ASCII_STRING_MARSHALLER);
                Metadata.Key<String> passwordKey = Metadata.Key.of("ldap-password", Metadata.ASCII_STRING_MARSHALLER);

                headers.put(userIdKey, userId);
                headers.put(passwordKey, password);

                applier.apply(headers);
            } catch (Throwable e) {
                applier.fail(Status.UNAUTHENTICATED.withCause(e));
            }
        });
    }

    @Override
    public void thisUsesUnstableApi() {
        // Required by the CallCredentials API for forward compatibility
    }
}