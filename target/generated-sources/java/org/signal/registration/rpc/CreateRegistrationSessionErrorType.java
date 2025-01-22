// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: registration_service.proto

// Protobuf Java Version: 3.25.5
package org.signal.registration.rpc;

/**
 * Protobuf enum {@code org.signal.registration.rpc.CreateRegistrationSessionErrorType}
 */
public enum CreateRegistrationSessionErrorType
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <code>CREATE_REGISTRATION_SESSION_ERROR_TYPE_UNSPECIFIED = 0;</code>
   */
  CREATE_REGISTRATION_SESSION_ERROR_TYPE_UNSPECIFIED(0),
  /**
   * <pre>
   **
   * Indicates that a session could not be created because too many requests to
   * create a session for the given phone number have been received in some
   * window of time. Callers should wait and try again later.
   * </pre>
   *
   * <code>CREATE_REGISTRATION_SESSION_ERROR_TYPE_RATE_LIMITED = 1;</code>
   */
  CREATE_REGISTRATION_SESSION_ERROR_TYPE_RATE_LIMITED(1),
  /**
   * <pre>
   **
   * Indicates that the provided phone number could not be parsed.
   * </pre>
   *
   * <code>CREATE_REGISTRATION_SESSION_ERROR_TYPE_ILLEGAL_PHONE_NUMBER = 2;</code>
   */
  CREATE_REGISTRATION_SESSION_ERROR_TYPE_ILLEGAL_PHONE_NUMBER(2),
  UNRECOGNIZED(-1),
  ;

  /**
   * <code>CREATE_REGISTRATION_SESSION_ERROR_TYPE_UNSPECIFIED = 0;</code>
   */
  public static final int CREATE_REGISTRATION_SESSION_ERROR_TYPE_UNSPECIFIED_VALUE = 0;
  /**
   * <pre>
   **
   * Indicates that a session could not be created because too many requests to
   * create a session for the given phone number have been received in some
   * window of time. Callers should wait and try again later.
   * </pre>
   *
   * <code>CREATE_REGISTRATION_SESSION_ERROR_TYPE_RATE_LIMITED = 1;</code>
   */
  public static final int CREATE_REGISTRATION_SESSION_ERROR_TYPE_RATE_LIMITED_VALUE = 1;
  /**
   * <pre>
   **
   * Indicates that the provided phone number could not be parsed.
   * </pre>
   *
   * <code>CREATE_REGISTRATION_SESSION_ERROR_TYPE_ILLEGAL_PHONE_NUMBER = 2;</code>
   */
  public static final int CREATE_REGISTRATION_SESSION_ERROR_TYPE_ILLEGAL_PHONE_NUMBER_VALUE = 2;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static CreateRegistrationSessionErrorType valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static CreateRegistrationSessionErrorType forNumber(int value) {
    switch (value) {
      case 0: return CREATE_REGISTRATION_SESSION_ERROR_TYPE_UNSPECIFIED;
      case 1: return CREATE_REGISTRATION_SESSION_ERROR_TYPE_RATE_LIMITED;
      case 2: return CREATE_REGISTRATION_SESSION_ERROR_TYPE_ILLEGAL_PHONE_NUMBER;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<CreateRegistrationSessionErrorType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      CreateRegistrationSessionErrorType> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<CreateRegistrationSessionErrorType>() {
          public CreateRegistrationSessionErrorType findValueByNumber(int number) {
            return CreateRegistrationSessionErrorType.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return org.signal.registration.rpc.RegistrationServiceOuterClass.getDescriptor().getEnumTypes().get(0);
  }

  private static final CreateRegistrationSessionErrorType[] VALUES = values();

  public static CreateRegistrationSessionErrorType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private CreateRegistrationSessionErrorType(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:org.signal.registration.rpc.CreateRegistrationSessionErrorType)
}

