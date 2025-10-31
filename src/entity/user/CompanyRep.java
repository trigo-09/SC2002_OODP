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
        this.department = department;
        this.companyName = company;
        this.position = position;
        this.status = status;
//        throw new UnsupportedOperationException();
    }

	public String getDepartment() {
        return department;
//		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param department
	 */
	public void setDepartment(String department) {
        this.department = department;
//		throw new UnsupportedOperationException();
	}

	public String getCompanyName() {
        return companyName;
//		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
        this.companyName = companyName;
//		throw new UnsupportedOperationException();
	}

	public String getPosition() {
        return position;
//		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param position
	 */
	public void setPosition(String position) {
        this.position = position;
//		throw new UnsupportedOperationException();
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