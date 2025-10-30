package entity.internship;
import entity.application.Application;

import java.io.Serializable;
import java.time.LocalDate;
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
	private List<Application> appslots;
	private InternStatus status;
	private Boolean visibility;
	private CompanyRep createdBy;
	private List<Application> approvedslots;

	public InternshipOpportunity(String id, String companyName, String title, String description, InternshipLevel level,
								 String preferredMajors, LocalDate openingDate, LocalDate closingDate, int numOfSlots,
								 InternStatus status, CompanyRep createdBy){
		this.id = id;
		this.companyName = companyName;
		this.title = title;
		this.description = description;
		this.level = level;
		this.preferredMajors = preferredMajors;
		this.openingDate = openingDate;
		this.closingDate = closingDate;
		this.numOfSlots = 0;
		this.appslots = new ArrayList<>();
		this.status = status;
		this.visibility = false;
		this.createdBy = createdBy;
		this.approvedslots = new ArrayList<>();
	}

	public String getID(){
		return this.id;
	}
	/**
	 *
	 * @param id
	 */
	public void setID(String id){
		this.id = id;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	/**
	 * 
	 * @param companyName
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTitle() {
		return this.title;
	}

	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	/**
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public InternshipLevel getLevel() {
		return this.level;
	}

	/**
	 * 
	 * @param level
	 */
	public void setLevel(InternshipLevel level) {
		this.level = level;
	}

	public String getPreferredMajors() {
		return this.preferredMajors;
	}

	/**
	 * 
	 * @param preferredMajors
	 */
	public void setPreferredMajors(String preferredMajors) {
		this.preferredMajors = preferredMajors;
	}

	public LocalDate getOpeningDate() {
		return this.openingDate;
	}

	/**
	 * 
	 * @param openingDate
	 */
	public void setOpeningDate(LocalDate openingDate) {
		this.openingDate = openingDate;
	}

	public LocalDate getClosingDate() {
		return this.closingDate;
	}

	/**
	 * 
	 * @param closingDate
	 */
	public void setClosingDate(LocalDate closingDate) {
		this.closingDate = closingDate;
	}

	public int getNumOfSlots(){
		return this.numOfSlots;
	}

	/**
	 *
	 * @param numOfSlots
	 */
	public void setNumOfSlots(int numOfSlots){
		this.numOfSlots = numOfSlots;
	}

	public List<Application> getSlots() {
		return this.appslots;
	}

	/**
	 * 
	 * @param slots
	 */
	public void setSlots(List<Application> slots) {
		this.appslots = slots;
	}


	public InternStatus getStatus() {
		return this.status;
	}

	/**
	 *
	 * @param state
	 */
	public void setStatus(InternStatus state) {
		this.status = state;
	}

	public Boolean getVisibility() {
		return this.visibility;
	}


	/**
	 * 
	 * @param visibility
	 */
	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}

	public CompanyRep getCreatedBy() {
		return this.createdBy;
	}
	/**
	 *
	 * @param creator
	 */
	public void setCreatedBy(CompanyRep creator){
		this.createdBy = creator;
	}

	public List<Application>  getApprovedSlots(){
		return this.approvedslots;
	}
	/**
	 *
	 * @param approvedslots
	 */
	public void setApprovedslots(List<Application> approvedslots){
		this.approvedslots=approvedslots;
	}




}