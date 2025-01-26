// swift-tools-version:5.7
import PackageDescription

let package = Package(
    name: "RegistrationClient",
    platforms: [
        .iOS(.v13),
        .macOS(.v10_15)
    ],
    products: [
        .library(
            name: "RegistrationClient",
            targets: ["RegistrationClient"]),
    ],
    dependencies: [
        .package(url: "https://github.com/grpc/grpc-swift.git", from: "1.20.0"),
    ],
    targets: [
        .target(
            name: "RegistrationClient",
            dependencies: [
                .product(name: "GRPC", package: "grpc-swift"),
            ],
            path: "Sources/RegistrationClient"
        ),
        .testTarget(
            name: "RegistrationClientTests",
            dependencies: ["RegistrationClient"],
            path: "Tests/RegistrationClientTests"
        ),
    ]
)
