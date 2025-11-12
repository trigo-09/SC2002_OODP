package entity.user;
import entity.internship.InternStatus;
import entity.internship.InternshipOpportunity;
import java.util.*;

public class CompanyRep extends User {

	private String department;
	private final String companyName;
	private String position;
	private RepStatus status;
	private final List<InternshipOpportunity> internships;

    /**
     * constructor of Companyrep
     * @param name
     * @param id
     * @param pass
     * @param company
     * @param department
     * @param position
     */
    public CompanyRep(String name, String id, String pass, String company, String department, String position) {
        super(name, id, pass, UserRole.REP);
        this.department = department;
        this.companyName = company;
        this.position = position;
        internships = new ArrayList<>();
        this.status = RepStatus.PENDING;
    }

    /**
     *
     * @return department of rep
     */
	public String getDepartment() {
        return department;
	}

    /**
     *
     * @return get company name
     */
	public String getCompanyName() {
        return companyName;
	}


    /**
     *
     * @return position of rep
     */
	public String getPosition() {
        return position;
	}

    /**
     *
     * @return account status
     */
	public RepStatus getStatus() {
        return this.status;
	}

	/**
	 * update status of account
	 * @param status
	 */
	public void setStatus(RepStatus status) {
        this.status = status;
	}

    /**
     * set department of rep
     * @param department
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * set position of rep
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     *
     * @return all the internship of rep
     */
    public List<InternshipOpportunity> getInternships() {
        return new ArrayList<>(internships);
    }

    /**
     * add new internship to list
     * @param internship
     */
    public void addInternship(InternshipOpportunity internship) {
        this.internships.add(internship);
    }

    /**
     * remove internship from list
     * @param internship
     */
    public void removeInternship(InternshipOpportunity internship) {
        this.internships.remove(internship);
    }

}