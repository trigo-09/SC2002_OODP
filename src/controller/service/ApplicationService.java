package controller.service;


import controller.database.*;
import entity.application.*;
import entity.user.Student;
import java.util.*;


public class ApplicationService {

    private final IRepository systemRepository;
    private final InternshipService internshipService;
    private final RequestService requestService;
    private static final int MAX_ACTIVE_APPLICATIONS = 3;


    public ApplicationService(IRepository systemRepository, InternshipService internshipService, RequestService requestService) {
        this.systemRepository = systemRepository;
        this.internshipService = internshipService;
        this.requestService = requestService;
    }

    // method to apply for internship
    public void apply(String StudentId, String internshipId) {
        // check eligibility
        Student user = (Student) systemRepository.findUser(StudentId);
        if (!internshipService.isEligible(user, internshipId)) {
            throw new IllegalArgumentException("Student is not eligible to apply for this internship.");
        }
        else if (user.getNumOfApplications() < MAX_ACTIVE_APPLICATIONS) {
            System.out.println("Student already has " + MAX_ACTIVE_APPLICATIONS + " active applications");
        }
        else {
            Application application = new Application(user.getId(), internshipId);
            systemRepository.addApplication(StudentId,application);
        }
    }


    // method for student to accept application
    public void acceptApplication(String applicationId) {
        Application application = systemRepository.findApplication(applicationId);
        if (validStatusTransition(application.getStatus(),ApplicationStatus.ACCEPTED)) {
        application.changeApplicationStatus(ApplicationStatus.ACCEPTED);
        }
        // fetch updated application list and withdraw other pending or successful applications
        List<Application> applications = systemRepository.applicationByStudent(application.getStudentId());
        applications.stream()
                .filter(app -> app.getStatus() == ApplicationStatus.PENDING)
                .forEach(app->app.changeApplicationStatus(ApplicationStatus.WITHDRAWN));
    }

    // method for student to request to withdraw application
    public void requestWithdrawal(String appId, String reason) {
        Application application = systemRepository.findApplication(appId);
        if (validStatusTransition(application.getStatus(), ApplicationStatus.WITHDRAWN)) {
            requestService.createWithdrawalRequest(application.getStudentId(), application, reason);
        }
        throw new IllegalStateException("Application is already " + application.getStatus() + " and cannot be withdrawn.");
    }

    // method for company rep to process applications
    public void reviewApplication(String repId, String appId, boolean approve) {
        
        Application application = systemRepository.findApplication(appId);
        if (approve) {
            application.changeApplicationStatus(ApplicationStatus.APPROVED);
        } else {
            application.changeApplicationStatus(ApplicationStatus.REJECTED);
        }
    }

   // HELPER METHOD\\
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

