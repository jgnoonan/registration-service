// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: session.proto

// Protobuf Java Version: 3.25.5
package org.signal.registration.session;

public final class Session {
  private Session() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_signal_registration_session_RegistrationSession_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_signal_registration_session_RegistrationSession_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_signal_registration_session_FailedSendAttempt_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_signal_registration_session_FailedSendAttempt_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_signal_registration_session_RegistrationAttempt_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_signal_registration_session_RegistrationAttempt_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_org_signal_registration_session_SessionMetadata_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_org_signal_registration_session_SessionMetadata_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rsession.proto\022\037org.signal.registration" +
      ".session\032\032registration_service.proto\"\236\004\n" +
      "\023RegistrationSession\022\024\n\014phone_number\030\001 \001" +
      "(\t\022\025\n\rverified_code\030\004 \001(\t\022S\n\025registratio" +
      "n_attempts\030\005 \003(\01324.org.signal.registrati" +
      "on.session.RegistrationAttempt\022\n\n\002id\030\006 \001" +
      "(\014\022\033\n\023check_code_attempts\030\007 \001(\r\022,\n$last_" +
      "check_code_attempt_epoch_millis\030\010 \001(\004\022\037\n" +
      "\027expiration_epoch_millis\030\t \001(\004\022\034\n\024create" +
      "d_epoch_millis\030\n \001(\004\022J\n\020session_metadata" +
      "\030\013 \001(\01320.org.signal.registration.session" +
      ".SessionMetadata\022J\n\023rejected_transports\030" +
      "\014 \003(\0162-.org.signal.registration.rpc.Mess" +
      "ageTransport\022K\n\017failed_attempts\030\r \003(\01322." +
      "org.signal.registration.session.FailedSe" +
      "ndAttemptJ\004\010\002\020\003J\004\010\003\020\004\"\271\002\n\021FailedSendAtte" +
      "mpt\022\036\n\026timestamp_epoch_millis\030\001 \001(\004\022\023\n\013s" +
      "ender_name\030\002 \001(\t\022H\n\021message_transport\030\003 " +
      "\001(\0162-.org.signal.registration.rpc.Messag" +
      "eTransport\022<\n\013client_type\030\004 \001(\0162\'.org.si" +
      "gnal.registration.rpc.ClientType\022M\n\022fail" +
      "ed_send_reason\030\005 \001(\01621.org.signal.regist" +
      "ration.session.FailedSendReason\022\030\n\020selec" +
      "tion_reason\030\006 \001(\t\"\265\002\n\023RegistrationAttemp" +
      "t\022\036\n\026timestamp_epoch_millis\030\001 \001(\004\022\023\n\013sen" +
      "der_name\030\002 \001(\t\022H\n\021message_transport\030\003 \001(" +
      "\0162-.org.signal.registration.rpc.MessageT" +
      "ransport\022\023\n\013sender_data\030\004 \001(\014\022\037\n\027expirat" +
      "ion_epoch_millis\030\005 \001(\004\022\021\n\tremote_id\030\006 \001(" +
      "\t\022<\n\013client_type\030\007 \001(\0162\'.org.signal.regi" +
      "stration.rpc.ClientType\022\030\n\020selection_rea" +
      "son\030\010 \001(\t\"W\n\017SessionMetadata\022 \n\030account_" +
      "exists_with_e164\030\001 \001(\010\022\020\n\010username\030\002 \001(\t" +
      "\022\020\n\010password\030\003 \001(\t*\243\001\n\020FailedSendReason\022" +
      "\"\n\036FAILED_SEND_REASON_UNSPECIFIED\020\000\022&\n\"F" +
      "AILED_SEND_REASON_SUSPECTED_FRAUD\020\001\022\037\n\033F" +
      "AILED_SEND_REASON_REJECTED\020\002\022\"\n\036FAILED_S" +
      "END_REASON_UNAVAILABLE\020\003B\002P\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          org.signal.registration.rpc.RegistrationServiceOuterClass.getDescriptor(),
        });
    internal_static_org_signal_registration_session_RegistrationSession_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_org_signal_registration_session_RegistrationSession_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_signal_registration_session_RegistrationSession_descriptor,
        new java.lang.String[] { "PhoneNumber", "VerifiedCode", "RegistrationAttempts", "Id", "CheckCodeAttempts", "LastCheckCodeAttemptEpochMillis", "ExpirationEpochMillis", "CreatedEpochMillis", "SessionMetadata", "RejectedTransports", "FailedAttempts", });
    internal_static_org_signal_registration_session_FailedSendAttempt_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_org_signal_registration_session_FailedSendAttempt_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_signal_registration_session_FailedSendAttempt_descriptor,
        new java.lang.String[] { "TimestampEpochMillis", "SenderName", "MessageTransport", "ClientType", "FailedSendReason", "SelectionReason", });
    internal_static_org_signal_registration_session_RegistrationAttempt_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_org_signal_registration_session_RegistrationAttempt_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_signal_registration_session_RegistrationAttempt_descriptor,
        new java.lang.String[] { "TimestampEpochMillis", "SenderName", "MessageTransport", "SenderData", "ExpirationEpochMillis", "RemoteId", "ClientType", "SelectionReason", });
    internal_static_org_signal_registration_session_SessionMetadata_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_org_signal_registration_session_SessionMetadata_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_org_signal_registration_session_SessionMetadata_descriptor,
        new java.lang.String[] { "AccountExistsWithE164", "Username", "Password", });
    org.signal.registration.rpc.RegistrationServiceOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
