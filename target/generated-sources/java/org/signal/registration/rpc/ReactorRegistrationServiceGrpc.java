package org.signal.registration.rpc;

import static org.signal.registration.rpc.RegistrationServiceGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;


@javax.annotation.Generated(
value = "by ReactorGrpc generator",
comments = "Source: registration_service.proto")
public final class ReactorRegistrationServiceGrpc {
    private ReactorRegistrationServiceGrpc() {}

    public static ReactorRegistrationServiceStub newReactorStub(io.grpc.Channel channel) {
        return new ReactorRegistrationServiceStub(channel);
    }

    public static final class ReactorRegistrationServiceStub extends io.grpc.stub.AbstractStub<ReactorRegistrationServiceStub> {
        private RegistrationServiceGrpc.RegistrationServiceStub delegateStub;

        private ReactorRegistrationServiceStub(io.grpc.Channel channel) {
            super(channel);
            delegateStub = RegistrationServiceGrpc.newStub(channel);
        }

        private ReactorRegistrationServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
            delegateStub = RegistrationServiceGrpc.newStub(channel).build(channel, callOptions);
        }

        @Override
        protected ReactorRegistrationServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new ReactorRegistrationServiceStub(channel, callOptions);
        }

        /**
         * <pre>
         * &#42;
         *  Create a new registration session for a given destination phone number.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.CreateRegistrationSessionResponse> createSession(reactor.core.publisher.Mono<org.signal.registration.rpc.CreateRegistrationSessionRequest> reactorRequest) {
            return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactorRequest, delegateStub::createSession, getCallOptions());
        }

        /**
         * <pre>
         * &#42;
         *  Retrieves session metadata for a given session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.GetRegistrationSessionMetadataResponse> getSessionMetadata(reactor.core.publisher.Mono<org.signal.registration.rpc.GetRegistrationSessionMetadataRequest> reactorRequest) {
            return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactorRequest, delegateStub::getSessionMetadata, getCallOptions());
        }

        /**
         * <pre>
         * &#42;
         *  Sends a verification code to a destination phone number within the context
         *  of a previously-created registration session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.SendVerificationCodeResponse> sendVerificationCode(reactor.core.publisher.Mono<org.signal.registration.rpc.SendVerificationCodeRequest> reactorRequest) {
            return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactorRequest, delegateStub::sendVerificationCode, getCallOptions());
        }

        /**
         * <pre>
         * &#42;
         *  Checks a client-provided verification code for a given registration
         *  session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.CheckVerificationCodeResponse> checkVerificationCode(reactor.core.publisher.Mono<org.signal.registration.rpc.CheckVerificationCodeRequest> reactorRequest) {
            return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactorRequest, delegateStub::checkVerificationCode, getCallOptions());
        }

        /**
         * <pre>
         * &#42;
         *  Create a new registration session for a given destination phone number.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.CreateRegistrationSessionResponse> createSession(org.signal.registration.rpc.CreateRegistrationSessionRequest reactorRequest) {
           return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactor.core.publisher.Mono.just(reactorRequest), delegateStub::createSession, getCallOptions());
        }

        /**
         * <pre>
         * &#42;
         *  Retrieves session metadata for a given session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.GetRegistrationSessionMetadataResponse> getSessionMetadata(org.signal.registration.rpc.GetRegistrationSessionMetadataRequest reactorRequest) {
           return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactor.core.publisher.Mono.just(reactorRequest), delegateStub::getSessionMetadata, getCallOptions());
        }

        /**
         * <pre>
         * &#42;
         *  Sends a verification code to a destination phone number within the context
         *  of a previously-created registration session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.SendVerificationCodeResponse> sendVerificationCode(org.signal.registration.rpc.SendVerificationCodeRequest reactorRequest) {
           return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactor.core.publisher.Mono.just(reactorRequest), delegateStub::sendVerificationCode, getCallOptions());
        }

        /**
         * <pre>
         * &#42;
         *  Checks a client-provided verification code for a given registration
         *  session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.CheckVerificationCodeResponse> checkVerificationCode(org.signal.registration.rpc.CheckVerificationCodeRequest reactorRequest) {
           return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactor.core.publisher.Mono.just(reactorRequest), delegateStub::checkVerificationCode, getCallOptions());
        }

    }

    public static abstract class RegistrationServiceImplBase implements io.grpc.BindableService {

        /**
         * <pre>
         * &#42;
         *  Create a new registration session for a given destination phone number.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.CreateRegistrationSessionResponse> createSession(org.signal.registration.rpc.CreateRegistrationSessionRequest request) {
            return createSession(reactor.core.publisher.Mono.just(request));
        }

        /**
         * <pre>
         * &#42;
         *  Create a new registration session for a given destination phone number.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.CreateRegistrationSessionResponse> createSession(reactor.core.publisher.Mono<org.signal.registration.rpc.CreateRegistrationSessionRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        /**
         * <pre>
         * &#42;
         *  Retrieves session metadata for a given session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.GetRegistrationSessionMetadataResponse> getSessionMetadata(org.signal.registration.rpc.GetRegistrationSessionMetadataRequest request) {
            return getSessionMetadata(reactor.core.publisher.Mono.just(request));
        }

        /**
         * <pre>
         * &#42;
         *  Retrieves session metadata for a given session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.GetRegistrationSessionMetadataResponse> getSessionMetadata(reactor.core.publisher.Mono<org.signal.registration.rpc.GetRegistrationSessionMetadataRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        /**
         * <pre>
         * &#42;
         *  Sends a verification code to a destination phone number within the context
         *  of a previously-created registration session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.SendVerificationCodeResponse> sendVerificationCode(org.signal.registration.rpc.SendVerificationCodeRequest request) {
            return sendVerificationCode(reactor.core.publisher.Mono.just(request));
        }

        /**
         * <pre>
         * &#42;
         *  Sends a verification code to a destination phone number within the context
         *  of a previously-created registration session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.SendVerificationCodeResponse> sendVerificationCode(reactor.core.publisher.Mono<org.signal.registration.rpc.SendVerificationCodeRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        /**
         * <pre>
         * &#42;
         *  Checks a client-provided verification code for a given registration
         *  session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.CheckVerificationCodeResponse> checkVerificationCode(org.signal.registration.rpc.CheckVerificationCodeRequest request) {
            return checkVerificationCode(reactor.core.publisher.Mono.just(request));
        }

        /**
         * <pre>
         * &#42;
         *  Checks a client-provided verification code for a given registration
         *  session.
         * </pre>
         */
        public reactor.core.publisher.Mono<org.signal.registration.rpc.CheckVerificationCodeResponse> checkVerificationCode(reactor.core.publisher.Mono<org.signal.registration.rpc.CheckVerificationCodeRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            org.signal.registration.rpc.RegistrationServiceGrpc.getCreateSessionMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            org.signal.registration.rpc.CreateRegistrationSessionRequest,
                                            org.signal.registration.rpc.CreateRegistrationSessionResponse>(
                                            this, METHODID_CREATE_SESSION)))
                    .addMethod(
                            org.signal.registration.rpc.RegistrationServiceGrpc.getGetSessionMetadataMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            org.signal.registration.rpc.GetRegistrationSessionMetadataRequest,
                                            org.signal.registration.rpc.GetRegistrationSessionMetadataResponse>(
                                            this, METHODID_GET_SESSION_METADATA)))
                    .addMethod(
                            org.signal.registration.rpc.RegistrationServiceGrpc.getSendVerificationCodeMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            org.signal.registration.rpc.SendVerificationCodeRequest,
                                            org.signal.registration.rpc.SendVerificationCodeResponse>(
                                            this, METHODID_SEND_VERIFICATION_CODE)))
                    .addMethod(
                            org.signal.registration.rpc.RegistrationServiceGrpc.getCheckVerificationCodeMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            org.signal.registration.rpc.CheckVerificationCodeRequest,
                                            org.signal.registration.rpc.CheckVerificationCodeResponse>(
                                            this, METHODID_CHECK_VERIFICATION_CODE)))
                    .build();
        }

        protected io.grpc.CallOptions getCallOptions(int methodId) {
            return null;
        }

        protected Throwable onErrorMap(Throwable throwable) {
            return com.salesforce.reactorgrpc.stub.ServerCalls.prepareError(throwable);
        }
    }

    public static final int METHODID_CREATE_SESSION = 0;
    public static final int METHODID_GET_SESSION_METADATA = 1;
    public static final int METHODID_SEND_VERIFICATION_CODE = 2;
    public static final int METHODID_CHECK_VERIFICATION_CODE = 3;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final RegistrationServiceImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(RegistrationServiceImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_CREATE_SESSION:
                    com.salesforce.reactorgrpc.stub.ServerCalls.oneToOne((org.signal.registration.rpc.CreateRegistrationSessionRequest) request,
                            (io.grpc.stub.StreamObserver<org.signal.registration.rpc.CreateRegistrationSessionResponse>) responseObserver,
                            serviceImpl::createSession, serviceImpl::onErrorMap);
                    break;
                case METHODID_GET_SESSION_METADATA:
                    com.salesforce.reactorgrpc.stub.ServerCalls.oneToOne((org.signal.registration.rpc.GetRegistrationSessionMetadataRequest) request,
                            (io.grpc.stub.StreamObserver<org.signal.registration.rpc.GetRegistrationSessionMetadataResponse>) responseObserver,
                            serviceImpl::getSessionMetadata, serviceImpl::onErrorMap);
                    break;
                case METHODID_SEND_VERIFICATION_CODE:
                    com.salesforce.reactorgrpc.stub.ServerCalls.oneToOne((org.signal.registration.rpc.SendVerificationCodeRequest) request,
                            (io.grpc.stub.StreamObserver<org.signal.registration.rpc.SendVerificationCodeResponse>) responseObserver,
                            serviceImpl::sendVerificationCode, serviceImpl::onErrorMap);
                    break;
                case METHODID_CHECK_VERIFICATION_CODE:
                    com.salesforce.reactorgrpc.stub.ServerCalls.oneToOne((org.signal.registration.rpc.CheckVerificationCodeRequest) request,
                            (io.grpc.stub.StreamObserver<org.signal.registration.rpc.CheckVerificationCodeResponse>) responseObserver,
                            serviceImpl::checkVerificationCode, serviceImpl::onErrorMap);
                    break;
                default:
                    throw new java.lang.AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new java.lang.AssertionError();
            }
        }
    }

}
