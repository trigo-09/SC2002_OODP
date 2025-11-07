package controller.service;

import controller.database.IResposistory;
import entity.user.User;
import util.PasswordHasher;

public class AuthenticationService {

    private final IResposistory resposistory;

	/**
     *
     * @param resposistory
     */
	public AuthenticationService(IResposistory resposistory) {
        this.resposistory = resposistory;
	}

	/**
	 * 
	 * @param userId
	 * @param password
	 */
	public boolean authenticate(String userId, String password) {
        User user = resposistory.findUser(userId);
        return PasswordHasher.verify(password,user.getHashedPassword());
	}

	/**
     *
     * @param userId
     * @param newpassword
     */
	public void changePassword(String userId,String newpassword) {
		User user = resposistory.findUser(userId);
        String newHashedPassword = PasswordHasher.hash(newpassword);
        user.setHashedPassword(newHashedPassword);
	}

}