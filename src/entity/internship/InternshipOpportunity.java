package entity.internship;

import java.io.Serializable;
import java.time.LocalDate;
import entity.Displayable;
import entity.application.Application;
import util.io.AsciiTableFormatter;

import java.util.*;

/**
 * Each rep contains internshipOpportunity and this class help create the object
 */
public class InternshipOpportunity implements Serializable, Displayable {

	private final String id;
	private String companyName;
	private String title;
	private String description;
	private InternshipLevel level;
	private String preferredMajors;
	private LocalDate openingDate;
	private LocalDate closingDate;
	private int numOfSlots;
	private List<Application> pendingApplications;
	private InternStatus status;
	private Boolean visibility;
	private String createdBy;
	private List<Application> approvedslots;

    /**
     * Constructor of internships with status set to PENDING
     * @param id
     * @param companyName
     * @param title
     * @param description
     * @param level
     * @param preferredMajors
     * @param openingDate
     * @param closingDate
     * @param numOfSlots
     * @param status
     * @param createdBy
     */
	public InternshipOpportunity(String id,String companyName, String title, String description, InternshipLevel level,
								 String preferredMajors, LocalDate openingDate, LocalDate closingDate, int numOfSlots, InternStatus status,
								 String createdBy){
		this.id = id;
		this.companyName = companyName;
		this.title = title;
		this.description = description;
		this.level = level;
		this.preferredMajors = preferredMajors;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.numOfSlots = numOfSlots;
		this.pendingApplications = new ArrayList<>();
		this.status = status;
		this.visibility = false;
		this.createdBy = createdBy;
		this.approvedslots = new ArrayList<>();
	}

    /**
     *
     * @return ID of internship
     */
	public String getId(){
		return this.id;
	}

    /**
     *
     * @return company name
     */
	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * 
	 * @param companyName  set internship's company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

    /**
     *
     * @return title of internship
     */
	public String getTitle() {
		return this.title;
	}

	/**
	 * 
	 * @param title set internship's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

    /**
     *
     * @return description of internship
     */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 
	 * @param description internship's description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

    /**
     *
     * @return internship level
     */
	public InternshipLevel getLevel() {
		return this.level;
	}

	/**
	 * 
	 * @param level internship's level
	 */
	public void setLevel(InternshipLevel level) {
		this.level = level;
	}

    /**
     *
     * @return preferred major of internship
     */
	public String getPreferredMajors() {
		return this.preferredMajors;
	}

	/**
	 * 
	 * @param preferredMajors internship's preferred majors
	 */
	public void setPreferredMajors(String preferredMajors) {
		this.preferredMajors = preferredMajors;
	}

    /**
     *
     * @return opening date
     */
	public LocalDate getOpeningDate() {
		return this.openingDate;
	}

	/**
	 * 
	 * @param openingDate internship's opening date
	 */
	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}

    /**
     *
     * @return closing date of internship
     */
	public LocalDate getClosingDate() {
		return this.closingDate;
	}

	/**
	 * 
	 * @param closingDate internship's opening date
	 */
	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

    /**
     *
     * @return number of approved application slots
     */
	public int getNumOfSlots(){
		return this.numOfSlots;
	}

	/**
	 *
	 * @param numOfSlots number of available slots
	 */
	public void setNumOfSlots(int numOfSlots){
		this.numOfSlots = numOfSlots;
	}

    /**
     *
     * @return list of pending application
     */
	public List<Application> getPendingApplications() {
		return new ArrayList<>(this.pendingApplications);
	}

    /**
     * add application into pending list
     * @param slot
     */
    public void addPendingApplication(Application slot){
        this.pendingApplications.add(slot);
    }

    /**
     * remove application from pending list
     * @param slot
     */
    public void removePendingApplication(Application slot){
        this.pendingApplications.remove(slot);
    }

    /**
     *
     * @return status of internship
     */
	public InternStatus getStatus() {
		return this.status;
	}

	/**
	 *
	 * @param status set internship's status
	 */
	public void setStatus(InternStatus status) {
		this.status = status;
	}

    /**
     *
     * @return visibility of internship
     */
	public Boolean getVisibility() {
		return this.visibility;
	}

	/**
	 * 
	 * @param visibility set internship's visibility
	 */
	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

    /**
     *
     * @return rep' ID
     */
	public String getCreatedBy() {
		return this.createdBy;
	}

	/**
	 *
	 * @param creator the company rep that created the internship
	 */
	public void setCreatedBy(String creator){
		this.createdBy = creator;
	}

    /**
     *
     * @return approvedSlots
     */
	public List<Application>  getApprovedSlots(){
		return this.approvedslots;
	}

    /**
     * move application from pending to approved list
     * @param application
     */
    public void addApprovedapplication(Application application){
        this.pendingApplications.remove(application);
        this.approvedslots.add(application);
    }

    /**
     * remove app from approved list
     * @param application
     */
    public void removeApprovedapplication(Application application){
        this.approvedslots.remove(application);
    }

    /**
     *
     * @return number of filled up slots
     */
    public int getNumOfFilledSlots(){
        return approvedslots.size();
    }

    /**
     *
     * @return String for displaying
     */
	@Override
	public String toString() {
		int approved = (approvedslots == null) ? 0 : approvedslots.size();
		int pending = (pendingApplications == null) ? 0 : pendingApplications.size();
		int open = Math.max(0, numOfSlots - approved);

		String slotsSummary = String.format(
				"%d total | %d approved | %d pending | %d open",
				numOfSlots, approved, pending, open
		);

		String placeholder = "#".repeat(String.valueOf(status).length());

		List<AsciiTableFormatter.Row> rows = List.of(
				new AsciiTableFormatter.Row("Title", title),
				new AsciiTableFormatter.Row("Company", companyName),
				new AsciiTableFormatter.Row("Level", String.valueOf(level)),
				new AsciiTableFormatter.Row("Preferred Majors", preferredMajors),
				new AsciiTableFormatter.Row("Description", description),
				new AsciiTableFormatter.Row("Opening Date", String.valueOf(openingDate)),
				new AsciiTableFormatter.Row("Closing Date", String.valueOf(closingDate)),
				new AsciiTableFormatter.Row("Status", placeholder),
				new AsciiTableFormatter.Row("Visibility", (visibility != null && visibility) ? "Public" : "Hidden"),
				new AsciiTableFormatter.Row("Slots", slotsSummary),
				new AsciiTableFormatter.Row("Created By", createdBy)

		);

		String table = AsciiTableFormatter.formatTable(rows);
		return table.replace(placeholder, status.coloredString());
	}

}