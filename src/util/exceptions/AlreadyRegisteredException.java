package util.exceptions;

public class AlreadyRegisteredException extends Exception {
    /**
     * Constructs a new {@code AlreadyRegisteredException} with a default detail message.
     * The default message is "This user ID is already registered."
     */
    public AlreadyRegisteredException() {
        super("This user ID is already registered.");
    }

    /**
     *Constructs a new {@code AlreadyRegisteredException} with a message param
     * @param message
     */

    public AlreadyRegisteredException(String message) {
        super(message);
    }
}
