package controller.control.user;

import boundary.StudentUI;
import controller.control.SystemController;
import controller.database.IRepository;
import controller.service.ApplicationService;
import controller.service.AuthenticationService;
import controller.service.InternshipService;
import controller.service.RequestService;
import entity.application.Application;
import entity.application.ApplicationStatus;
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
        internshipService = new InternshipService(repo);
        applicationService = new ApplicationService(repo);
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
        if (internshipService.findInternshipById(internshipId) == null) {
            throw new IllegalArgumentException("Invalid internship ID: " + internshipId);
        }
        if (!internshipService.isEligible(student, internshipId)) {
            throw new IllegalStateException("Student is not eligible to apply for this internship.");
        }
        if (internshipService.isFilled(internshipId)) {
            throw new IllegalStateException("Internship is already filled.");
        }
         Application app =applicationService.apply(student.getId(), internshipId);
         internshipService.addPendApplicationToInternship(app);
	}
	/**
	 * 
	 * @param applicationId
	 */
	public void acceptPlacement(String applicationId) {
        applicationService.acceptApplication(student.getId(),applicationId);

	}

    public void withdrawPlacement(String appId, String reason) throws IllegalArgumentException, SecurityException, ObjectNotFoundException {
        Application application = applicationService.findApplication(appId);
        // Ensure application exists
        if (application == null) {
            throw new ObjectNotFoundException("Invalid application ID: " + appId);
        }
        // Security check: ensure the application belongs to the student
        if (!application.getStudentId().equalsIgnoreCase(student.getId())) {
            throw new SecurityException("You can only withdraw your own applications.");
        }
        // Check if the application can be withdrawn
        if (applicationService.validStatusTransition(application.getStatus(), ApplicationStatus.WITHDRAWN)) {
            request.createWithdrawalRequest(student.getId(), application, reason);
        } else {
            throw new IllegalStateException("Application is already " + application.getStatus() + " and cannot be withdrawn.");
        }
    }


	public List<Application> myApplications() {
        return student.getApplications();
	}

    public Student getStudent(){
        return student;
    }
}