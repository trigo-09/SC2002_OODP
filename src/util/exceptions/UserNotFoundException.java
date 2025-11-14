package util.exceptions;

/**
 * Represents an exception that is thrown when a specific user cannot be found.
 * Used in Login page and other user finding instance.
 */
public class UserNotFoundException extends AuthenticationException{
    /**
     * Constructs a new {@code UserNotFoundException} with a default detail message.
     * The default message is "No user with this ID."
     * @param userId user ID to be searched for
     */
    public UserNotFoundException(String userId) {
        super("No user with this ID: "+userId);
    }
}
