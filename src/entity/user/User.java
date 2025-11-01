package entity.user;

import java.io.Serializable;

public abstract class User implements Serializable {

	private String name;
	private String id;
	private String hashedPassword;

    /**
     *
     * @param name
     * @param id
     * @param pass
     */
    protected User(String name, String id, String pass) {
        // TODO - implement User.User
        this.name = name;
        this.id = id;
        this.hashedPassword = pass;

        throw new UnsupportedOperationException();
    }


    public String getHashedPassword() {
		return this.hashedPassword;
	}

    /**
     *
     * @param pass
     */
    protected void setHashedPassword(String pass) {
        this.hashedPassword = pass;
    }

	public String getId() {
		// TODO - implement User.getID
        return this.id;
		//throw new UnsupportedOperationException();
	}

	public String getUserName() {
		// TODO - implement User.getUserName
        return this.name;
		//throw new UnsupportedOperationException();
	}


}