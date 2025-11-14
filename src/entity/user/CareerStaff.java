package entity.user;

/**
 * Represents a career staff
 * extends the User class
 */
public class CareerStaff extends User {

	private String staffDepartment;


    /**
     * constructor of Career staff
     * @param name name of staff
     * @param id id of staff
     * @param pass password of staff
     * @param department department of staff
     */
    public CareerStaff(String name, String id, String pass, String department) {
        super(name, id, pass, UserRole.STAFF);
        this.staffDepartment = department;
    }

    /**
     *
     * @return staff's department
     */
	public String getStaffDepartment() {
		return this.staffDepartment;
	}

	/**
	 * set new department
	 * @param staffDepartment new department name
	 */
	public void setStaffDepartment(String staffDepartment) {
		this.staffDepartment = staffDepartment;
	}




}