package controller.control.user;

import boundary.usermenu.StudentUI;
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
import util.exceptions.*;

/**
 * controller class for student
 * act as layer between user and services
 * allow student to view internship, apply and withdraw from internship
 */
public class StudentController extends UserController {

	private final Student student;
    private final ApplicationService applicationService;
    private final InternshipService internshipService;

    /**
     * constructor for student controller
     * @param auth authentication service
     * @param repo repository service
     * @param request request service
     * @param internshipService internship service
     * @param applicationService application service
     * @param student student
     */
    public StudentController(AuthenticationService auth, IRepository repo, RequestService request, InternshipService internshipService, ApplicationService applicationService ,Student student) {
        super(auth, repo, request);
        this.student = student;
        this.internshipService = internshipService;
        this.applicationService = applicationService;
    }

    /**
     * create student UI and launch the menu of student UI
     * @param systemController systemController
     */
    public void launch(SystemController systemController) {
        StudentUI studentUI = new StudentUI(systemController, this);
        studentUI.menu();
    }

    /**
     * view filtered internships for student
     * @param filter filter
     * @return list of internships
     */
	public List<InternshipOpportunity> viewFilteredInternships(FilterCriteria filter) {
        List <InternshipOpportunity> eligibleInternships = internshipService.getEligibleInternships(student);
        return internshipService.getFilteredInternship(eligibleInternships, filter);
	}

	/**
	 * apply for internship
	 * @param internshipId internship id
     * @throws ObjectNotFoundException if internship not found
     * @throws IllegalStateException if the student not eligible for internship
     * @throws IllegalStateException if there are no slot left in internship
	 */
	public void applyInternship(String internshipId)  throws ObjectNotFoundException, MaxExceedException, UserNotFoundException {
        if (internshipService.findInternshipById(internshipId) == null) {
            throw new ObjectNotFoundException("Invalid internship ID: " + internshipId);
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
	 *accept approved application for internship
	 * @param applicationId application ID
     * @throws ObjectNotFoundException if application cannot be found
	 */
	public void acceptPlacement(String applicationId) throws ObjectNotFoundException{
        applicationService.acceptApplication(student.getId(),applicationId);

	}

    /**
     * request withdrawal of application
     * @param appId application ID
     * @param reason reason for withdrawal
     * @throws SecurityException if illegal access of other's application
     * @throws ObjectNotFoundException if application cannot be found
     * @throws ObjectAlreadyExistsException if there is already a withdrawal request
     */
    public void withdrawPlacement(String appId, String reason) throws IllegalStateException,SecurityException, ObjectNotFoundException, ObjectAlreadyExistsException{
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
            requestService.createWithdrawalRequest(student.getId(), application, reason);
        } else {
            throw new IllegalStateException("Application is already " + application.getStatus() + " and cannot be withdrawn.");
        }
    }

    /**
     *
     * @return list of student's application
     */
	public List<Application> myApplications() {
        return student.getApplications();
	}

    /**
     *
     * @return return student
     */
    public Student getStudent(){
        return student;
    }
}