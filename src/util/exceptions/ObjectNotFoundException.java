package util.exceptions;

public class ObjectNotFoundException extends Exception {
      /**
     * Creates a new instance of the {@link ObjectNotFoundException} class with a default error message.
     * The default message is "Model not found".
     */
    public ObjectNotFoundException() {
        super("Model not found");
    }

    /**
     * Creates a new instance of the {@link ObjectNotFoundException} class with a custom error message.
     *
     * @param message The custom error message to be used.
     */
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
