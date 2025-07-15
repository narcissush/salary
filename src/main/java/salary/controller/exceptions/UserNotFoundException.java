package salary.controller.exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("Access Denied !!! User Not Found !!!");
    }
}
