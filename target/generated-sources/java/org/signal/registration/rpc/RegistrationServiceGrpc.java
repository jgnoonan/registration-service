package org.signal.registration.rpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.66.0)",
    comments = "Source: registration_service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class RegistrationServiceGrpc {

  private RegistrationServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "org.signal.registration.rpc.RegistrationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.signal.registration.rpc.CreateRegistrationSessionRequest,
      org.signal.registration.rpc.CreateRegistrationSessionResponse> getCreateSessionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateSession",
      requestType = org.signal.registration.rpc.CreateRegistrationSessionRequest.class,
      responseType = org.signal.registration.rpc.CreateRegistrationSessionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.signal.registration.rpc.CreateRegistrationSessionRequest,
      org.signal.registration.rpc.CreateRegistrationSessionResponse> getCreateSessionMethod() {
    io.grpc.MethodDescriptor<org.signal.registration.rpc.CreateRegistrationSessionRequest, org.signal.registration.rpc.CreateRegistrationSessionResponse> getCreateSessionMethod;
    if ((getCreateSessionMethod = RegistrationServiceGrpc.getCreateSessionMethod) == null) {
      synchronized (RegistrationServiceGrpc.class) {
        if ((getCreateSessionMethod = RegistrationServiceGrpc.getCreateSessionMethod) == null) {
          RegistrationServiceGrpc.getCreateSessionMethod = getCreateSessionMethod =
              io.grpc.MethodDescriptor.<org.signal.registration.rpc.CreateRegistrationSessionRequest, org.signal.registration.rpc.CreateRegistrationSessionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateSession"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.signal.registration.rpc.CreateRegistrationSessionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.signal.registration.rpc.CreateRegistrationSessionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RegistrationServiceMethodDescriptorSupplier("CreateSession"))
              .build();
        }
      }
    }
    return getCreateSessionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.signal.registration.rpc.GetRegistrationSessionMetadataRequest,
      org.signal.registration.rpc.GetRegistrationSessionMetadataResponse> getGetSessionMetadataMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetSessionMetadata",
      requestType = org.signal.registration.rpc.GetRegistrationSessionMetadataRequest.class,
      responseType = org.signal.registration.rpc.GetRegistrationSessionMetadataResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.signal.registration.rpc.GetRegistrationSessionMetadataRequest,
      org.signal.registration.rpc.GetRegistrationSessionMetadataResponse> getGetSessionMetadataMethod() {
    io.grpc.MethodDescriptor<org.signal.registration.rpc.GetRegistrationSessionMetadataRequest, org.signal.registration.rpc.GetRegistrationSessionMetadataResponse> getGetSessionMetadataMethod;
    if ((getGetSessionMetadataMethod = RegistrationServiceGrpc.getGetSessionMetadataMethod) == null) {
      synchronized (RegistrationServiceGrpc.class) {
        if ((getGetSessionMetadataMethod = RegistrationServiceGrpc.getGetSessionMetadataMethod) == null) {
          RegistrationServiceGrpc.getGetSessionMetadataMethod = getGetSessionMetadataMethod =
              io.grpc.MethodDescriptor.<org.signal.registration.rpc.GetRegistrationSessionMetadataRequest, org.signal.registration.rpc.GetRegistrationSessionMetadataResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetSessionMetadata"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.signal.registration.rpc.GetRegistrationSessionMetadataRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.signal.registration.rpc.GetRegistrationSessionMetadataResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RegistrationServiceMethodDescriptorSupplier("GetSessionMetadata"))
              .build();
        }
      }
    }
    return getGetSessionMetadataMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.signal.registration.rpc.SendVerificationCodeRequest,
      org.signal.registration.rpc.SendVerificationCodeResponse> getSendVerificationCodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendVerificationCode",
      requestType = org.signal.registration.rpc.SendVerificationCodeRequest.class,
      responseType = org.signal.registration.rpc.SendVerificationCodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.signal.registration.rpc.SendVerificationCodeRequest,
      org.signal.registration.rpc.SendVerificationCodeResponse> getSendVerificationCodeMethod() {
    io.grpc.MethodDescriptor<org.signal.registration.rpc.SendVerificationCodeRequest, org.signal.registration.rpc.SendVerificationCodeResponse> getSendVerificationCodeMethod;
    if ((getSendVerificationCodeMethod = RegistrationServiceGrpc.getSendVerificationCodeMethod) == null) {
      synchronized (RegistrationServiceGrpc.class) {
        if ((getSendVerificationCodeMethod = RegistrationServiceGrpc.getSendVerificationCodeMethod) == null) {
          RegistrationServiceGrpc.getSendVerificationCodeMethod = getSendVerificationCodeMethod =
              io.grpc.MethodDescriptor.<org.signal.registration.rpc.SendVerificationCodeRequest, org.signal.registration.rpc.SendVerificationCodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SendVerificationCode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.signal.registration.rpc.SendVerificationCodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.signal.registration.rpc.SendVerificationCodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RegistrationServiceMethodDescriptorSupplier("SendVerificationCode"))
              .build();
        }
      }
    }
    return getSendVerificationCodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.signal.registration.rpc.CheckVerificationCodeRequest,
      org.signal.registration.rpc.CheckVerificationCodeResponse> getCheckVerificationCodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckVerificationCode",
      requestType = org.signal.registration.rpc.CheckVerificationCodeRequest.class,
      responseType = org.signal.registration.rpc.CheckVerificationCodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.signal.registration.rpc.CheckVerificationCodeRequest,
      org.signal.registration.rpc.CheckVerificationCodeResponse> getCheckVerificationCodeMethod() {
    io.grpc.MethodDescriptor<org.signal.registration.rpc.CheckVerificationCodeRequest, org.signal.registration.rpc.CheckVerificationCodeResponse> getCheckVerificationCodeMethod;
    if ((getCheckVerificationCodeMethod = RegistrationServiceGrpc.getCheckVerificationCodeMethod) == null) {
      synchronized (RegistrationServiceGrpc.class) {
        if ((getCheckVerificationCodeMethod = RegistrationServiceGrpc.getCheckVerificationCodeMethod) == null) {
          RegistrationServiceGrpc.getCheckVerificationCodeMethod = getCheckVerificationCodeMethod =
              io.grpc.MethodDescriptor.<org.signal.registration.rpc.CheckVerificationCodeRequest, org.signal.registration.rpc.CheckVerificationCodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckVerificationCode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.signal.registration.rpc.CheckVerificationCodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.signal.registration.rpc.CheckVerificationCodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new RegistrationServiceMethodDescriptorSupplier("CheckVerificationCode"))
              .build();
        }
      }
    }
    return getCheckVerificationCodeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static RegistrationServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceStub>() {
        @java.lang.Override
        public RegistrationServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RegistrationServiceStub(channel, callOptions);
        }
      };
    return RegistrationServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static RegistrationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceBlockingStub>() {
        @java.lang.Override
        public RegistrationServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RegistrationServiceBlockingStub(channel, callOptions);
        }
      };
    return RegistrationServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static RegistrationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<RegistrationServiceFutureStub>() {
        @java.lang.Override
        public RegistrationServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new RegistrationServiceFutureStub(channel, callOptions);
        }
      };
    return RegistrationServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     * <pre>
     **
     * Create a new registration session for a given destination phone number.
     * </pre>
     */
    default void createSession(org.signal.registration.rpc.CreateRegistrationSessionRequest request,
        io.grpc.stub.StreamObserver<org.signal.registration.rpc.CreateRegistrationSessionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateSessionMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Retrieves session metadata for a given session.
     * </pre>
     */
    default void getSessionMetadata(org.signal.registration.rpc.GetRegistrationSessionMetadataRequest request,
        io.grpc.stub.StreamObserver<org.signal.registration.rpc.GetRegistrationSessionMetadataResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetSessionMetadataMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Sends a verification code to a destination phone number within the context
     * of a previously-created registration session.
     * </pre>
     */
    default void sendVerificationCode(org.signal.registration.rpc.SendVerificationCodeRequest request,
        io.grpc.stub.StreamObserver<org.signal.registration.rpc.SendVerificationCodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSendVerificationCodeMethod(), responseObserver);
    }

    /**
     * <pre>
     **
     * Checks a client-provided verification code for a given registration
     * session.
     * </pre>
     */
    default void checkVerificationCode(org.signal.registration.rpc.CheckVerificationCodeRequest request,
        io.grpc.stub.StreamObserver<org.signal.registration.rpc.CheckVerificationCodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckVerificationCodeMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service RegistrationService.
   */
  public static abstract class RegistrationServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return RegistrationServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service RegistrationService.
   */
  public static final class RegistrationServiceStub
      extends io.grpc.stub.AbstractAsyncStub<RegistrationServiceStub> {
    private RegistrationServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RegistrationServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RegistrationServiceStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Create a new registration session for a given destination phone number.
     * </pre>
     */
    public void createSession(org.signal.registration.rpc.CreateRegistrationSessionRequest request,
        io.grpc.stub.StreamObserver<org.signal.registration.rpc.CreateRegistrationSessionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateSessionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Retrieves session metadata for a given session.
     * </pre>
     */
    public void getSessionMetadata(org.signal.registration.rpc.GetRegistrationSessionMetadataRequest request,
        io.grpc.stub.StreamObserver<org.signal.registration.rpc.GetRegistrationSessionMetadataResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetSessionMetadataMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Sends a verification code to a destination phone number within the context
     * of a previously-created registration session.
     * </pre>
     */
    public void sendVerificationCode(org.signal.registration.rpc.SendVerificationCodeRequest request,
        io.grpc.stub.StreamObserver<org.signal.registration.rpc.SendVerificationCodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSendVerificationCodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     **
     * Checks a client-provided verification code for a given registration
     * session.
     * </pre>
     */
    public void checkVerificationCode(org.signal.registration.rpc.CheckVerificationCodeRequest request,
        io.grpc.stub.StreamObserver<org.signal.registration.rpc.CheckVerificationCodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckVerificationCodeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service RegistrationService.
   */
  public static final class RegistrationServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<RegistrationServiceBlockingStub> {
    private RegistrationServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RegistrationServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RegistrationServiceBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Create a new registration session for a given destination phone number.
     * </pre>
     */
    public org.signal.registration.rpc.CreateRegistrationSessionResponse createSession(org.signal.registration.rpc.CreateRegistrationSessionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateSessionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Retrieves session metadata for a given session.
     * </pre>
     */
    public org.signal.registration.rpc.GetRegistrationSessionMetadataResponse getSessionMetadata(org.signal.registration.rpc.GetRegistrationSessionMetadataRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetSessionMetadataMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Sends a verification code to a destination phone number within the context
     * of a previously-created registration session.
     * </pre>
     */
    public org.signal.registration.rpc.SendVerificationCodeResponse sendVerificationCode(org.signal.registration.rpc.SendVerificationCodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSendVerificationCodeMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     **
     * Checks a client-provided verification code for a given registration
     * session.
     * </pre>
     */
    public org.signal.registration.rpc.CheckVerificationCodeResponse checkVerificationCode(org.signal.registration.rpc.CheckVerificationCodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckVerificationCodeMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service RegistrationService.
   */
  public static final class RegistrationServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<RegistrationServiceFutureStub> {
    private RegistrationServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected RegistrationServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new RegistrationServiceFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     **
     * Create a new registration session for a given destination phone number.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.signal.registration.rpc.CreateRegistrationSessionResponse> createSession(
        org.signal.registration.rpc.CreateRegistrationSessionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateSessionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Retrieves session metadata for a given session.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.signal.registration.rpc.GetRegistrationSessionMetadataResponse> getSessionMetadata(
        org.signal.registration.rpc.GetRegistrationSessionMetadataRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetSessionMetadataMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Sends a verification code to a destination phone number within the context
     * of a previously-created registration session.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.signal.registration.rpc.SendVerificationCodeResponse> sendVerificationCode(
        org.signal.registration.rpc.SendVerificationCodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSendVerificationCodeMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     **
     * Checks a client-provided verification code for a given registration
     * session.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<org.signal.registration.rpc.CheckVerificationCodeResponse> checkVerificationCode(
        org.signal.registration.rpc.CheckVerificationCodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckVerificationCodeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_SESSION = 0;
  private static final int METHODID_GET_SESSION_METADATA = 1;
  private static final int METHODID_SEND_VERIFICATION_CODE = 2;
  private static final int METHODID_CHECK_VERIFICATION_CODE = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_SESSION:
          serviceImpl.createSession((org.signal.registration.rpc.CreateRegistrationSessionRequest) request,
              (io.grpc.stub.StreamObserver<org.signal.registration.rpc.CreateRegistrationSessionResponse>) responseObserver);
          break;
        case METHODID_GET_SESSION_METADATA:
          serviceImpl.getSessionMetadata((org.signal.registration.rpc.GetRegistrationSessionMetadataRequest) request,
              (io.grpc.stub.StreamObserver<org.signal.registration.rpc.GetRegistrationSessionMetadataResponse>) responseObserver);
          break;
        case METHODID_SEND_VERIFICATION_CODE:
          serviceImpl.sendVerificationCode((org.signal.registration.rpc.SendVerificationCodeRequest) request,
              (io.grpc.stub.StreamObserver<org.signal.registration.rpc.SendVerificationCodeResponse>) responseObserver);
          break;
        case METHODID_CHECK_VERIFICATION_CODE:
          serviceImpl.checkVerificationCode((org.signal.registration.rpc.CheckVerificationCodeRequest) request,
              (io.grpc.stub.StreamObserver<org.signal.registration.rpc.CheckVerificationCodeResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateSessionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.signal.registration.rpc.CreateRegistrationSessionRequest,
              org.signal.registration.rpc.CreateRegistrationSessionResponse>(
                service, METHODID_CREATE_SESSION)))
        .addMethod(
          getGetSessionMetadataMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.signal.registration.rpc.GetRegistrationSessionMetadataRequest,
              org.signal.registration.rpc.GetRegistrationSessionMetadataResponse>(
                service, METHODID_GET_SESSION_METADATA)))
        .addMethod(
          getSendVerificationCodeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.signal.registration.rpc.SendVerificationCodeRequest,
              org.signal.registration.rpc.SendVerificationCodeResponse>(
                service, METHODID_SEND_VERIFICATION_CODE)))
        .addMethod(
          getCheckVerificationCodeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.signal.registration.rpc.CheckVerificationCodeRequest,
              org.signal.registration.rpc.CheckVerificationCodeResponse>(
                service, METHODID_CHECK_VERIFICATION_CODE)))
        .build();
  }

  private static abstract class RegistrationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    RegistrationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("RegistrationService");
    }
  }

  private static final class RegistrationServiceFileDescriptorSupplier
      extends RegistrationServiceBaseDescriptorSupplier {
    RegistrationServiceFileDescriptorSupplier() {}
  }

  private static final class RegistrationServiceMethodDescriptorSupplier
      extends RegistrationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    RegistrationServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (RegistrationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new RegistrationServiceFileDescriptorSupplier())
              .addMethod(getCreateSessionMethod())
              .addMethod(getGetSessionMetadataMethod())
              .addMethod(getSendVerificationCodeMethod())
              .addMethod(getCheckVerificationCodeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
