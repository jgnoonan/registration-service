// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: registration_service.proto

// Protobuf Java Version: 3.25.5
package org.signal.registration.rpc;

/**
 * Protobuf type {@code org.signal.registration.rpc.SendVerificationCodeRequest}
 */
public final class SendVerificationCodeRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.signal.registration.rpc.SendVerificationCodeRequest)
    SendVerificationCodeRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use SendVerificationCodeRequest.newBuilder() to construct.
  private SendVerificationCodeRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private SendVerificationCodeRequest() {
    transport_ = 0;
    acceptLanguage_ = "";
    clientType_ = 0;
    sessionId_ = com.google.protobuf.ByteString.EMPTY;
    senderName_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new SendVerificationCodeRequest();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_SendVerificationCodeRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_SendVerificationCodeRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.signal.registration.rpc.SendVerificationCodeRequest.class, org.signal.registration.rpc.SendVerificationCodeRequest.Builder.class);
  }

  public static final int TRANSPORT_FIELD_NUMBER = 2;
  private int transport_ = 0;
  /**
   * <pre>
   **
   * The message transport to use to send a verification code to the destination
   * phone number.
   * </pre>
   *
   * <code>.org.signal.registration.rpc.MessageTransport transport = 2;</code>
   * @return The enum numeric value on the wire for transport.
   */
  @java.lang.Override public int getTransportValue() {
    return transport_;
  }
  /**
   * <pre>
   **
   * The message transport to use to send a verification code to the destination
   * phone number.
   * </pre>
   *
   * <code>.org.signal.registration.rpc.MessageTransport transport = 2;</code>
   * @return The transport.
   */
  @java.lang.Override public org.signal.registration.rpc.MessageTransport getTransport() {
    org.signal.registration.rpc.MessageTransport result = org.signal.registration.rpc.MessageTransport.forNumber(transport_);
    return result == null ? org.signal.registration.rpc.MessageTransport.UNRECOGNIZED : result;
  }

  public static final int ACCEPT_LANGUAGE_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object acceptLanguage_ = "";
  /**
   * <pre>
   **
   * A prioritized list of languages accepted by the destination; should be
   * provided in the same format as the value of an HTTP Accept-Language header.
   * </pre>
   *
   * <code>string accept_language = 3;</code>
   * @return The acceptLanguage.
   */
  @java.lang.Override
  public java.lang.String getAcceptLanguage() {
    java.lang.Object ref = acceptLanguage_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      acceptLanguage_ = s;
      return s;
    }
  }
  /**
   * <pre>
   **
   * A prioritized list of languages accepted by the destination; should be
   * provided in the same format as the value of an HTTP Accept-Language header.
   * </pre>
   *
   * <code>string accept_language = 3;</code>
   * @return The bytes for acceptLanguage.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getAcceptLanguageBytes() {
    java.lang.Object ref = acceptLanguage_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      acceptLanguage_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CLIENT_TYPE_FIELD_NUMBER = 4;
  private int clientType_ = 0;
  /**
   * <pre>
   **
   * The type of client requesting a verification code.
   * </pre>
   *
   * <code>.org.signal.registration.rpc.ClientType client_type = 4;</code>
   * @return The enum numeric value on the wire for clientType.
   */
  @java.lang.Override public int getClientTypeValue() {
    return clientType_;
  }
  /**
   * <pre>
   **
   * The type of client requesting a verification code.
   * </pre>
   *
   * <code>.org.signal.registration.rpc.ClientType client_type = 4;</code>
   * @return The clientType.
   */
  @java.lang.Override public org.signal.registration.rpc.ClientType getClientType() {
    org.signal.registration.rpc.ClientType result = org.signal.registration.rpc.ClientType.forNumber(clientType_);
    return result == null ? org.signal.registration.rpc.ClientType.UNRECOGNIZED : result;
  }

  public static final int SESSION_ID_FIELD_NUMBER = 5;
  private com.google.protobuf.ByteString sessionId_ = com.google.protobuf.ByteString.EMPTY;
  /**
   * <pre>
   **
   * The ID of a session within which to send (or re-send) a verification code.
   * </pre>
   *
   * <code>bytes session_id = 5;</code>
   * @return The sessionId.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getSessionId() {
    return sessionId_;
  }

  public static final int SENDER_NAME_FIELD_NUMBER = 6;
  @SuppressWarnings("serial")
  private volatile java.lang.Object senderName_ = "";
  /**
   * <pre>
   **
   * If provided, always attempt to use the specified sender to send
   * this message.
   * </pre>
   *
   * <code>string sender_name = 6;</code>
   * @return The senderName.
   */
  @java.lang.Override
  public java.lang.String getSenderName() {
    java.lang.Object ref = senderName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      senderName_ = s;
      return s;
    }
  }
  /**
   * <pre>
   **
   * If provided, always attempt to use the specified sender to send
   * this message.
   * </pre>
   *
   * <code>string sender_name = 6;</code>
   * @return The bytes for senderName.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getSenderNameBytes() {
    java.lang.Object ref = senderName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      senderName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (transport_ != org.signal.registration.rpc.MessageTransport.MESSAGE_TRANSPORT_UNSPECIFIED.getNumber()) {
      output.writeEnum(2, transport_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(acceptLanguage_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, acceptLanguage_);
    }
    if (clientType_ != org.signal.registration.rpc.ClientType.CLIENT_TYPE_UNSPECIFIED.getNumber()) {
      output.writeEnum(4, clientType_);
    }
    if (!sessionId_.isEmpty()) {
      output.writeBytes(5, sessionId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(senderName_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 6, senderName_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (transport_ != org.signal.registration.rpc.MessageTransport.MESSAGE_TRANSPORT_UNSPECIFIED.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, transport_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(acceptLanguage_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, acceptLanguage_);
    }
    if (clientType_ != org.signal.registration.rpc.ClientType.CLIENT_TYPE_UNSPECIFIED.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(4, clientType_);
    }
    if (!sessionId_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(5, sessionId_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(senderName_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(6, senderName_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof org.signal.registration.rpc.SendVerificationCodeRequest)) {
      return super.equals(obj);
    }
    org.signal.registration.rpc.SendVerificationCodeRequest other = (org.signal.registration.rpc.SendVerificationCodeRequest) obj;

    if (transport_ != other.transport_) return false;
    if (!getAcceptLanguage()
        .equals(other.getAcceptLanguage())) return false;
    if (clientType_ != other.clientType_) return false;
    if (!getSessionId()
        .equals(other.getSessionId())) return false;
    if (!getSenderName()
        .equals(other.getSenderName())) return false;
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + TRANSPORT_FIELD_NUMBER;
    hash = (53 * hash) + transport_;
    hash = (37 * hash) + ACCEPT_LANGUAGE_FIELD_NUMBER;
    hash = (53 * hash) + getAcceptLanguage().hashCode();
    hash = (37 * hash) + CLIENT_TYPE_FIELD_NUMBER;
    hash = (53 * hash) + clientType_;
    hash = (37 * hash) + SESSION_ID_FIELD_NUMBER;
    hash = (53 * hash) + getSessionId().hashCode();
    hash = (37 * hash) + SENDER_NAME_FIELD_NUMBER;
    hash = (53 * hash) + getSenderName().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.signal.registration.rpc.SendVerificationCodeRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.SendVerificationCodeRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.SendVerificationCodeRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.SendVerificationCodeRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.SendVerificationCodeRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.SendVerificationCodeRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.SendVerificationCodeRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.signal.registration.rpc.SendVerificationCodeRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static org.signal.registration.rpc.SendVerificationCodeRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static org.signal.registration.rpc.SendVerificationCodeRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.signal.registration.rpc.SendVerificationCodeRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.signal.registration.rpc.SendVerificationCodeRequest parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(org.signal.registration.rpc.SendVerificationCodeRequest prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code org.signal.registration.rpc.SendVerificationCodeRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.signal.registration.rpc.SendVerificationCodeRequest)
      org.signal.registration.rpc.SendVerificationCodeRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_SendVerificationCodeRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_SendVerificationCodeRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.signal.registration.rpc.SendVerificationCodeRequest.class, org.signal.registration.rpc.SendVerificationCodeRequest.Builder.class);
    }

    // Construct using org.signal.registration.rpc.SendVerificationCodeRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      transport_ = 0;
      acceptLanguage_ = "";
      clientType_ = 0;
      sessionId_ = com.google.protobuf.ByteString.EMPTY;
      senderName_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_SendVerificationCodeRequest_descriptor;
    }

    @java.lang.Override
    public org.signal.registration.rpc.SendVerificationCodeRequest getDefaultInstanceForType() {
      return org.signal.registration.rpc.SendVerificationCodeRequest.getDefaultInstance();
    }

    @java.lang.Override
    public org.signal.registration.rpc.SendVerificationCodeRequest build() {
      org.signal.registration.rpc.SendVerificationCodeRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.signal.registration.rpc.SendVerificationCodeRequest buildPartial() {
      org.signal.registration.rpc.SendVerificationCodeRequest result = new org.signal.registration.rpc.SendVerificationCodeRequest(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(org.signal.registration.rpc.SendVerificationCodeRequest result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.transport_ = transport_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.acceptLanguage_ = acceptLanguage_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.clientType_ = clientType_;
      }
      if (((from_bitField0_ & 0x00000008) != 0)) {
        result.sessionId_ = sessionId_;
      }
      if (((from_bitField0_ & 0x00000010) != 0)) {
        result.senderName_ = senderName_;
      }
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.signal.registration.rpc.SendVerificationCodeRequest) {
        return mergeFrom((org.signal.registration.rpc.SendVerificationCodeRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.signal.registration.rpc.SendVerificationCodeRequest other) {
      if (other == org.signal.registration.rpc.SendVerificationCodeRequest.getDefaultInstance()) return this;
      if (other.transport_ != 0) {
        setTransportValue(other.getTransportValue());
      }
      if (!other.getAcceptLanguage().isEmpty()) {
        acceptLanguage_ = other.acceptLanguage_;
        bitField0_ |= 0x00000002;
        onChanged();
      }
      if (other.clientType_ != 0) {
        setClientTypeValue(other.getClientTypeValue());
      }
      if (other.getSessionId() != com.google.protobuf.ByteString.EMPTY) {
        setSessionId(other.getSessionId());
      }
      if (!other.getSenderName().isEmpty()) {
        senderName_ = other.senderName_;
        bitField0_ |= 0x00000010;
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 16: {
              transport_ = input.readEnum();
              bitField0_ |= 0x00000001;
              break;
            } // case 16
            case 26: {
              acceptLanguage_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000002;
              break;
            } // case 26
            case 32: {
              clientType_ = input.readEnum();
              bitField0_ |= 0x00000004;
              break;
            } // case 32
            case 42: {
              sessionId_ = input.readBytes();
              bitField0_ |= 0x00000008;
              break;
            } // case 42
            case 50: {
              senderName_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000010;
              break;
            } // case 50
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private int transport_ = 0;
    /**
     * <pre>
     **
     * The message transport to use to send a verification code to the destination
     * phone number.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.MessageTransport transport = 2;</code>
     * @return The enum numeric value on the wire for transport.
     */
    @java.lang.Override public int getTransportValue() {
      return transport_;
    }
    /**
     * <pre>
     **
     * The message transport to use to send a verification code to the destination
     * phone number.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.MessageTransport transport = 2;</code>
     * @param value The enum numeric value on the wire for transport to set.
     * @return This builder for chaining.
     */
    public Builder setTransportValue(int value) {
      transport_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * The message transport to use to send a verification code to the destination
     * phone number.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.MessageTransport transport = 2;</code>
     * @return The transport.
     */
    @java.lang.Override
    public org.signal.registration.rpc.MessageTransport getTransport() {
      org.signal.registration.rpc.MessageTransport result = org.signal.registration.rpc.MessageTransport.forNumber(transport_);
      return result == null ? org.signal.registration.rpc.MessageTransport.UNRECOGNIZED : result;
    }
    /**
     * <pre>
     **
     * The message transport to use to send a verification code to the destination
     * phone number.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.MessageTransport transport = 2;</code>
     * @param value The transport to set.
     * @return This builder for chaining.
     */
    public Builder setTransport(org.signal.registration.rpc.MessageTransport value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000001;
      transport_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * The message transport to use to send a verification code to the destination
     * phone number.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.MessageTransport transport = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearTransport() {
      bitField0_ = (bitField0_ & ~0x00000001);
      transport_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object acceptLanguage_ = "";
    /**
     * <pre>
     **
     * A prioritized list of languages accepted by the destination; should be
     * provided in the same format as the value of an HTTP Accept-Language header.
     * </pre>
     *
     * <code>string accept_language = 3;</code>
     * @return The acceptLanguage.
     */
    public java.lang.String getAcceptLanguage() {
      java.lang.Object ref = acceptLanguage_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        acceptLanguage_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     **
     * A prioritized list of languages accepted by the destination; should be
     * provided in the same format as the value of an HTTP Accept-Language header.
     * </pre>
     *
     * <code>string accept_language = 3;</code>
     * @return The bytes for acceptLanguage.
     */
    public com.google.protobuf.ByteString
        getAcceptLanguageBytes() {
      java.lang.Object ref = acceptLanguage_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        acceptLanguage_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     **
     * A prioritized list of languages accepted by the destination; should be
     * provided in the same format as the value of an HTTP Accept-Language header.
     * </pre>
     *
     * <code>string accept_language = 3;</code>
     * @param value The acceptLanguage to set.
     * @return This builder for chaining.
     */
    public Builder setAcceptLanguage(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      acceptLanguage_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * A prioritized list of languages accepted by the destination; should be
     * provided in the same format as the value of an HTTP Accept-Language header.
     * </pre>
     *
     * <code>string accept_language = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearAcceptLanguage() {
      acceptLanguage_ = getDefaultInstance().getAcceptLanguage();
      bitField0_ = (bitField0_ & ~0x00000002);
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * A prioritized list of languages accepted by the destination; should be
     * provided in the same format as the value of an HTTP Accept-Language header.
     * </pre>
     *
     * <code>string accept_language = 3;</code>
     * @param value The bytes for acceptLanguage to set.
     * @return This builder for chaining.
     */
    public Builder setAcceptLanguageBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      acceptLanguage_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }

    private int clientType_ = 0;
    /**
     * <pre>
     **
     * The type of client requesting a verification code.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.ClientType client_type = 4;</code>
     * @return The enum numeric value on the wire for clientType.
     */
    @java.lang.Override public int getClientTypeValue() {
      return clientType_;
    }
    /**
     * <pre>
     **
     * The type of client requesting a verification code.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.ClientType client_type = 4;</code>
     * @param value The enum numeric value on the wire for clientType to set.
     * @return This builder for chaining.
     */
    public Builder setClientTypeValue(int value) {
      clientType_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * The type of client requesting a verification code.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.ClientType client_type = 4;</code>
     * @return The clientType.
     */
    @java.lang.Override
    public org.signal.registration.rpc.ClientType getClientType() {
      org.signal.registration.rpc.ClientType result = org.signal.registration.rpc.ClientType.forNumber(clientType_);
      return result == null ? org.signal.registration.rpc.ClientType.UNRECOGNIZED : result;
    }
    /**
     * <pre>
     **
     * The type of client requesting a verification code.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.ClientType client_type = 4;</code>
     * @param value The clientType to set.
     * @return This builder for chaining.
     */
    public Builder setClientType(org.signal.registration.rpc.ClientType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000004;
      clientType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * The type of client requesting a verification code.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.ClientType client_type = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearClientType() {
      bitField0_ = (bitField0_ & ~0x00000004);
      clientType_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString sessionId_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     **
     * The ID of a session within which to send (or re-send) a verification code.
     * </pre>
     *
     * <code>bytes session_id = 5;</code>
     * @return The sessionId.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getSessionId() {
      return sessionId_;
    }
    /**
     * <pre>
     **
     * The ID of a session within which to send (or re-send) a verification code.
     * </pre>
     *
     * <code>bytes session_id = 5;</code>
     * @param value The sessionId to set.
     * @return This builder for chaining.
     */
    public Builder setSessionId(com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      sessionId_ = value;
      bitField0_ |= 0x00000008;
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * The ID of a session within which to send (or re-send) a verification code.
     * </pre>
     *
     * <code>bytes session_id = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearSessionId() {
      bitField0_ = (bitField0_ & ~0x00000008);
      sessionId_ = getDefaultInstance().getSessionId();
      onChanged();
      return this;
    }

    private java.lang.Object senderName_ = "";
    /**
     * <pre>
     **
     * If provided, always attempt to use the specified sender to send
     * this message.
     * </pre>
     *
     * <code>string sender_name = 6;</code>
     * @return The senderName.
     */
    public java.lang.String getSenderName() {
      java.lang.Object ref = senderName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        senderName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     **
     * If provided, always attempt to use the specified sender to send
     * this message.
     * </pre>
     *
     * <code>string sender_name = 6;</code>
     * @return The bytes for senderName.
     */
    public com.google.protobuf.ByteString
        getSenderNameBytes() {
      java.lang.Object ref = senderName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        senderName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     **
     * If provided, always attempt to use the specified sender to send
     * this message.
     * </pre>
     *
     * <code>string sender_name = 6;</code>
     * @param value The senderName to set.
     * @return This builder for chaining.
     */
    public Builder setSenderName(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      senderName_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * If provided, always attempt to use the specified sender to send
     * this message.
     * </pre>
     *
     * <code>string sender_name = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearSenderName() {
      senderName_ = getDefaultInstance().getSenderName();
      bitField0_ = (bitField0_ & ~0x00000010);
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * If provided, always attempt to use the specified sender to send
     * this message.
     * </pre>
     *
     * <code>string sender_name = 6;</code>
     * @param value The bytes for senderName to set.
     * @return This builder for chaining.
     */
    public Builder setSenderNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      senderName_ = value;
      bitField0_ |= 0x00000010;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:org.signal.registration.rpc.SendVerificationCodeRequest)
  }

  // @@protoc_insertion_point(class_scope:org.signal.registration.rpc.SendVerificationCodeRequest)
  private static final org.signal.registration.rpc.SendVerificationCodeRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.signal.registration.rpc.SendVerificationCodeRequest();
  }

  public static org.signal.registration.rpc.SendVerificationCodeRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SendVerificationCodeRequest>
      PARSER = new com.google.protobuf.AbstractParser<SendVerificationCodeRequest>() {
    @java.lang.Override
    public SendVerificationCodeRequest parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<SendVerificationCodeRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<SendVerificationCodeRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.signal.registration.rpc.SendVerificationCodeRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

