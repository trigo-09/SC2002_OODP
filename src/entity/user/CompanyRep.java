package entity.user;
import entity.internship.InternshipOpportunity;
import java.util.*;

public class CompanyRep extends User {

	private String department;
	private String companyName;
	private String position;
	private RepStatus status;
	private List<InternshipOpportunity> internships;

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
        super(name, id, pass);
        // TODO - implement CompanyRep.CompanyRep
        this.companyName = company;
        this.department = department;
        this.status = status;
    }

	public String getDepartment() {
		// TODO - implement CompanyRep.getDepartment
		return this.department;
	}

	/**
	 * 
	 * @param department
	 */
	public void setDepartment(String department) {

		this.department = department;
	}

	public String getCompanyName() {

		return companyName;
	}

	/**
	 * 
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {

		this.companyName = companyName;
	}

	public String getPosition() {

		return position;
	}

	/**
	 * 
	 * @param position
	 */
	public void setPosition(String position) {

		this.position = position;
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
