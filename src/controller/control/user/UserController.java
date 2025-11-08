package controller.control.user;

import controller.service.AuthenticationService;
import entity.user.User;

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
	public void changePassword(String oldPass, String newPass, User user, String confirmPas) {
        // code is incomplete
        if (oldPass.equals(newPass)) {
            System.out.println("error: new password cannot be the same"); //suppose to throw error ig but build it more
        }
        else {
            auth.changePassword(user.getId(), newPass);
        }
	}

}