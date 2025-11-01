package controller.service;
import java.util.*;

import entity.internship.InternStatus;
import entity.internship.InternshipOpportunity;
import entity.user.Student;

public class InternshipService {

	/**
	 * 
	 * @param rep
	 * @param data
	 */
	public InternshipOpportunity createInternship(String rep, String data) {
		// TODO - implement InternshipService.createInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 * @param visible
	 */
	public void setVisibility(String id, boolean visible) {
		// TODO - implement InternshipService.setVisibility
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param id
	 * @param status
	 */
	public void updateStatus(String id, InternStatus status) {
		// TODO - implement InternshipService.updateStatus
		throw new UnsupportedOperationException();
	}

	public List<InternshipOpportunity> getAllInternship() {
		// TODO - implement InternshipService.getAllInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Id
	 */
	public void findByID(String Id) {
		// TODO - implement InternshipService.findByID
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param student
	 * @param intern
	 */
	public boolean isEligible(Student student, InternshipOpportunity intern) {
		// TODO - implement InternshipService.isEligible
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param student
	 * @param intern
	 */
	public boolean canApply(Student student, InternshipOpportunity intern) {
		// TODO - implement InternshipService.canApply
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param Intern
	 */
	public boolean isFilled(InternshipOpportunity Intern) {
		// TODO - implement InternshipService.isFilled
		throw new UnsupportedOperationException();
	}

}