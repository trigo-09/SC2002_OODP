package controller.control.user;

import controller.service.AuthenticationService;

public class UserController {

	private AuthenticationService auth;


    public UserController(AuthenticationService auth) {
        this.auth = auth;
    }

	/**
	 * 
	 * @param oldPass
	 * @param newPass
	 */
	public void changePassword(String oldPass, String newPass) {
		// TODO - implement UserController.changePassword
		throw new UnsupportedOperationException();
	}

}