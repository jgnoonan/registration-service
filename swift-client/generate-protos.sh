#!/bin/bash

# Create the output directory if it doesn't exist
mkdir -p Sources/RegistrationClient/Generated

# Generate Swift code from proto files
protoc ../src/main/proto/ldap_validation.proto \
    --proto_path=../src/main/proto \
    --swift_out=Sources/RegistrationClient/Generated \
    --grpc-swift_out=Sources/RegistrationClient/Generated \
    --swift_opt=Visibility=Public
