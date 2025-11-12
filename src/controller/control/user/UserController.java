package controller.control.user;

import controller.database.IRepository;
import controller.service.AuthenticationService;
import controller.service.RequestService;
import entity.user.User;
import util.FilterCriteria;
import util.exceptions.AuthenticationException;

public class UserController {

	protected AuthenticationService auth;
    protected FilterCriteria filter;
    protected IRepository repo;
    protected RequestService request;



    public UserController(AuthenticationService auth, IRepository repo, RequestService request) {
        filter = new FilterCriteria();
        this.auth = auth;
        this.repo = repo;
        this.request = request;
    }

	/**
	 * 
	 * @param oldPass
	 * @param newPass
     * @param user
     * @param confirmPas
     * @throws Exception        throws exception when the confirmed pass do not match or the same password used again
     * @throws AuthenticationException      throws exception when there is arror in auth.changePassword
	 */
	public void changePassword(String oldPass, String newPass, User user, String confirmPas) throws Exception ,AuthenticationException {
        if (!newPass.equals(confirmPas)) {
            throw new IllegalArgumentException("New Password and confirmation do not match");
        }
        if (oldPass.equals(newPass)) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }
        auth.changePassword(user.getId(),oldPass,newPass);
        System.out.println("Password has been changed successfully");
	}

    public FilterCriteria getFilter() {return filter;}
    public AuthenticationService getAuth() {return auth;}
    public IRepository getRepo() {return repo;}
    public RequestService getRequest() {return request;}

}