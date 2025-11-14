package util.exceptions;

/**
 * Represents an exception that is thrown when a company representative account is rejected and they try to login
 * The exception provides a message detailing the specific representative's ID.
 */
public class RepNotApprovedException extends AuthenticationException {
    public RepNotApprovedException(String repId) {
        super("Company representative account " + repId + " has been rejected");
    }

}
