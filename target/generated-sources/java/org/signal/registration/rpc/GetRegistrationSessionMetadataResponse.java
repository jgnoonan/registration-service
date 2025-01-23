// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: registration_service.proto

// Protobuf Java Version: 3.25.5
package org.signal.registration.rpc;

/**
 * Protobuf type {@code org.signal.registration.rpc.GetRegistrationSessionMetadataResponse}
 */
public final class GetRegistrationSessionMetadataResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:org.signal.registration.rpc.GetRegistrationSessionMetadataResponse)
    GetRegistrationSessionMetadataResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use GetRegistrationSessionMetadataResponse.newBuilder() to construct.
  private GetRegistrationSessionMetadataResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GetRegistrationSessionMetadataResponse() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new GetRegistrationSessionMetadataResponse();
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_GetRegistrationSessionMetadataResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_GetRegistrationSessionMetadataResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            org.signal.registration.rpc.GetRegistrationSessionMetadataResponse.class, org.signal.registration.rpc.GetRegistrationSessionMetadataResponse.Builder.class);
  }

  private int responseCase_ = 0;
  @SuppressWarnings("serial")
  private java.lang.Object response_;
  public enum ResponseCase
      implements com.google.protobuf.Internal.EnumLite,
          com.google.protobuf.AbstractMessage.InternalOneOfEnum {
    SESSION_METADATA(1),
    ERROR(2),
    RESPONSE_NOT_SET(0);
    private final int value;
    private ResponseCase(int value) {
      this.value = value;
    }
    /**
     * @param value The number of the enum to look for.
     * @return The enum associated with the given number.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static ResponseCase valueOf(int value) {
      return forNumber(value);
    }

    public static ResponseCase forNumber(int value) {
      switch (value) {
        case 1: return SESSION_METADATA;
        case 2: return ERROR;
        case 0: return RESPONSE_NOT_SET;
        default: return null;
      }
    }
    public int getNumber() {
      return this.value;
    }
  };

  public ResponseCase
  getResponseCase() {
    return ResponseCase.forNumber(
        responseCase_);
  }

  public static final int SESSION_METADATA_FIELD_NUMBER = 1;
  /**
   * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
   * @return Whether the sessionMetadata field is set.
   */
  @java.lang.Override
  public boolean hasSessionMetadata() {
    return responseCase_ == 1;
  }
  /**
   * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
   * @return The sessionMetadata.
   */
  @java.lang.Override
  public org.signal.registration.rpc.RegistrationSessionMetadata getSessionMetadata() {
    if (responseCase_ == 1) {
       return (org.signal.registration.rpc.RegistrationSessionMetadata) response_;
    }
    return org.signal.registration.rpc.RegistrationSessionMetadata.getDefaultInstance();
  }
  /**
   * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
   */
  @java.lang.Override
  public org.signal.registration.rpc.RegistrationSessionMetadataOrBuilder getSessionMetadataOrBuilder() {
    if (responseCase_ == 1) {
       return (org.signal.registration.rpc.RegistrationSessionMetadata) response_;
    }
    return org.signal.registration.rpc.RegistrationSessionMetadata.getDefaultInstance();
  }

  public static final int ERROR_FIELD_NUMBER = 2;
  /**
   * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
   * @return Whether the error field is set.
   */
  @java.lang.Override
  public boolean hasError() {
    return responseCase_ == 2;
  }
  /**
   * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
   * @return The error.
   */
  @java.lang.Override
  public org.signal.registration.rpc.GetRegistrationSessionMetadataError getError() {
    if (responseCase_ == 2) {
       return (org.signal.registration.rpc.GetRegistrationSessionMetadataError) response_;
    }
    return org.signal.registration.rpc.GetRegistrationSessionMetadataError.getDefaultInstance();
  }
  /**
   * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
   */
  @java.lang.Override
  public org.signal.registration.rpc.GetRegistrationSessionMetadataErrorOrBuilder getErrorOrBuilder() {
    if (responseCase_ == 2) {
       return (org.signal.registration.rpc.GetRegistrationSessionMetadataError) response_;
    }
    return org.signal.registration.rpc.GetRegistrationSessionMetadataError.getDefaultInstance();
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
    if (responseCase_ == 1) {
      output.writeMessage(1, (org.signal.registration.rpc.RegistrationSessionMetadata) response_);
    }
    if (responseCase_ == 2) {
      output.writeMessage(2, (org.signal.registration.rpc.GetRegistrationSessionMetadataError) response_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (responseCase_ == 1) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, (org.signal.registration.rpc.RegistrationSessionMetadata) response_);
    }
    if (responseCase_ == 2) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, (org.signal.registration.rpc.GetRegistrationSessionMetadataError) response_);
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
    if (!(obj instanceof org.signal.registration.rpc.GetRegistrationSessionMetadataResponse)) {
      return super.equals(obj);
    }
    org.signal.registration.rpc.GetRegistrationSessionMetadataResponse other = (org.signal.registration.rpc.GetRegistrationSessionMetadataResponse) obj;

    if (!getResponseCase().equals(other.getResponseCase())) return false;
    switch (responseCase_) {
      case 1:
        if (!getSessionMetadata()
            .equals(other.getSessionMetadata())) return false;
        break;
      case 2:
        if (!getError()
            .equals(other.getError())) return false;
        break;
      case 0:
      default:
    }
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
    switch (responseCase_) {
      case 1:
        hash = (37 * hash) + SESSION_METADATA_FIELD_NUMBER;
        hash = (53 * hash) + getSessionMetadata().hashCode();
        break;
      case 2:
        hash = (37 * hash) + ERROR_FIELD_NUMBER;
        hash = (53 * hash) + getError().hashCode();
        break;
      case 0:
      default:
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse parseFrom(
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
  public static Builder newBuilder(org.signal.registration.rpc.GetRegistrationSessionMetadataResponse prototype) {
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
   * Protobuf type {@code org.signal.registration.rpc.GetRegistrationSessionMetadataResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:org.signal.registration.rpc.GetRegistrationSessionMetadataResponse)
      org.signal.registration.rpc.GetRegistrationSessionMetadataResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_GetRegistrationSessionMetadataResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_GetRegistrationSessionMetadataResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              org.signal.registration.rpc.GetRegistrationSessionMetadataResponse.class, org.signal.registration.rpc.GetRegistrationSessionMetadataResponse.Builder.class);
    }

    // Construct using org.signal.registration.rpc.GetRegistrationSessionMetadataResponse.newBuilder()
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
      if (sessionMetadataBuilder_ != null) {
        sessionMetadataBuilder_.clear();
      }
      if (errorBuilder_ != null) {
        errorBuilder_.clear();
      }
      responseCase_ = 0;
      response_ = null;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.signal.registration.rpc.RegistrationServiceOuterClass.internal_static_org_signal_registration_rpc_GetRegistrationSessionMetadataResponse_descriptor;
    }

    @java.lang.Override
    public org.signal.registration.rpc.GetRegistrationSessionMetadataResponse getDefaultInstanceForType() {
      return org.signal.registration.rpc.GetRegistrationSessionMetadataResponse.getDefaultInstance();
    }

    @java.lang.Override
    public org.signal.registration.rpc.GetRegistrationSessionMetadataResponse build() {
      org.signal.registration.rpc.GetRegistrationSessionMetadataResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public org.signal.registration.rpc.GetRegistrationSessionMetadataResponse buildPartial() {
      org.signal.registration.rpc.GetRegistrationSessionMetadataResponse result = new org.signal.registration.rpc.GetRegistrationSessionMetadataResponse(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      buildPartialOneofs(result);
      onBuilt();
      return result;
    }

    private void buildPartial0(org.signal.registration.rpc.GetRegistrationSessionMetadataResponse result) {
      int from_bitField0_ = bitField0_;
    }

    private void buildPartialOneofs(org.signal.registration.rpc.GetRegistrationSessionMetadataResponse result) {
      result.responseCase_ = responseCase_;
      result.response_ = this.response_;
      if (responseCase_ == 1 &&
          sessionMetadataBuilder_ != null) {
        result.response_ = sessionMetadataBuilder_.build();
      }
      if (responseCase_ == 2 &&
          errorBuilder_ != null) {
        result.response_ = errorBuilder_.build();
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
      if (other instanceof org.signal.registration.rpc.GetRegistrationSessionMetadataResponse) {
        return mergeFrom((org.signal.registration.rpc.GetRegistrationSessionMetadataResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(org.signal.registration.rpc.GetRegistrationSessionMetadataResponse other) {
      if (other == org.signal.registration.rpc.GetRegistrationSessionMetadataResponse.getDefaultInstance()) return this;
      switch (other.getResponseCase()) {
        case SESSION_METADATA: {
          mergeSessionMetadata(other.getSessionMetadata());
          break;
        }
        case ERROR: {
          mergeError(other.getError());
          break;
        }
        case RESPONSE_NOT_SET: {
          break;
        }
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
            case 10: {
              input.readMessage(
                  getSessionMetadataFieldBuilder().getBuilder(),
                  extensionRegistry);
              responseCase_ = 1;
              break;
            } // case 10
            case 18: {
              input.readMessage(
                  getErrorFieldBuilder().getBuilder(),
                  extensionRegistry);
              responseCase_ = 2;
              break;
            } // case 18
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
    private int responseCase_ = 0;
    private java.lang.Object response_;
    public ResponseCase
        getResponseCase() {
      return ResponseCase.forNumber(
          responseCase_);
    }

    public Builder clearResponse() {
      responseCase_ = 0;
      response_ = null;
      onChanged();
      return this;
    }

    private int bitField0_;

    private com.google.protobuf.SingleFieldBuilderV3<
        org.signal.registration.rpc.RegistrationSessionMetadata, org.signal.registration.rpc.RegistrationSessionMetadata.Builder, org.signal.registration.rpc.RegistrationSessionMetadataOrBuilder> sessionMetadataBuilder_;
    /**
     * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
     * @return Whether the sessionMetadata field is set.
     */
    @java.lang.Override
    public boolean hasSessionMetadata() {
      return responseCase_ == 1;
    }
    /**
     * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
     * @return The sessionMetadata.
     */
    @java.lang.Override
    public org.signal.registration.rpc.RegistrationSessionMetadata getSessionMetadata() {
      if (sessionMetadataBuilder_ == null) {
        if (responseCase_ == 1) {
          return (org.signal.registration.rpc.RegistrationSessionMetadata) response_;
        }
        return org.signal.registration.rpc.RegistrationSessionMetadata.getDefaultInstance();
      } else {
        if (responseCase_ == 1) {
          return sessionMetadataBuilder_.getMessage();
        }
        return org.signal.registration.rpc.RegistrationSessionMetadata.getDefaultInstance();
      }
    }
    /**
     * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
     */
    public Builder setSessionMetadata(org.signal.registration.rpc.RegistrationSessionMetadata value) {
      if (sessionMetadataBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        response_ = value;
        onChanged();
      } else {
        sessionMetadataBuilder_.setMessage(value);
      }
      responseCase_ = 1;
      return this;
    }
    /**
     * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
     */
    public Builder setSessionMetadata(
        org.signal.registration.rpc.RegistrationSessionMetadata.Builder builderForValue) {
      if (sessionMetadataBuilder_ == null) {
        response_ = builderForValue.build();
        onChanged();
      } else {
        sessionMetadataBuilder_.setMessage(builderForValue.build());
      }
      responseCase_ = 1;
      return this;
    }
    /**
     * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
     */
    public Builder mergeSessionMetadata(org.signal.registration.rpc.RegistrationSessionMetadata value) {
      if (sessionMetadataBuilder_ == null) {
        if (responseCase_ == 1 &&
            response_ != org.signal.registration.rpc.RegistrationSessionMetadata.getDefaultInstance()) {
          response_ = org.signal.registration.rpc.RegistrationSessionMetadata.newBuilder((org.signal.registration.rpc.RegistrationSessionMetadata) response_)
              .mergeFrom(value).buildPartial();
        } else {
          response_ = value;
        }
        onChanged();
      } else {
        if (responseCase_ == 1) {
          sessionMetadataBuilder_.mergeFrom(value);
        } else {
          sessionMetadataBuilder_.setMessage(value);
        }
      }
      responseCase_ = 1;
      return this;
    }
    /**
     * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
     */
    public Builder clearSessionMetadata() {
      if (sessionMetadataBuilder_ == null) {
        if (responseCase_ == 1) {
          responseCase_ = 0;
          response_ = null;
          onChanged();
        }
      } else {
        if (responseCase_ == 1) {
          responseCase_ = 0;
          response_ = null;
        }
        sessionMetadataBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
     */
    public org.signal.registration.rpc.RegistrationSessionMetadata.Builder getSessionMetadataBuilder() {
      return getSessionMetadataFieldBuilder().getBuilder();
    }
    /**
     * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
     */
    @java.lang.Override
    public org.signal.registration.rpc.RegistrationSessionMetadataOrBuilder getSessionMetadataOrBuilder() {
      if ((responseCase_ == 1) && (sessionMetadataBuilder_ != null)) {
        return sessionMetadataBuilder_.getMessageOrBuilder();
      } else {
        if (responseCase_ == 1) {
          return (org.signal.registration.rpc.RegistrationSessionMetadata) response_;
        }
        return org.signal.registration.rpc.RegistrationSessionMetadata.getDefaultInstance();
      }
    }
    /**
     * <code>.org.signal.registration.rpc.RegistrationSessionMetadata session_metadata = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        org.signal.registration.rpc.RegistrationSessionMetadata, org.signal.registration.rpc.RegistrationSessionMetadata.Builder, org.signal.registration.rpc.RegistrationSessionMetadataOrBuilder> 
        getSessionMetadataFieldBuilder() {
      if (sessionMetadataBuilder_ == null) {
        if (!(responseCase_ == 1)) {
          response_ = org.signal.registration.rpc.RegistrationSessionMetadata.getDefaultInstance();
        }
        sessionMetadataBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            org.signal.registration.rpc.RegistrationSessionMetadata, org.signal.registration.rpc.RegistrationSessionMetadata.Builder, org.signal.registration.rpc.RegistrationSessionMetadataOrBuilder>(
                (org.signal.registration.rpc.RegistrationSessionMetadata) response_,
                getParentForChildren(),
                isClean());
        response_ = null;
      }
      responseCase_ = 1;
      onChanged();
      return sessionMetadataBuilder_;
    }

    private com.google.protobuf.SingleFieldBuilderV3<
        org.signal.registration.rpc.GetRegistrationSessionMetadataError, org.signal.registration.rpc.GetRegistrationSessionMetadataError.Builder, org.signal.registration.rpc.GetRegistrationSessionMetadataErrorOrBuilder> errorBuilder_;
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
     * @return Whether the error field is set.
     */
    @java.lang.Override
    public boolean hasError() {
      return responseCase_ == 2;
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
     * @return The error.
     */
    @java.lang.Override
    public org.signal.registration.rpc.GetRegistrationSessionMetadataError getError() {
      if (errorBuilder_ == null) {
        if (responseCase_ == 2) {
          return (org.signal.registration.rpc.GetRegistrationSessionMetadataError) response_;
        }
        return org.signal.registration.rpc.GetRegistrationSessionMetadataError.getDefaultInstance();
      } else {
        if (responseCase_ == 2) {
          return errorBuilder_.getMessage();
        }
        return org.signal.registration.rpc.GetRegistrationSessionMetadataError.getDefaultInstance();
      }
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
     */
    public Builder setError(org.signal.registration.rpc.GetRegistrationSessionMetadataError value) {
      if (errorBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        response_ = value;
        onChanged();
      } else {
        errorBuilder_.setMessage(value);
      }
      responseCase_ = 2;
      return this;
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
     */
    public Builder setError(
        org.signal.registration.rpc.GetRegistrationSessionMetadataError.Builder builderForValue) {
      if (errorBuilder_ == null) {
        response_ = builderForValue.build();
        onChanged();
      } else {
        errorBuilder_.setMessage(builderForValue.build());
      }
      responseCase_ = 2;
      return this;
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
     */
    public Builder mergeError(org.signal.registration.rpc.GetRegistrationSessionMetadataError value) {
      if (errorBuilder_ == null) {
        if (responseCase_ == 2 &&
            response_ != org.signal.registration.rpc.GetRegistrationSessionMetadataError.getDefaultInstance()) {
          response_ = org.signal.registration.rpc.GetRegistrationSessionMetadataError.newBuilder((org.signal.registration.rpc.GetRegistrationSessionMetadataError) response_)
              .mergeFrom(value).buildPartial();
        } else {
          response_ = value;
        }
        onChanged();
      } else {
        if (responseCase_ == 2) {
          errorBuilder_.mergeFrom(value);
        } else {
          errorBuilder_.setMessage(value);
        }
      }
      responseCase_ = 2;
      return this;
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
     */
    public Builder clearError() {
      if (errorBuilder_ == null) {
        if (responseCase_ == 2) {
          responseCase_ = 0;
          response_ = null;
          onChanged();
        }
      } else {
        if (responseCase_ == 2) {
          responseCase_ = 0;
          response_ = null;
        }
        errorBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
     */
    public org.signal.registration.rpc.GetRegistrationSessionMetadataError.Builder getErrorBuilder() {
      return getErrorFieldBuilder().getBuilder();
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
     */
    @java.lang.Override
    public org.signal.registration.rpc.GetRegistrationSessionMetadataErrorOrBuilder getErrorOrBuilder() {
      if ((responseCase_ == 2) && (errorBuilder_ != null)) {
        return errorBuilder_.getMessageOrBuilder();
      } else {
        if (responseCase_ == 2) {
          return (org.signal.registration.rpc.GetRegistrationSessionMetadataError) response_;
        }
        return org.signal.registration.rpc.GetRegistrationSessionMetadataError.getDefaultInstance();
      }
    }
    /**
     * <code>.org.signal.registration.rpc.GetRegistrationSessionMetadataError error = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        org.signal.registration.rpc.GetRegistrationSessionMetadataError, org.signal.registration.rpc.GetRegistrationSessionMetadataError.Builder, org.signal.registration.rpc.GetRegistrationSessionMetadataErrorOrBuilder> 
        getErrorFieldBuilder() {
      if (errorBuilder_ == null) {
        if (!(responseCase_ == 2)) {
          response_ = org.signal.registration.rpc.GetRegistrationSessionMetadataError.getDefaultInstance();
        }
        errorBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            org.signal.registration.rpc.GetRegistrationSessionMetadataError, org.signal.registration.rpc.GetRegistrationSessionMetadataError.Builder, org.signal.registration.rpc.GetRegistrationSessionMetadataErrorOrBuilder>(
                (org.signal.registration.rpc.GetRegistrationSessionMetadataError) response_,
                getParentForChildren(),
                isClean());
        response_ = null;
      }
      responseCase_ = 2;
      onChanged();
      return errorBuilder_;
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


    // @@protoc_insertion_point(builder_scope:org.signal.registration.rpc.GetRegistrationSessionMetadataResponse)
  }

  // @@protoc_insertion_point(class_scope:org.signal.registration.rpc.GetRegistrationSessionMetadataResponse)
  private static final org.signal.registration.rpc.GetRegistrationSessionMetadataResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new org.signal.registration.rpc.GetRegistrationSessionMetadataResponse();
  }

  public static org.signal.registration.rpc.GetRegistrationSessionMetadataResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GetRegistrationSessionMetadataResponse>
      PARSER = new com.google.protobuf.AbstractParser<GetRegistrationSessionMetadataResponse>() {
    @java.lang.Override
    public GetRegistrationSessionMetadataResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<GetRegistrationSessionMetadataResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<GetRegistrationSessionMetadataResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public org.signal.registration.rpc.GetRegistrationSessionMetadataResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

