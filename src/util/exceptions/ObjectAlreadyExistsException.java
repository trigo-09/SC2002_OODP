package util.exceptions;

public class ObjectAlreadyExistsException extends Exception {
    public ObjectAlreadyExistsException(){super("Model already exists");}
    public ObjectAlreadyExistsException(String message) {super(message);}
}
