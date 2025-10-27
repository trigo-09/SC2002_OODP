package entity.user;

public class CareerStaff extends User {

	private String staffDepartment;

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

	/**
	 * 
	 * @param name
	 * @param id
	 * @param pass
	 * @param department
	 */
	public CareerStaff(String name, String id, String pass, String department) {
		// TODO - implement CareerStaff.CareerStaff
		throw new UnsupportedOperationException();
	}

}