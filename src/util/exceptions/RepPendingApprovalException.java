package util.exceptions;

/**
 * Represents an exception that is thrown when a company representative's account is pending for approval but they try
 * to login in
 */
public class RepPendingApprovalException extends AuthenticationException {
    /**
     * Constructs a new {@code RepPendingApprovalException} with a default detail message.
     * @param repId company representative ID
     */
    public RepPendingApprovalException(String repId) {
        super("account " + repId + " has not been approved yet");
    }

    /**
     * Constructs a new {@code RepPendingApprovalException} with a custom detail message.
     * @param repId company representative ID
     * @param message custom message
     */
    public RepPendingApprovalException(String repId, String message) {
        super(repId + message);
    }
}
