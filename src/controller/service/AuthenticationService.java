package controller.service;

import controller.database.IResposistory;
import entity.user.User;
import util.PasswordHasher;
import util.exceptions.AuthenticationException;
import util.exceptions.PasswordIncorrectException;
import util.exceptions.RepNotApprovedException;
import util.exceptions.UserNotFoundException;

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
	public User authenticate(String userId, String password) throws AuthenticationException {
        User user = resposistory.findUser(userId);

        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        if (resposistory.getPendingReps().containsKey(userId)) {
            throw new RepNotApprovedException(userId);
        }

        if (!PasswordHasher.verify(password,user.getHashedPassword())){
            throw new PasswordIncorrectException();
        }

        return user;
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