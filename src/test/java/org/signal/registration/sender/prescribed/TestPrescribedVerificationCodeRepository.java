package org.signal.registration.sender.prescribed;

import com.google.i18n.phonenumbers.Phonenumber;
import io.micronaut.context.annotation.Requires;
import jakarta.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
@Requires(env = "test")
public class TestPrescribedVerificationCodeRepository implements PrescribedVerificationCodeRepository {
    private final Map<Phonenumber.PhoneNumber, String> verificationCodes = new HashMap<>();

    @Override
    public CompletableFuture<Map<Phonenumber.PhoneNumber, String>> getVerificationCodes() {
        return CompletableFuture.completedFuture(new HashMap<>(verificationCodes));
    }

    public void setVerificationCode(Phonenumber.PhoneNumber phoneNumber, String code) {
        verificationCodes.put(phoneNumber, code);
    }

    public void clear() {
        verificationCodes.clear();
    }
}
