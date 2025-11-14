package util.exceptions;


public class RepPendingApprovalException extends AuthenticationException {
    public RepPendingApprovalException(String repId) {
        super("account " + repId + " has not been approved yet");
    }
    public RepPendingApprovalException(String repId, String message) {
        super(repId + message);
    }
}
