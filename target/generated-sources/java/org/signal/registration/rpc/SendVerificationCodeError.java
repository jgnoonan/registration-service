// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: registration_service.proto

// Protobuf Java Version: 3.25.5
package org.signal.registration.rpc;

/**
 * Protobuf type {@code org.signal.registration.rpc.SendVerificationCodeError}
 */
public final class SendVerificationCodeError extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.signal.registration.rpc.SendVerificationCodeError)
    SendVerificationCodeErrorOrBuilder {
private static final long serialVersionUID = 0L;
  // Use SendVerificationCodeError.newBuilder() to construct.
  private SendVerificationCodeError(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private SendVerificationCodeError() {
    errorType_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new SendVerificationCodeError();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_SendVerificationCodeError_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_SendVerificationCodeError_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.signal.registration.rpc.SendVerificationCodeError.class, org.signal.registration.rpc.SendVerificationCodeError.Builder.class);
  }

  public static final int ERROR_TYPE_FIELD_NUMBER = 1;
  private int errorType_ = 0;
  /**
   * <pre>
   **
   * The type of error that prevented a verification code from being sent.
   * </pre>
   *
   * <code>.org.signal.registration.rpc.SendVerificationCodeErrorType error_type = 1;</code>
   * @return The enum numeric value on the wire for errorType.
   */
  @java.lang.Override public int getErrorTypeValue() {
    return errorType_;
  }
  /**
   * <pre>
   **
   * The type of error that prevented a verification code from being sent.
   * </pre>
   *
   * <code>.org.signal.registration.rpc.SendVerificationCodeErrorType error_type = 1;</code>
   * @return The errorType.
   */
  @java.lang.Override public org.signal.registration.rpc.SendVerificationCodeErrorType getErrorType() {
    org.signal.registration.rpc.SendVerificationCodeErrorType result = org.signal.registration.rpc.SendVerificationCodeErrorType.forNumber(errorType_);
    return result == null ? org.signal.registration.rpc.SendVerificationCodeErrorType.UNRECOGNIZED : result;
  }

  public static final int MAY_RETRY_FIELD_NUMBER = 2;
  private boolean mayRetry_ = false;
  /**
   * <pre>
   **
   * Indicates that this error may succeed if retried without modification after
   * a delay indicated by `retry_after_seconds`. If false, callers should not
   * retry the request without modification.
   * </pre>
   *
   * <code>bool may_retry = 2;</code>
   * @return The mayRetry.
   */
  @java.lang.Override
  public boolean getMayRetry() {
    return mayRetry_;
  }

  public static final int RETRY_AFTER_SECONDS_FIELD_NUMBER = 3;
  private long retryAfterSeconds_ = 0L;
  /**
   * <pre>
   **
   * If this error may be retried,, indicates the duration in seconds from the
   * present after which the request may be retried without modification. This
   * value has no meaning otherwise.
   * </pre>
   *
   * <code>uint64 retry_after_seconds = 3;</code>
   * @return The retryAfterSeconds.
   */
  @java.lang.Override
  public long getRetryAfterSeconds() {
    return retryAfterSeconds_;
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
    if (errorType_ != org.signal.registration.rpc.SendVerificationCodeErrorType.SEND_VERIFICATION_CODE_ERROR_TYPE_UNSPECIFIED.getNumber()) {
      output.writeEnum(1, errorType_);
    }
    if (mayRetry_ != false) {
      output.writeBool(2, mayRetry_);
    }
    if (retryAfterSeconds_ != 0L) {
      output.writeUInt64(3, retryAfterSeconds_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (errorType_ != org.signal.registration.rpc.SendVerificationCodeErrorType.SEND_VERIFICATION_CODE_ERROR_TYPE_UNSPECIFIED.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, errorType_);
    }
    if (mayRetry_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(2, mayRetry_);
    }
    if (retryAfterSeconds_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeUInt64Size(3, retryAfterSeconds_);
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
    if (!(obj instanceof org.signal.registration.rpc.SendVerificationCodeError)) {
      return super.equals(obj);
    }
    org.signal.registration.rpc.SendVerificationCodeError other = (org.signal.registration.rpc.SendVerificationCodeError) obj;

    if (errorType_ != other.errorType_) return false;
    if (getMayRetry()
        != other.getMayRetry()) return false;
    if (getRetryAfterSeconds()
        != other.getRetryAfterSeconds()) return false;
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
    hash = (37 * hash) + ERROR_TYPE_FIELD_NUMBER;
    hash = (53 * hash) + errorType_;
    hash = (37 * hash) + MAY_RETRY_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getMayRetry());
    hash = (37 * hash) + RETRY_AFTER_SECONDS_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getRetryAfterSeconds());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.signal.registration.rpc.SendVerificationCodeError parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.SendVerificationCodeError parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.SendVerificationCodeError parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.SendVerificationCodeError parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.SendVerificationCodeError parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.SendVerificationCodeError parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.SendVerificationCodeError parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.signal.registration.rpc.SendVerificationCodeError parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static org.signal.registration.rpc.SendVerificationCodeError parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static org.signal.registration.rpc.SendVerificationCodeError parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.signal.registration.rpc.SendVerificationCodeError parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.signal.registration.rpc.SendVerificationCodeError parseFrom(
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
  public static Builder newBuilder(org.signal.registration.rpc.SendVerificationCodeError prototype) {
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
   * Protobuf type {@code org.signal.registration.rpc.SendVerificationCodeError}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.signal.registration.rpc.SendVerificationCodeError)
      org.signal.registration.rpc.SendVerificationCodeErrorOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_SendVerificationCodeError_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_SendVerificationCodeError_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.signal.registration.rpc.SendVerificationCodeError.class, org.signal.registration.rpc.SendVerificationCodeError.Builder.class);
    }

    // Construct using org.signal.registration.rpc.SendVerificationCodeError.newBuilder()
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
      errorType_ = 0;
      mayRetry_ = false;
      retryAfterSeconds_ = 0L;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_SendVerificationCodeError_descriptor;
    }

    @java.lang.Override
    public org.signal.registration.rpc.SendVerificationCodeError getDefaultInstanceForType() {
      return org.signal.registration.rpc.SendVerificationCodeError.getDefaultInstance();
    }

    @java.lang.Override
    public org.signal.registration.rpc.SendVerificationCodeError build() {
      org.signal.registration.rpc.SendVerificationCodeError result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.signal.registration.rpc.SendVerificationCodeError buildPartial() {
      org.signal.registration.rpc.SendVerificationCodeError result = new org.signal.registration.rpc.SendVerificationCodeError(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(org.signal.registration.rpc.SendVerificationCodeError result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.errorType_ = errorType_;
      }
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.mayRetry_ = mayRetry_;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.retryAfterSeconds_ = retryAfterSeconds_;
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
      if (other instanceof org.signal.registration.rpc.SendVerificationCodeError) {
        return mergeFrom((org.signal.registration.rpc.SendVerificationCodeError)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.signal.registration.rpc.SendVerificationCodeError other) {
      if (other == org.signal.registration.rpc.SendVerificationCodeError.getDefaultInstance()) return this;
      if (other.errorType_ != 0) {
        setErrorTypeValue(other.getErrorTypeValue());
      }
      if (other.getMayRetry() != false) {
        setMayRetry(other.getMayRetry());
      }
      if (other.getRetryAfterSeconds() != 0L) {
        setRetryAfterSeconds(other.getRetryAfterSeconds());
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
            case 8: {
              errorType_ = input.readEnum();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              mayRetry_ = input.readBool();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 24: {
              retryAfterSeconds_ = input.readUInt64();
              bitField0_ |= 0x00000004;
              break;
            } // case 24
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

    private int errorType_ = 0;
    /**
     * <pre>
     **
     * The type of error that prevented a verification code from being sent.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.SendVerificationCodeErrorType error_type = 1;</code>
     * @return The enum numeric value on the wire for errorType.
     */
    @java.lang.Override public int getErrorTypeValue() {
      return errorType_;
    }
    /**
     * <pre>
     **
     * The type of error that prevented a verification code from being sent.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.SendVerificationCodeErrorType error_type = 1;</code>
     * @param value The enum numeric value on the wire for errorType to set.
     * @return This builder for chaining.
     */
    public Builder setErrorTypeValue(int value) {
      errorType_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * The type of error that prevented a verification code from being sent.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.SendVerificationCodeErrorType error_type = 1;</code>
     * @return The errorType.
     */
    @java.lang.Override
    public org.signal.registration.rpc.SendVerificationCodeErrorType getErrorType() {
      org.signal.registration.rpc.SendVerificationCodeErrorType result = org.signal.registration.rpc.SendVerificationCodeErrorType.forNumber(errorType_);
      return result == null ? org.signal.registration.rpc.SendVerificationCodeErrorType.UNRECOGNIZED : result;
    }
    /**
     * <pre>
     **
     * The type of error that prevented a verification code from being sent.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.SendVerificationCodeErrorType error_type = 1;</code>
     * @param value The errorType to set.
     * @return This builder for chaining.
     */
    public Builder setErrorType(org.signal.registration.rpc.SendVerificationCodeErrorType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000001;
      errorType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * The type of error that prevented a verification code from being sent.
     * </pre>
     *
     * <code>.org.signal.registration.rpc.SendVerificationCodeErrorType error_type = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearErrorType() {
      bitField0_ = (bitField0_ & ~0x00000001);
      errorType_ = 0;
      onChanged();
      return this;
    }

    private boolean mayRetry_ ;
    /**
     * <pre>
     **
     * Indicates that this error may succeed if retried without modification after
     * a delay indicated by `retry_after_seconds`. If false, callers should not
     * retry the request without modification.
     * </pre>
     *
     * <code>bool may_retry = 2;</code>
     * @return The mayRetry.
     */
    @java.lang.Override
    public boolean getMayRetry() {
      return mayRetry_;
    }
    /**
     * <pre>
     **
     * Indicates that this error may succeed if retried without modification after
     * a delay indicated by `retry_after_seconds`. If false, callers should not
     * retry the request without modification.
     * </pre>
     *
     * <code>bool may_retry = 2;</code>
     * @param value The mayRetry to set.
     * @return This builder for chaining.
     */
    public Builder setMayRetry(boolean value) {

      mayRetry_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * Indicates that this error may succeed if retried without modification after
     * a delay indicated by `retry_after_seconds`. If false, callers should not
     * retry the request without modification.
     * </pre>
     *
     * <code>bool may_retry = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearMayRetry() {
      bitField0_ = (bitField0_ & ~0x00000002);
      mayRetry_ = false;
      onChanged();
      return this;
    }

    private long retryAfterSeconds_ ;
    /**
     * <pre>
     **
     * If this error may be retried,, indicates the duration in seconds from the
     * present after which the request may be retried without modification. This
     * value has no meaning otherwise.
     * </pre>
     *
     * <code>uint64 retry_after_seconds = 3;</code>
     * @return The retryAfterSeconds.
     */
    @java.lang.Override
    public long getRetryAfterSeconds() {
      return retryAfterSeconds_;
    }
    /**
     * <pre>
     **
     * If this error may be retried,, indicates the duration in seconds from the
     * present after which the request may be retried without modification. This
     * value has no meaning otherwise.
     * </pre>
     *
     * <code>uint64 retry_after_seconds = 3;</code>
     * @param value The retryAfterSeconds to set.
     * @return This builder for chaining.
     */
    public Builder setRetryAfterSeconds(long value) {

      retryAfterSeconds_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <pre>
     **
     * If this error may be retried,, indicates the duration in seconds from the
     * present after which the request may be retried without modification. This
     * value has no meaning otherwise.
     * </pre>
     *
     * <code>uint64 retry_after_seconds = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearRetryAfterSeconds() {
      bitField0_ = (bitField0_ & ~0x00000004);
      retryAfterSeconds_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:org.signal.registration.rpc.SendVerificationCodeError)
  }

  // @@protoc_insertion_point(class_scope:org.signal.registration.rpc.SendVerificationCodeError)
  private static final org.signal.registration.rpc.SendVerificationCodeError DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.signal.registration.rpc.SendVerificationCodeError();
  }

  public static org.signal.registration.rpc.SendVerificationCodeError getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<SendVerificationCodeError>
      PARSER = new com.google.protobuf.AbstractParser<SendVerificationCodeError>() {
    @java.lang.Override
    public SendVerificationCodeError parsePartialFrom(
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

  public static com.google.protobuf.Parser<SendVerificationCodeError> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<SendVerificationCodeError> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.signal.registration.rpc.SendVerificationCodeError getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

