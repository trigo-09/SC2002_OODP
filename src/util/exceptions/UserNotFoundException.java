package util.exceptions;

public class UserNotFoundException extends AuthenticationException{
    /**
     * Constructs a new {@code UserNotFoundException} with a default detail message.
     * The default message is "No user with this ID."
     */
    public UserNotFoundException(String userId) {
        super("No user with this ID: "+userId);
    }
}
