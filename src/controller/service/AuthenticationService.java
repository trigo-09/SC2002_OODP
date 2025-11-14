package controller.service;

import controller.database.IRepository;
import entity.user.CompanyRep;
import entity.user.RepStatus;
import entity.user.User;
import util.PasswordHasher;
import util.exceptions.*;

/**
 * Service class for authentication
 * Handles changing password and validating login
 */
public class AuthenticationService {

    private final IRepository resposistory;

	/**
     * constructor of authentication Service
     * @param resposistory system repository
     */
	public AuthenticationService(IRepository resposistory) {
        this.resposistory = resposistory;
	}

	/**
	 * validate login and return user object
	 * @param userId user id
	 * @param password password
     * @throws AuthenticationException if user not found or user not approved or wrong password
	 */
	public User authenticate(String userId, String password) throws AuthenticationException {
        User user = resposistory.findUser(userId);

        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        if (resposistory.getPendingReps().containsKey(userId)) {
            CompanyRep rep = (CompanyRep) user;
            if (rep.getStatus() == RepStatus.REJECTED) {
                throw new RepNotApprovedException(userId);
            }else {throw new RepPendingApprovalException(userId);}
        }

        if (!PasswordHasher.verify(password,user.getHashedPassword())){
            throw new PasswordIncorrectException();
        }

        return user;
	}

	/**
     * changes password
     * @param userId user id
     * @param newPassword new password
     * @throws AuthenticationException if wrong old password entered
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