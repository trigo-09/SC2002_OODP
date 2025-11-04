package controller.control.user;

import java.util.*;
import entity.internship.InternshipOpportunity;
import entity.user.CompanyRep;
import entity.application.Application;
import util.FilterCriteria;

public class RepController extends UserController {

	private CompanyRep rep;
	private FilterCriteria filter;

	public InternshipOpportunity createInternship() {
		// TODO - implement RepController.createInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param internshipid
	 */
	public void toggleVisibility(String internshipid) {
		// TODO - implement RepController.toggleVisibility
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param internshipId
	 */
	public List<Application> getApplication(String internshipId) {
		// TODO - implement RepController.getApplication
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param appId
	 * @param internshipid
	 */
	public void approveApp(String appId, String internshipid) {
		// TODO - implement RepController.approveApp
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param appid
	 * @param internshipId
	 */
	public void rejectApp(String appid, String internshipId) {
		// TODO - implement RepController.rejectApp
		throw new UnsupportedOperationException();
	}

}