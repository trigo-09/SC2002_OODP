package entity.user;

import entity.internship.InternshipOpportunity;

public class CompanyRep extends User {

	private String department;
	private String companyName;
	private String position;
	private RepStatus status;
	private List<InternshipOpportunity> internships;

	public void getDepartment() {
		// TODO - implement CompanyRep.getDepartment
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param department
	 */
	public void setDepartment(int department) {
		// TODO - implement CompanyRep.setDepartment
		throw new UnsupportedOperationException();
	}

	public void getCompanyName() {
		// TODO - implement CompanyRep.getCompanyName
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param companyName
	 */
	public void setCompanyName(int companyName) {
		// TODO - implement CompanyRep.setCompanyName
		throw new UnsupportedOperationException();
	}

	public void getPosition() {
		// TODO - implement CompanyRep.getPosition
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param position
	 */
	public void setPosition(int position) {
		// TODO - implement CompanyRep.setPosition
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param name
	 * @param id
	 * @param pass
	 * @param company
	 * @param department
	 * @param position
	 * @param status
	 */
	public CompanyRep(String name, String id, String pass, String company, String department, String position, RepStatus status) {
		// TODO - implement CompanyRep.CompanyRep
		throw new UnsupportedOperationException();
	}

	public RepStatus getStatus() {
		return this.status;
	}

	/**
	 * 
	 * @param status
	 */
	public void setStatus(RepStatus status) {
		this.status = status;
	}

}