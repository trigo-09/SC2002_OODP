package entity.internship;
import entity.application.Application;

import java.io.Serializable;
import java.time.LocalDate;
import entity.user.CompanyRep;

import java.util.*;

public class InternshipOpportunity implements Serializable {

	Collection<Application> submission;
	private String companyName;
	private String title;
	private String description;
	private InternshipLevel level;
	private String preferredMajors;
	private LocalDate openingDate;
	private LocalDate closingDate;
	private List<Application> appslots;
	private InternStatus status;
	private Boolean visibility;
	private CompanyRep createdBy;
	private String id;
	private List<Application> approvedslot;

	/**
	 * 
	 * @param state
	 */
	public void setStatus(InternStatus state) {
		this.status = state;
	}

	public void isVisibile() {
		// TODO - implement entity.internship.InternshipOpportunity.isVisibile
		throw new UnsupportedOperationException();
	}

	public void getCompanyName() {
		// TODO - implement entity.internship.InternshipOpportunity.getCompanyName
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param companyName
	 */
	public void setCompanyName(int companyName) {
		// TODO - implement entity.internship.InternshipOpportunity.setCompanyName
		throw new UnsupportedOperationException();
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

	public List<Application> getSlots() {
		// TODO - implement entity.internship.InternshipOpportunity.getSlots
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param slots
	 */
	public void setSlots(List<Application> slots) {
		// TODO - implement entity.internship.InternshipOpportunity.setSlots
		throw new UnsupportedOperationException();
	}

	public InternStatus getStatus() {
		return this.status;
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

}