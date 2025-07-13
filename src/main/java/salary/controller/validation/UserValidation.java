package salary.controller.validation;

import java.util.regex.Pattern;

public class UserValidation {
    private static final Pattern NAME_PATTERN =
            Pattern.compile("(^[ آ-ی]{2,30}$)");

    private static final Pattern FAMILY_PATTERN =
            Pattern.compile("(^[ آ-ی]{2,30}$)");

    private static final Pattern NATIONALID_PATTERN =
            Pattern.compile("(^\\d{10}$)");

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z][a-zA-Z0-9_.]{4,19}$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile(
                    "^(?=.*[0-9])" +
                            "(?=.*[a-z])" +
                            "(?=.*[A-Z])" +
                            "(?=.*[@#$%^&+=!])" +
                            "(?=\\S+$).{8,20}$"
            );
    public static boolean isValidName(String name) {
        if (name == null) return false;
        return NAME_PATTERN.matcher(name).matches();
    }
    public static boolean isValidFamilye(String family) {
        if (family == null) return false;
        return FAMILY_PATTERN.matcher(family).matches();
    }
    public static boolean isValidNationalId(String nationalId) {
        if (nationalId == null) return false;
        return NATIONALID_PATTERN.matcher(nationalId).matches();
    }

    public static boolean isValidUserName(String username) {
        if (username == null) return false;
        return USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}
