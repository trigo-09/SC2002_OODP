package controller.control.user;

import controller.service.ApplicationService;
import controller.service.AuthenticationService;
import controller.service.InternshipService;
import entity.application.Application;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import entity.user.CompanyRep;
import java.time.LocalDate;
import java.util.*;
import util.FilterCriteria;

public class RepController extends UserController {

	private final CompanyRep rep;
	private final FilterCriteria filter;
	private final ApplicationService applicationService;
	private final InternshipService internshipService;

	public RepController(CompanyRep rep, 
						AuthenticationService auth, 
						FilterCriteria filter, 
						ApplicationService applicationService, 
						InternshipService internshipService) {
		super(auth);
		this.rep = rep;
		this.filter = filter;
		this.applicationService = applicationService;
		this.internshipService = internshipService;
	}

	public InternshipOpportunity createInternship(String title,
                                                  String description,
                                                  InternshipLevel level,
                                                  String preferredMajors,
                                                  LocalDate openingDate,
                                                  LocalDate closingDate,
                                                  int numOfSlots) {
        return internshipService.proposeInternship(title, description, level, preferredMajors, openingDate, closingDate, numOfSlots, rep.getId(), rep.getCompanyName());
    }
	

	/**
	 * 
	 * @param internshipid
	 */
	public void toggleVisibility(String internshipid, boolean visibility) {
		internshipService.setVisibility(internshipid, visibility);
	}

	/**
	 * 
	 * @param internshipId
	 */
	public List<Application> getApplications(String internshipId) {
		return internshipService.findInternshipById(internshipId).getPendingApplications();
	}

	/**
	 * 
	 * @param rep
	 */
	public List<InternshipOpportunity> getInternships() {
		return internshipService.getInternshipsByCompany(rep.getCompanyName());
	}


	/**
	 * 
	 * @param appId
	 */
	public void approveApp(String appId) {
		applicationService.reviewApplication(rep.getId(),appId, true);
	}

	/**
	 * 
	 * @param appid
	 */
	public void rejectApp(String appid) {
		applicationService.reviewApplication(rep.getId(), appid, false);
	}

}