package util.exceptions;

/**
 * Represents an exception that is thrown when someone tries to registers but the account exists and approved
 */
public class AlreadyApprovedException extends AuthenticationException {
    /**
     * Constructs a new {@code AlreadyRegisteredException} with a default detail message.
     * The default message is "This user ID is already registered."
     */
    public AlreadyApprovedException() {
        super("This user ID is already registered.");
    }

    /**
     *Constructs a new {@code AlreadyRegisteredException} with a message param
     * @param message custom message to be passed
     */

    public AlreadyApprovedException(String message) {
        super(message);
    }
}
