package controller.service;

import controller.database.IRepository;
import controller.database.IUser;
import entity.user.User;
import util.PasswordHasher;
import util.exceptions.AuthenticationException;
import util.exceptions.PasswordIncorrectException;
import util.exceptions.RepNotApprovedException;
import util.exceptions.UserNotFoundException;

/**
 * Service class for authentication
 * Handles changing password and validating login
 */
public class AuthenticationService {

    private final IRepository resposistory;

	/**
     * constructor of authentication Service
     * @param resposistory
     */
	public AuthenticationService(IRepository resposistory) {
        this.resposistory = resposistory;
	}

	/**
	 * validate login and return user object
	 * @param userId
	 * @param password
     * @throws AuthenticationException if user not found or user not approved or wrong password
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
     * changes password
     * @param userId
     * @param newPassword
     * @throws if wrong old password entered
     */
	public void changePassword(String userId,String oldPassword,String newPassword) throws AuthenticationException {
		User user = resposistory.findUser(userId);
        if(user.getHashedPassword().equals(PasswordHasher.hash(oldPassword))) {
            String newHashedPassword = PasswordHasher.hash(newPassword);
            user.setHashedPassword(newHashedPassword);
        } else  {
            throw new PasswordIncorrectException();
        }
	}

}