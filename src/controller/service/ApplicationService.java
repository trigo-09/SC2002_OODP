package controller.service;

import controller.database.*;
import entity.application.*;
import entity.internship.InternshipOpportunity;
import entity.user.Student;
import java.util.List;
import util.exceptions.MaxExceedException;
import util.exceptions.ObjectNotFoundException;

/**
 * Service class for managing internship applications.
 * Handles application submissions, reviews, acceptances, and withdrawals.
 */
public class ApplicationService {

    private final IRepository systemRepository;
    private final InternshipService internshipService;
    private final RequestService requestService;
    private static final int MAX_ACTIVE_APPLICATIONS = 3;

    /**
     * Constructs the application service with required dependencies.
     *
     * @param systemRepository   shared repository for data persistence
     * @param internshipService  shared service for managing internships
     * @param requestService     shared service for handling requests
     */
    public ApplicationService(IRepository systemRepository, InternshipService internshipService, RequestService requestService) {
        this.systemRepository = systemRepository;
        this.internshipService = internshipService;
        this.requestService = requestService;
    }

    /**
     * Student applies for an internship opportunity.
     * @param studentId the ID of the student
     * @param internshipId the ID of the internship opportunity
     * @throws IllegalStateException if the student is not eligible, internship is filled, or max applications reached
     * @throws IllegalArgumentException if the internship ID is invalid
     */
    public Application apply(String studentId, String internshipId) throws MaxExceedException {
        if (systemRepository.findInternshipOpportunity(internshipId) == null) {
            throw new IllegalArgumentException("Invalid internship ID: " + internshipId);
        }
        // check eligibility
        Student user = (Student) systemRepository.findUser(studentId);
        if (user == null) throw new IllegalArgumentException("Invalid student ID: " + studentId);
        if (!(systemRepository.findUser(studentId) instanceof Student))
            throw new SecurityException("User is not a student: " + studentId);

        if (!internshipService.isEligible(user, internshipId)) {
            throw new IllegalStateException("Student is not eligible to apply for this internship.");
        }
        else if (user.getNumOfApplications() >= MAX_ACTIVE_APPLICATIONS) {
            throw new MaxExceedException("Student already has " + MAX_ACTIVE_APPLICATIONS + " active applications");
        }
        else if (internshipService.isFilled(internshipId)) {
            throw new IllegalStateException("Internship is already filled.");
        }
        else if (systemRepository.applicationByStudent(studentId).stream()
                .anyMatch(app -> app.getInternshipId().equalsIgnoreCase(internshipId))) {
            throw new IllegalStateException("Student has already applied for this internship.");
        }
        else {
            Application application = new Application(user.getId(), internshipId);
            systemRepository.addApplication(studentId, application);
            return application;
        }
    }

    /** 
     * Student accepts an approved application.
     * This will automatically withdraw all other pending or approved applications of the student.
     * @param studentId the ID of the student
     * @param applicationId the ID of the application to accept
     * @throws IllegalArgumentException if the application ID is invalid
     * @throws SecurityException        if the application does not belong to the student
     * 
     */
    public void acceptApplication(String studentId, String applicationId) {
        Application application = systemRepository.findApplication(applicationId);
        // Ensure application exists
        if (application == null) {
            throw new IllegalArgumentException("Invalid application ID: " + applicationId);
        }
        // Security check: ensure the application belongs to the student
        if (!application.getStudentId().equalsIgnoreCase(studentId)) {
            throw new SecurityException("You can only accept your own applications.");
        }
        if (validStatusTransition(application.getStatus(),ApplicationStatus.ACCEPTED)) {
            application.changeApplicationStatus(ApplicationStatus.ACCEPTED);
        } else {
            throw new IllegalStateException("Application is not in an APPROVED state and cannot be accepted.");
        }
        // fetch updated application list and withdraw other pending or successful applications
        List<Application> applications = systemRepository.applicationByStudent(application.getStudentId());
        applications.stream()
                .filter(app -> app.getStatus() == ApplicationStatus.PENDING || app.getStatus() == ApplicationStatus.APPROVED)
                .forEach(app->app.changeApplicationStatus(ApplicationStatus.WITHDRAWN));
    }

    /**
     * Student can request withdrawal of an application.
     * @param appId the ID of the application to withdraw
     * @param reason the reason for withdrawal
     * @throws IllegalArgumentException if the application ID is invalid
     * @throws IllegalStateException    if the application is not in a withdrawable state
     * @throws SecurityException        if the application does not belong to the student
     */
    public void requestWithdrawal(String studentId, String appId, String reason) throws ObjectNotFoundException {
        Application application = systemRepository.findApplication(appId);
        // Ensure application exists
        if (application == null) {
            throw new ObjectNotFoundException("Invalid application ID: " + appId);
        }
        // Security check: ensure the application belongs to the student
        if (!application.getStudentId().equalsIgnoreCase(studentId)) {
            throw new SecurityException("You can only withdraw your own applications.");
        }
        // Check if the application can be withdrawn
        if (validStatusTransition(application.getStatus(), ApplicationStatus.WITHDRAWN)) {
            requestService.createWithdrawalRequest(studentId, application, reason);
        } else {
            throw new IllegalStateException("Application is already " + application.getStatus() + " and cannot be withdrawn.");
        }
    }

    /**
     * Reviews an application submitted to an internship opportunity.
     * @param repId the ID of the company representative
     * @param appId the ID of the application to review
     * @param approve true to approve, false to reject
     * @throws ObjectNotFoundException  if the application ID is invalid
     * @throws SecurityException        if the application does not belong to this representative
     * @throws IllegalStateException    if the application has already been reviewed
     */
    public void reviewApplication(String repId, String appId, boolean approve) throws ObjectNotFoundException {

        Application application = systemRepository.findApplication(appId);
        // Ensure application exists
        if (application == null) {
            throw new ObjectNotFoundException("Invalid application ID: " + appId);
        }
        InternshipOpportunity internship = internshipService.findInternshipById(application.getInternshipId());
        // Ensure internship exists
        if (internship == null) {
            throw new ObjectNotFoundException("Invalid internship ID associated with application: " + application.getInternshipId());
        }
        // Ensure the application has not been reviewed already
        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new IllegalStateException("This application has already been reviewed.");
        }
        
        // Security check: ensure the internship belongs to the representative
        if (!internship.getCreatedBy().equalsIgnoreCase(repId)) {
            throw new SecurityException("You can only review applications for your own internships.");
        }
        // Ensure number of available slots if approving
        if (approve && internshipService.isFilled(internship.getId())) {
            throw new IllegalStateException("Cannot approve application; internship is already filled.");
        }
        // Apply the decision
        application.changeApplicationStatus(
            approve ? ApplicationStatus.APPROVED : ApplicationStatus.REJECTED
        );
    }   

    /**
	 * Helper method to validate status transitions.
	 * @param current the current status
	 * @param next the desired new status
	 * @return true if the transition is valid, false otherwise
	 */
    private boolean validStatusTransition(ApplicationStatus current, ApplicationStatus next) {
        return switch (current) {
            case PENDING -> next == ApplicationStatus.APPROVED || next == ApplicationStatus.REJECTED || next == ApplicationStatus.WITHDRAWN;
            case APPROVED -> next == ApplicationStatus.ACCEPTED || next == ApplicationStatus.WITHDRAWN;
            case ACCEPTED -> next == ApplicationStatus.WITHDRAWN;
            case REJECTED, WITHDRAWN -> false;
            default -> false;
        };
    }
}

