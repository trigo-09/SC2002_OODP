package entity.user;

import java.io.Serializable;


public abstract class User implements Serializable {

    private String name;
    private final String id;
    private String hashedPassword;
    private final UserRole role;

    /**
     * Contructor of User abstract class
     * @param name
     * @param id
     * @param pass
     */
    public User(String name, String id, String pass, UserRole role) {
        this.name = name;
        this.id = id;
        this.hashedPassword = pass;
        this.role = role;
    }

    /**
     *
     * @return hashed password
     */
    public String getHashedPassword() {
        return this.hashedPassword;
    }

    /**
     *
     * @return type of user
     */
    public UserRole getRole() {
        return this.role;
    }

    /**
     * set new hashedpassword
     * @param pass
     */
    public void setHashedPassword(String pass) {
        this.hashedPassword = pass;
    }

    /**
     *
     * @return user's name
     */
    public String getUserName() {
        return this.name;
    }

    /**
     *
     * @return user's ID
     */
    public String getId() {
        return this.id;
    }

}
