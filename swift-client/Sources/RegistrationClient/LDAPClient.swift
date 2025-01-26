import Foundation
import GRPC
import NIO

public class LDAPClient {
    private let client: LdapValidationServiceClient
    private let group: EventLoopGroup
    
    public init(host: String = "localhost", port: Int = 50051) {
        group = PlatformSupport.makeEventLoopGroup(loopCount: 1)
        
        let channel = ClientConnection
            .insecure(group: group)
            .connect(host: host, port: port)
        
        client = LdapValidationServiceClient(channel: channel)
    }
    
    deinit {
        try? group.syncShutdownGracefully()
    }
    
    public func validateCredentials(userId: String, password: String) async throws -> String {
        var request = ValidateCredentialsRequest()
        request.userId = userId
        request.password = password
        
        let response = try await client.validateCredentials(request)
        
        switch response.result {
        case .phoneNumber(let number):
            return number
        case .error(let error):
            throw LDAPError(error)
        case .none:
            throw LDAPError.unknown
        }
    }
}

public enum LDAPError: Error {
    case invalidCredentials
    case userNotFound
    case phoneNumberNotFound
    case serverError
    case unknown
    
    init(_ error: ValidateCredentialsError) {
        switch error.errorType {
        case .validateCredentialsErrorTypeInvalidCredentials:
            self = .invalidCredentials
        case .validateCredentialsErrorTypeUserNotFound:
            self = .userNotFound
        case .validateCredentialsErrorTypePhoneNumberNotFound:
            self = .phoneNumberNotFound
        case .validateCredentialsErrorTypeServerError:
            self = .serverError
        case .validateCredentialsErrorTypeUnspecified:
            self = .unknown
        default:
            self = .unknown
        }
    }
}
