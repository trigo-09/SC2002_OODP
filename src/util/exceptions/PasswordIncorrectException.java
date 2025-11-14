package util.exceptions;

/**
 * Represents an exception that is thrown when the provided password is not valid.
 */
public class PasswordIncorrectException extends AuthenticationException {
    /**
     * Constructs a new PasswordIncorrectException object with a default message.
     * The default message is "Password is incorrect".
     */
    public PasswordIncorrectException() {
        super("Password is incorrect");
    }
}
