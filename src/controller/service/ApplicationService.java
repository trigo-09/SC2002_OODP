package controller.service;

import controller.database.*;
import entity.application.*;
import entity.internship.InternshipOpportunity;
import entity.user.Student;
import java.util.List;

import entity.user.User;
import util.exceptions.MaxExceedException;
import util.exceptions.ObjectNotFoundException;
import util.exceptions.UserNotFoundException;

/**
 * Service class for managing internship applications
 * Handles application submissions, reviews, acceptances, and withdrawals
 */
public class ApplicationService {

    private final IApplicationRepo systemRepository;
    private static final int MAX_ACTIVE_APPLICATIONS = 3;

    /**
     * Constructs the application service with required dependencies.
     *
     * @param systemRepository   shared repository for data persistence
     */
    public ApplicationService(IRepository systemRepository) {
        this.systemRepository = systemRepository;
    }

    /**
     * Student applies for an internship opportunity.
     * @param studentId the ID of the student
     * @param internshipId the ID of the internship opportunity
     * @throws UserNotFoundException if its invalid student id
     * @throws IllegalStateException if the student applied for the internship already or have accepted application
     * @throws MaxExceedException if student exceed their max allowed applications
     */
    public Application apply(String studentId, String internshipId) throws MaxExceedException,UserNotFoundException,IllegalArgumentException {
        // check eligibility
        Student user = (Student) getUserById(studentId);
        if (user == null) throw new UserNotFoundException("Invalid student ID: " + studentId);

        else if (user.getNumOfApplications() >= MAX_ACTIVE_APPLICATIONS) {
            throw new MaxExceedException("Student already has " + MAX_ACTIVE_APPLICATIONS + " active applications");
        }
        else if (systemRepository.applicationByStudent(studentId).stream()
                .anyMatch(app -> app.getInternshipId().equalsIgnoreCase(internshipId))) {
            throw new IllegalStateException("Student has already applied for this internship.");
        }
        else if (systemRepository.applicationByStudent(studentId).stream()
                .anyMatch(app -> app.getStatus() == ApplicationStatus.ACCEPTED)) {
            throw new IllegalStateException("Student has already approved for internship.");
        }
        else {
            return new Application(user.getId(), internshipId);
        }
    }

    /**
     *
     * @param studentId student ID
     * @return student object corresponding to the Id
     */
    public User getUserById(String studentId){
        return systemRepository.findUser(studentId);
    }


    /** 
     * Student accepts an approved application.
     * This will automatically withdraw all other pending or approved applications of the student.
     * @param studentId the ID of the student
     * @param applicationId the ID of the application to accept
     * @throws ObjectNotFoundException if the application ID is invalid
     * @throws SecurityException        if the application does not belong to the student
     * 
     */
    public void acceptApplication(String studentId, String applicationId) throws ObjectNotFoundException{
        Application application = findApplication(applicationId);
        // Ensure application exists
        if (application == null) {
            throw new ObjectNotFoundException("Invalid application ID: " + applicationId);
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
     * Reviews an application submitted to an internship opportunity for approval or rejection.
     * @param appId the ID of the application to review
     * @param approve true to approve, false to reject
     * @throws ObjectNotFoundException  if the application ID is invalid
     * @throws SecurityException        if the application does not belong to this representative
     * @throws IllegalStateException    if the application has already been reviewed
     */
    public void reviewApplication(String appId, boolean approve) throws ObjectNotFoundException,IllegalArgumentException {
        Application application = findApplication(appId);
        // Ensure application exists
        if (application == null) {
            throw new ObjectNotFoundException("Invalid application ID: " + appId);
        }

        // Ensure the application has not been reviewed already
        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new IllegalStateException("This application has already been reviewed.");
        }

        // Apply the decision
        if (approve) {
            if (validStatusTransition(application.getStatus(), ApplicationStatus.APPROVED)) {
                application.changeApplicationStatus(ApplicationStatus.APPROVED);
            } else
                throw new IllegalStateException("Invalid status transition from " + application.getStatus() + " to " + ApplicationStatus.APPROVED);
        } else {
            if (validStatusTransition(application.getStatus(), ApplicationStatus.REJECTED)) {
                application.changeApplicationStatus(ApplicationStatus.REJECTED);
            } else
                throw new IllegalStateException("Invalid status transition from " + application.getStatus() + " to " + ApplicationStatus.REJECTED);
        }
    }


    /**
     * finds application object and returns it
     * @param applicationId application id
     * @return Application object
     */
    public Application findApplication(String applicationId) {
        return systemRepository.findApplication(applicationId);
    }

    /**
	 * Helper method to validate status transitions.
	 * @param current the current status
	 * @param next the desired new status
	 * @return true if the transition is valid, false otherwise
	 */
    public boolean validStatusTransition(ApplicationStatus current, ApplicationStatus next) {
        return switch (current) {
            case PENDING -> next == ApplicationStatus.APPROVED || next == ApplicationStatus.REJECTED || next == ApplicationStatus.WITHDRAWN;
            case APPROVED -> next == ApplicationStatus.ACCEPTED || next == ApplicationStatus.WITHDRAWN;
            case ACCEPTED -> next == ApplicationStatus.WITHDRAWN;
            case REJECTED, WITHDRAWN -> false;
            default -> false;
        };
    }

    /**
     *
     * @param application application object added the student application list
     */
    public void addApplication(Application application) {
        systemRepository.addApplication(application.getStudentId(), application);
    }
}

