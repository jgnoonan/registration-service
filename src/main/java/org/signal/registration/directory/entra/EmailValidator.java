package org.signal.registration.directory.entra;

import java.util.regex.Pattern;

public class EmailValidator {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+(?:#EXT#)?@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
