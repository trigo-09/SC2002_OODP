package controller.control.user;

import controller.database.IRepository;
import controller.service.AuthenticationService;
import controller.service.RequestService;
import entity.user.User;
import entity.FilterCriteria;
import util.exceptions.AuthenticationException;

/**
 * Base abstract class for user controls
 * implements method for change of password
 */
public abstract class UserController {

	protected AuthenticationService auth;
    protected FilterCriteria filter;
    protected IRepository repo;
    protected RequestService requestService;

    /**
     * Constructor for abstract class user controller
     * @param auth authentication service
     * @param repo system repository
     * @param requestService request service
     */
    public UserController(AuthenticationService auth, IRepository repo, RequestService requestService) {
        filter = new FilterCriteria();
        this.auth = auth;
        this.repo = repo;
        this.requestService = requestService;
    }

	/**
     *
	 * call authentication service to change password
	 * @param oldPass old password
	 * @param newPass new password
     * @param user user object
     * @param confirmPas confirm password
     * @throws IllegalArgumentException the confirmed pass do not match or the same password used again
     * @throws AuthenticationException if there is error in auth.changePassword
	 */
	public void changePassword(String oldPass, String newPass, User user, String confirmPas) throws AuthenticationException, IllegalArgumentException {
        if (!newPass.equals(confirmPas)) {
            throw new IllegalArgumentException("New Password and confirmation do not match");
        }
        if (oldPass.equals(newPass)) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }
        auth.changePassword(user.getId(),oldPass,newPass);
        System.out.println("Password has been changed successfully. Please login again.");
	}

    /**
     *
     * @return filter
     */
    public FilterCriteria getFilter() {return filter;}

    /**
     *
     * @return authentication service
     */
    public AuthenticationService getAuth() {return auth;}

    /**
     *
     * @return repository
     */
    public IRepository getRepo() {return repo;}

    /**
     *
     * @return request service
     */
    public RequestService getRequestService() {return requestService;}

}