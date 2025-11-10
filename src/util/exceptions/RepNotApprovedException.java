package util.exceptions;

public class RepNotApprovedException extends AuthenticationException {
    public RepNotApprovedException(String repId) {
        super("Company representative account " + repId + " has been rejected");
    }

}
