package entity.user;

public class CareerStaff extends User {

	private String staffDepartment;


    /**
     *
     * @param name
     * @param id
     * @param pass
     * @param department
     */
    public CareerStaff(String name, String id, String pass, String department) {
        super(name, id, pass);
        this.staffDepartment = department;
    }

	public String getStaffDepartment() {
		return this.staffDepartment;
	}

	/**
	 * 
	 * @param staffDepartment
	 */
	public void setStaffDepartment(String staffDepartment) {
		this.staffDepartment = staffDepartment;
	}




}