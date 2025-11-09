package controller.control.user;

import java.util.*;

import controller.service.ApplicationService;
import controller.service.InternshipService;
import controller.service.AuthenticationService;
import entity.application.Application;
import entity.user.Student;
import entity.internship.InternshipOpportunity;
import util.FilterCriteria;

public class StudentController extends UserController {

	private Student student;
	private FilterCriteria filter;
    private final ApplicationService applicationService;
    private final InternshipService internshipService;

    public StudentController(FilterCriteria filter, AuthenticationService auth, ApplicationService applicationService, InternshipService internshipService, Student student) {
        super(auth);
        this.filter = filter;
        this.student = student;
        this.applicationService = applicationService;
        this.internshipService = internshipService;
    }

	public List<InternshipOpportunity> ableToApply() {
        return internshipService.getEligibleInternships(student);
	}

	/**
	 * 
	 * @param internshipId
	 */
	public void applyInternship(String internshipId) {
        applicationService.apply(student.getId(), internshipId);
	}

	/**
	 * 
	 * @param applicationId
	 */
	public void acceptPlacement(String applicationId) {
        applicationService.acceptApplication(applicationId);
	}

    public void withdrawPlacement(String applicationId, String reason) {
        applicationService.requestWithdrawal(applicationId, reason);
    }

	public List<Application> myApplications() {
        return student.getApplications();
	}

}