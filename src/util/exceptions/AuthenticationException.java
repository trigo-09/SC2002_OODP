package util.exceptions;

/**
 * An abstract exception that is the base class for all exceptions
 * related to authentication.
 */
public abstract class AuthenticationException extends Exception {
    /**
     * Base class for all authentication-related exceptions.
     * Allows SystemController to handle login failures uniformly.
     */
    public AuthenticationException(String message) {
        super(message);
    }
}
