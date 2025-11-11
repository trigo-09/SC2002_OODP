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
    protected IRepository respo;
    protected RequestService request;



    public UserController(AuthenticationService auth, IRepository respo, RequestService request) {
        filter = new FilterCriteria();
        this.auth = auth;
        this.respo = respo;
        this.request = request;
    }

	/**
	 * 
	 * @param oldPass
	 * @param newPass
	 */
	public void changePassword(String oldPass, String newPass, User user, String confirmPas) {
        if (!newPass.equals(confirmPas)) {
            throw new IllegalArgumentException("New Password and confirmation do not match");
        }
        if (oldPass.equals(newPass)) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }
        try {
            auth.changePassword(user.getId(),oldPass,newPass);
            System.out.println("Password has been changed successfully");
        } catch (AuthenticationException e) {
           System.out.println(e.getMessage());
        }
	}

    public FilterCriteria getFilter() {return filter;}
    public AuthenticationService getAuth() {return auth;}
    public IRepository getRespo() {return respo;}
    public RequestService getRequest() {return request;}

}