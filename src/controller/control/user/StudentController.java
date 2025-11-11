package controller.control.user;

import java.util.*;

import boundary.StudentUI;
import controller.control.SystemController;
import controller.database.IRepository;
import controller.service.ApplicationService;
import controller.service.InternshipService;
import controller.service.AuthenticationService;
import controller.service.RequestService;
import entity.application.Application;
import entity.user.Student;
import entity.internship.InternshipOpportunity;
import util.FilterCriteria;

public class StudentController extends UserController {

	private Student student;
    private ApplicationService applicationService;
    private InternshipService internshipService;

    public StudentController(AuthenticationService auth, IRepository respo, RequestService request, Student student) {
        super(auth, respo, request);
        this.student = student;
        InternshipService internshipService = new InternshipService(respo, request);
        ApplicationService applicationService = new ApplicationService(respo, internshipService, request);
    }

    public void launch(SystemController systemController) {
        StudentUI studentUI = new StudentUI(systemController, this);
        studentUI.menu();
    }

	public List<InternshipOpportunity> viewFilteredInternships(FilterCriteria filter) {
        List <InternshipOpportunity> eligibleInternships = internshipService.getEligibleInternships(student);
        return internshipService.getFilteredInternship(eligibleInternships, filter);
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

    public Student getStudent(){
        return student;
    }
}