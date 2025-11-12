package entity.user;

public class CareerStaff extends User {

	private String staffDepartment;


    /**
     * constructor of Career staff
     * @param name
     * @param id
     * @param pass
     * @param department
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
	 * @param staffDepartment
	 */
	public void setStaffDepartment(String staffDepartment) {
		this.staffDepartment = staffDepartment;
	}




}