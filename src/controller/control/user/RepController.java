package controller.control.user;

import controller.database.IRepository;
import controller.service.ApplicationService;
import controller.service.AuthenticationService;
import controller.service.InternshipService;
import controller.service.RequestService;
import entity.application.Application;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import entity.user.CompanyRep;
import java.time.LocalDate;
import java.util.*;
import util.FilterCriteria;

public class RepController extends UserController {

	private final CompanyRep rep;
	private final ApplicationService applicationService;
	private final InternshipService internshipService;

	public RepController(AuthenticationService auth,
                         IRepository repository, RequestService requestService,CompanyRep rep){
		super(auth,repository,requestService);
		this.rep = rep;
		this.internshipService = new InternshipService(repository, requestService);
        this.applicationService = new ApplicationService(repository,internshipService,requestService);
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