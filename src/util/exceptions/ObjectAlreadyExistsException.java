package util.exceptions;

/**
 * Represents an exception that is thrown when trying to create object that already exists
 * in the system, used to esnure no repeated request created
 */
public class ObjectAlreadyExistsException extends Exception {
    public ObjectAlreadyExistsException(){super("Model already exists");}
    public ObjectAlreadyExistsException(String message) {super(message);}
}
