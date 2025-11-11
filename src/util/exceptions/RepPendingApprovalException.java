package util.exceptions;

public class RepPendingApprovalException extends AuthenticationException {
    public RepPendingApprovalException(String repId) {
        super("Company representative account " + repId + " has not been approved yet");
    }
}
