package util.exceptions;

public class RepPendingApprovalException extends AuthenticationException {
    public RepPendingApprovalException(String repId) {
        super("account " + repId + " has been registered");
    }
}
