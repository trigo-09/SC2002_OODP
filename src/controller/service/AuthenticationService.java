package controller.service;

import controller.database.IRepository;
import entity.user.User;
import util.PasswordHasher;
import util.exceptions.AuthenticationException;
import util.exceptions.PasswordIncorrectException;
import util.exceptions.RepNotApprovedException;
import util.exceptions.UserNotFoundException;

public class AuthenticationService {

    private final IRepository resposistory;

	/**
     *
     * @param resposistory
     */
	public AuthenticationService(IRepository resposistory) {
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
     * @param newPassword
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