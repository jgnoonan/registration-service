---
name: Maven Test Failures
about: Maven build issues preventing test execution
title: 'Fix Maven Test Execution Issues After LDAP Configuration Update'
labels: 'bug, build-failure'
assignees: ''
---

## Issue Description
Maven build failures are occurring when running tests after recent LDAP configuration cleanup. While the application runs correctly in development and production environments, the test execution phase is failing.

## Current Behavior
- All tests fail to execute properly
- Maven build process fails during test phase
- Error occurs even with clean builds and specific test targeting

## Technical Details

### Environment
- OS: macOS
- Java Version: 17.0.14
- Maven Version: 3.9.9

### Error Messages
1. Dependency conflicts warning:
```
WARNING: 'dependencyManagement.dependencies.dependency.(groupId:artifactId:type:classifier)' must be unique: 
org.apache.kafka:kafka-clients:jar -> version ${kafka.compat.version} vs ${kafka.version}
```

2. Build failure during test phase with MojoExecutionException

### Investigation Steps Taken
1. Attempted clean builds (`mvn clean test`)
2. Tried running specific test classes (`-Dtest=LdapAuthenticationTest`)
3. Used Maven wrapper instead of system Maven (`./mvnw`)
4. Added debug flags (`-X`) for detailed error output
5. Verified application runs correctly with `mvn mn:run`

## Expected Behavior
- Tests should execute successfully
- No dependency conflicts
- Clean test output

## Proposed Solutions
1. Resolve the kafka-clients dependency conflict by:
   - Standardizing on a single version variable
   - Explicitly defining version in dependencyManagement
2. Review and update Maven build configuration
3. Verify test environment configuration matches production settings

## Additional Context
- This issue appeared after cleaning up LDAP configuration in application.yml
- The application itself works correctly in all environments
- Only test execution is affected
- Related commit: Remove duplicate LDAP configuration and unused testPhoneNumber property (tag: v1.0.12)

## Impact
- Blocks automated testing
- May affect CI/CD pipeline
- Manual testing required for changes

## Priority
Medium - Application works correctly in production, but automated testing is blocked.
