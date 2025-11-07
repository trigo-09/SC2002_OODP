package entity.internship;

import java.io.Serializable;
import java.time.LocalDate;

import entity.application.Application;
import entity.user.CompanyRep;

import java.util.*;

public class InternshipOpportunity implements Serializable {

	private String id;
	private String companyName;
	private String title;
	private String description;
	private InternshipLevel level;
	private String preferredMajors;
	private LocalDate openingDate;
	private LocalDate closingDate;
	private int numOfSlots;
	private List<Application> pendingApplications; //think of using string id reference instead
	private InternStatus status;
	private Boolean visibility;
	private String createdBy;
	private List<Application> approvedslots;

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

	public String getId(){
		return this.id;
	}
	/**
	 *
	 * @param id unique internship id
	 */
	public void setId(String id){
		this.id = id;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * 
	 * @param companyName internship's company name
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTitle() {
		return this.title;
	}

	/**
	 * 
	 * @param title internship's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

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

	public List<Application> getPendingApplications() {
		return this.pendingApplications;
	}

	/**
	 * 
	 * @param slots internship applicationss
	 */
	public void setPendingApplications(List<Application> slots) {
		this.pendingApplications = slots;
	}

    public void addPendingApplication(Application slot){
        this.pendingApplications.add(slot);
    }


	public InternStatus getStatus() {
		return this.status;
	}

	/**
	 *
	 * @param status internship's status
	 */
	public void setStatus(InternStatus status) {
		this.status = status;
	}

	public Boolean getVisibility() {
		return this.visibility;
	}


	/**
	 * 
	 * @param visibility internship's visibility
	 */
	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}
	/**
	 *
	 * @param creator the comanyrep that created the internship
	 */
	public void setCreatedBy(String creator){
		this.createdBy = creator;
	}

	public List<Application>  getApprovedSlots(){
		return this.approvedslots;
	}
	/**
	 *
	 * @param approvedslots approved pendingApplications
	 */
	public void setApprovedslots(List<Application> approvedslots){
		this.approvedslots=approvedslots;
	}
    public void addApprovedapplication(Application application){
        this.approvedslots.add(application);
    }




}