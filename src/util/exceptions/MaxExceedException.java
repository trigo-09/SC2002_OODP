package util.exceptions;

/**
 * Represents an exception that is thrown when there been a exceed in limit like number of application or internship.
 */
public class MaxExceedException extends Exception {
    public MaxExceedException(String message) {
        super(message);
    }
}
