package controller.control.user;

import boundary.StudentUI;
import controller.control.SystemController;
import controller.database.IRepository;
import controller.service.ApplicationService;
import controller.service.AuthenticationService;
import controller.service.InternshipService;
import controller.service.RequestService;
import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.user.Student;
import java.util.*;
import util.FilterCriteria;
import util.exceptions.MaxExceedException;
import util.exceptions.ObjectNotFoundException;

public class StudentController extends UserController {

	private final Student student;
    private final ApplicationService applicationService;
    private final InternshipService internshipService;

    public StudentController(AuthenticationService auth, IRepository repo, RequestService request, Student student) {
        super(auth, repo, request);
        this.student = student;
        internshipService = new InternshipService(repo, request);
        applicationService = new ApplicationService(repo, internshipService, request);
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
	public void applyInternship(String internshipId)  throws IllegalArgumentException, SecurityException, MaxExceedException {
        applicationService.apply(student.getId(), internshipId);
	}
	/**
	 * 
	 * @param applicationId
	 */
	public void acceptPlacement(String applicationId) {
        applicationService.acceptApplication(student.getId(),applicationId);
	}

    public void withdrawPlacement(String applicationId, String reason) throws IllegalArgumentException, SecurityException, ObjectNotFoundException {
        applicationService.requestWithdrawal(student.getId(),applicationId, reason);
    }

	public List<Application> myApplications() {
        return student.getApplications();
	}

    public Student getStudent(){
        return student;
    }
}