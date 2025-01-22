// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: registration_service.proto

// Protobuf Java Version: 3.25.5
package org.signal.registration.rpc;

/**
 * Protobuf type {@code org.signal.registration.rpc.GetRegistrationSessionMetadataError}
 */
public final class GetRegistrationSessionMetadataError extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.signal.registration.rpc.GetRegistrationSessionMetadataError)
    GetRegistrationSessionMetadataErrorOrBuilder {
private static final long serialVersionUID = 0L;
  // Use GetRegistrationSessionMetadataError.newBuilder() to construct.
  private GetRegistrationSessionMetadataError(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GetRegistrationSessionMetadataError() {
    errorType_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new GetRegistrationSessionMetadataError();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_GetRegistrationSessionMetadataError_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_GetRegistrationSessionMetadataError_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.signal.registration.rpc.GetRegistrationSessionMetadataError.class, org.signal.registration.rpc.GetRegistrationSessionMetadataError.Builder.class);
  }

  public static final int ERROR_TYPE_FIELD_NUMBER = 1;
  private int errorType_ = 0;
  /**
   * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType error_type = 1;</code>
   * @return The enum numeric value on the wire for errorType.
   */
  @java.lang.Override public int getErrorTypeValue() {
    return errorType_;
  }
  /**
   * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType error_type = 1;</code>
   * @return The errorType.
   */
  @java.lang.Override public org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType getErrorType() {
    org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType result = org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType.forNumber(errorType_);
    return result == null ? org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType.UNRECOGNIZED : result;
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
    if (errorType_ != org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType.GET_REGISTRATION_SESSION_METADATA_ERROR_TYPE_UNSPECIFIED.getNumber()) {
      output.writeEnum(1, errorType_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (errorType_ != org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType.GET_REGISTRATION_SESSION_METADATA_ERROR_TYPE_UNSPECIFIED.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, errorType_);
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
    if (!(obj instanceof org.signal.registration.rpc.GetRegistrationSessionMetadataError)) {
      return super.equals(obj);
    }
    org.signal.registration.rpc.GetRegistrationSessionMetadataError other = (org.signal.registration.rpc.GetRegistrationSessionMetadataError) obj;

    if (errorType_ != other.errorType_) return false;
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
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError parseFrom(
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
  public static Builder newBuilder(org.signal.registration.rpc.GetRegistrationSessionMetadataError prototype) {
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
   * Protobuf type {@code org.signal.registration.rpc.GetRegistrationSessionMetadataError}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.signal.registration.rpc.GetRegistrationSessionMetadataError)
      org.signal.registration.rpc.GetRegistrationSessionMetadataErrorOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_GetRegistrationSessionMetadataError_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_GetRegistrationSessionMetadataError_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.signal.registration.rpc.GetRegistrationSessionMetadataError.class, org.signal.registration.rpc.GetRegistrationSessionMetadataError.Builder.class);
    }

    // Construct using org.signal.registration.rpc.GetRegistrationSessionMetadataError.newBuilder()
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
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_GetRegistrationSessionMetadataError_descriptor;
    }

    @java.lang.Override
    public org.signal.registration.rpc.GetRegistrationSessionMetadataError getDefaultInstanceForType() {
      return org.signal.registration.rpc.GetRegistrationSessionMetadataError.getDefaultInstance();
    }

    @java.lang.Override
    public org.signal.registration.rpc.GetRegistrationSessionMetadataError build() {
      org.signal.registration.rpc.GetRegistrationSessionMetadataError result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.signal.registration.rpc.GetRegistrationSessionMetadataError buildPartial() {
      org.signal.registration.rpc.GetRegistrationSessionMetadataError result = new org.signal.registration.rpc.GetRegistrationSessionMetadataError(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(org.signal.registration.rpc.GetRegistrationSessionMetadataError result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.errorType_ = errorType_;
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
      if (other instanceof org.signal.registration.rpc.GetRegistrationSessionMetadataError) {
        return mergeFrom((org.signal.registration.rpc.GetRegistrationSessionMetadataError)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.signal.registration.rpc.GetRegistrationSessionMetadataError other) {
      if (other == org.signal.registration.rpc.GetRegistrationSessionMetadataError.getDefaultInstance()) return this;
      if (other.errorType_ != 0) {
        setErrorTypeValue(other.getErrorTypeValue());
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
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType error_type = 1;</code>
     * @return The enum numeric value on the wire for errorType.
     */
    @java.lang.Override public int getErrorTypeValue() {
      return errorType_;
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType error_type = 1;</code>
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
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType error_type = 1;</code>
     * @return The errorType.
     */
    @java.lang.Override
    public org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType getErrorType() {
      org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType result = org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType.forNumber(errorType_);
      return result == null ? org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType.UNRECOGNIZED : result;
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType error_type = 1;</code>
     * @param value The errorType to set.
     * @return This builder for chaining.
     */
    public Builder setErrorType(org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000001;
      errorType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataErrorType error_type = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearErrorType() {
      bitField0_ = (bitField0_ & ~0x00000001);
      errorType_ = 0;
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


    // @@protoc_insertion_point(builder_scope:org.signal.registration.rpc.GetRegistrationSessionMetadataError)
  }

  // @@protoc_insertion_point(class_scope:org.signal.registration.rpc.GetRegistrationSessionMetadataError)
  private static final org.signal.registration.rpc.GetRegistrationSessionMetadataError DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.signal.registration.rpc.GetRegistrationSessionMetadataError();
  }

  public static org.signal.registration.rpc.GetRegistrationSessionMetadataError getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GetRegistrationSessionMetadataError>
      PARSER = new com.google.protobuf.AbstractParser<GetRegistrationSessionMetadataError>() {
    @java.lang.Override
    public GetRegistrationSessionMetadataError parsePartialFrom(
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

  public static com.google.protobuf.Parser<GetRegistrationSessionMetadataError> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<GetRegistrationSessionMetadataError> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.signal.registration.rpc.GetRegistrationSessionMetadataError getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

