package entity.user;

public abstract class User {

	private String name;
	private String id;
	private String hashedPassword;

	public String getHashedPassword() {
		return this.hashedPassword;
	}

	public String getID() {
		// TODO - implement User.getID
		throw new UnsupportedOperationException();
	}

	public String getUserName() {
		// TODO - implement User.getUserName
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param pass
	 */
	protected void setHashedPassword(String pass) {
		this.hashedPassword = pass;
	}

	/**
	 * 
	 * @param name
	 * @param id
	 * @param pass
	 */
	protected User(String name, String id, String pass) {
		// TODO - implement User.User
		throw new UnsupportedOperationException();
	}

}