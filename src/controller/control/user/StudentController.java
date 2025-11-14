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
import entity.internship.InternStatus;
import entity.internship.InternshipOpportunity;
import entity.user.Student;
import java.util.*;
import entity.FilterCriteria;
import entity.user.User;
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
     * Constructs student controller bound to a specific student
     *
     * @param auth                authentication service for login verification
     * @param repo                shared repository for data persistence
     * @param request             shared request service for handling requests
     * @param internshipService   shared service for managing internships
     * @param applicationService  shared service for managing applications
     * @param student             the student representative associated with this controller
     */
    public StudentController(AuthenticationService auth, IRepository repo, RequestService request, InternshipService internshipService, ApplicationService applicationService ,Student student) {
        super(auth, repo, request);
        this.student = student;
        this.internshipService = internshipService;
        this.applicationService = applicationService;
        filter.setStatus(InternStatus.APPROVED);
    }

    /**
     * Launches the student UI interface after log in
     * @param systemController shared controller class which allows student to navigate back to main menu
     */
    public void launch(SystemController systemController) {
        StudentUI studentUI = new StudentUI(systemController, this);
        studentUI.menu();
    }

    /**
     *  View all eligible internships with option to filter.
     *
     * @param filter  FilterCriteria object for filtering the internships
     * @return        list of internships matching the filter criteria
     */

	public List<InternshipOpportunity> viewFilteredInternships(FilterCriteria filter) {
        List <InternshipOpportunity> eligibleInternships = internshipService.getEligibleInternships(student);
        return internshipService.getFilteredInternship(eligibleInternships, filter);
	}

	/**
	 * Apply for an  internship.
     *
	 * @param internshipId                 Unique string id assigned to each Internship
     * @throws IllegalArgumentException    if the internship ID is invalid
     * @throws IllegalStateException       if the internship has already been filled or the student is not eligible to apply
	 */
	public void applyInternship(String internshipId)  throws ObjectNotFoundException, MaxExceedException, UserNotFoundException, IllegalStateException {
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
        applicationService.addApplication(app);
        internshipService.addPendApplicationToInternship(app);
	}

	/**
     * Accept an internship which has been offered to the student
	 *
	 * @param applicationId                 Unique string id assigned to each Application
	 */
	public void acceptPlacement(String applicationId) throws ObjectNotFoundException{
        applicationService.acceptApplication(student.getId(),applicationId);

	}

    /**
     * Withdraw an application (can be pending/accepted)
     *
     * @param appId                         Unique string id assigned to each Application
     * @param reason                        Reason provided by student for withdrawing
     * @throws IllegalArgumentException     if inputs are not strings
     * @throws SecurityException            if the application has already been withdrawn
     * @throws ObjectNotFoundException      if the application id is invalid
     * @throws ObjectAlreadyExistsException if there has been a previous exception
     */
    public void withdrawPlacement(String appId, String reason) throws IllegalArgumentException, SecurityException, ObjectNotFoundException,ObjectAlreadyExistsException {
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
     * @return List of applications by a specific student
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

    public User getStudentById(String studentId){
        return applicationService.getUserById(studentId);
    }

    public InternshipOpportunity getInternshipById(String InternID){
        return internshipService.findInternshipById(InternID);
    }
}