package controller.database;

import entity.user.User;

public interface IUser {
    /**
     * find user using their ID and return them
     * @param userId user ID
     */
    User findUser(String userId);
}
