package util.exceptions;

/**
 * Represents an exception that is thrown when trying to create object that already exists
 * in the system, used to esnure no repeated request created
 */
public class ObjectAlreadyExistsException extends Exception {
    /**
     * Constructs a new {@code ObjectAlreadyExistsException} with a default detail message.
     */
    public ObjectAlreadyExistsException(){super("Model already exists");}

    /**
     * Constructs a new {@code ObjectAlreadyExistsException} with a custom error message.
     * @param message custom message
     */
    public ObjectAlreadyExistsException(String message) {super(message);}
}
