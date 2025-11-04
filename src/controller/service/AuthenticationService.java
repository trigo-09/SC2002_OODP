package controller.service;

import entity.user.User;
import util.PasswordHasher;

public class AuthenticationService {

	private final PasswordHasher hasher;

	/**
	 * 
	 * @param hasher
	 */
	public AuthenticationService(PasswordHasher hasher) {
		// TODO - implement AuthenticationService.AuthenticationService
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param ID
	 * @param password
	 */
	public void authenticate(String ID, String password) {
		// TODO - implement AuthenticationService.authenticate
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param user
	 * @param newpassword
	 */
	public void changePassword(User user, String newpassword) {
		// TODO - implement AuthenticationService.changePassword
		throw new UnsupportedOperationException();
	}

}