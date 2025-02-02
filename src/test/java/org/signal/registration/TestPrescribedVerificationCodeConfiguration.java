package org.signal.registration;

import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;
import org.signal.registration.sender.prescribed.PrescribedVerificationCodeRepository;
import org.signal.registration.sender.prescribed.TestPrescribedVerificationCodeRepository;

@Factory
@Requires(env = "test")
public class TestPrescribedVerificationCodeConfiguration {

    @Singleton
    @Primary
    PrescribedVerificationCodeRepository prescribedVerificationCodeRepository() {
        return new TestPrescribedVerificationCodeRepository();
    }
}
