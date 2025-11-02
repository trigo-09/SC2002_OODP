package entity.user;

public abstract class User {

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
        this.hashedPassword = String.valueOf(pass.hashCode());
    }


    public String getHashedPassword() {
		return this.hashedPassword;
	}

    /**
     *
     * @param pass
     */
    protected void setHashedPassword(String pass) {
        this.hashedPassword = String.valueOf(pass.hashCode());
    }

	public String getID() {
		// TODO - implement User.getID
        return this.id;

	}

	public String getUserName() {
		// TODO - implement User.getUserName
		return this.name;
	}


}
