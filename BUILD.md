# Building Registration Service

This document describes how to build the Registration Service project.

## Prerequisites

- Java 21 (JDK)
- Maven 3.9.9 or higher
- protoc (Protocol Buffers compiler)
- Docker (for running tests and local development)

## Build Commands

### Clean Build (Skip Tests)
```bash
./mvnw clean install -DskipTests -Djgitver.skip=true
```

### Build with Tests
```bash
./mvnw clean install
```

### Run Locally
```bash
./mvnw mn:run
```

## Build Options

- `-DskipTests`: Skip running tests
- `-Djgitver.skip=true`: Skip version generation using jgitver
- `-Dexec.skip=true`: Skip execution of Java programs
- `-Dmaven.test.skip=true`: Skip test compilation and execution

## Protobuf Generation

Protocol Buffer files are automatically generated during the build process. The generated files can be found in:
- `target/generated-sources/protobuf/java`
- `target/generated-sources/protobuf/grpc-java`

To manually generate protobuf files:
```bash
mkdir -p target/generated-sources/protobuf/java
protoc -I=src/main/proto --java_out=target/generated-sources/protobuf/java src/main/proto/*.proto
```

## Docker Build

Build the Docker image:
```bash
./mvnw compile jib:dockerBuild
```

## Development Environment

### Running with Docker Compose
```bash
docker-compose up
```

This will start:
- The registration service
- Required dependencies (Redis, etc.)

### Running Tests
```bash
./mvnw test
```

## Troubleshooting

1. If you encounter dependency convergence errors, try building with `-Denforcer.skip=true`
2. For protobuf-related issues, ensure you have the correct version of protoc installed (version 3.25.5)
3. For Java version issues, make sure you're using Java 21 with preview features enabled
4. If you encounter Docker-related issues, ensure your Docker daemon is running and you have sufficient permissions

## Dependencies

The project uses several key dependencies:
- Micronaut Framework 4.6.3
- Protocol Buffers 3.25.5
- gRPC 1.61.0
- Reactor gRPC 1.2.4
