package util.exceptions;

/**
 * Represents an exception that is thrown when a company representative's account is pending for approval but they try
 * to login in
 */
public class RepPendingApprovalException extends AuthenticationException {
    public RepPendingApprovalException(String repId) {
        super("account " + repId + " has not been approved yet");
    }
    public RepPendingApprovalException(String repId, String message) {
        super(repId + message);
    }
}
