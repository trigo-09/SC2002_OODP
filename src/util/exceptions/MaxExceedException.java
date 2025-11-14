package util.exceptions;

/**
 * Represents an exception that is thrown when there been a exceed in limit like number of application or internship.
 */
public class MaxExceedException extends Exception {
    /**
     * Constructs a new {@code MaxExceedException} with a default detail message.
     * @param message custom message
     */
    public MaxExceedException(String message) {
        super(message);
    }
}
