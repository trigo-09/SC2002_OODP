package controller;

import entity.application.*;
import entity.internship.InternshipOpportunity;
import entity.request.*;
import controller.database.*;
import entity.user.Student;
import java.util.*;


public class ApplicationService {

    private final IResposistory systemRepository;
    private static final int MAX_ACTIVE_APPLICATIONS = 3;

    public ApplicationService(IResposistory systemRepository) {
        this.systemRepository = systemRepository;
    }

    // method to apply for internship
    public Application apply(Student student, InternshipOpportunity internship) {
        // fetch existing applications
        List<Application> applications = systemRepository.getApplications();
        
        // check eligibility
        if (!isEligible(student, internship)) {
            throw new IllegalArgumentException("Student is not eligible to apply for this internship.");
        }
        // check active applications
        long count = applications.stream()
                .filter(app -> Objects.equals(app.getStudentId(), student.getId()))
                .filter(app -> app.getStatus() == ApplicationStatus.PENDING || app.getStatus() == ApplicationStatus.REJECTED)
                .count();

        if (count >= MAX_ACTIVE_APPLICATIONS) {
            throw new IllegalStateException("Student already has " + MAX_ACTIVE_APPLICATIONS + " active applications");
        }

        Application application = new Application(student.getId(), internship.getId());
        systemRepository.addApplication(application);
        return application;
    }

    // eligibility method
    public boolean isEligible(Student student, InternshipOpportunity internship) {
        // Internship must be visible
        if (!internship.getVisibility()) {return false;}

        // Students year must match the internship level
        if (!internship.getLevel().isEligible(student.getYear())) {return false;}

        // Major must match (unless internship accepts "Any")
        String preferredMajors = internship.getPreferredMajors();
        if (!Objects.equals(preferredMajors, "Any") && !Objects.equals(preferredMajors, student.getMajor())) {
            return false;
        }

        // Passed all checks
        return true;
    }

    // method for student to accept application
    public void acceptApplication(Student student, Application application) {
        verifyOwnership(student, application);
        application.changeApplicationStatus(ApplicationStatus.ACCEPTED);
        // fetch updated application list and withdraw other pending or successful applications
        List<Application> applications = systemRepository.getApplications();
        applications.stream()
                .filter(app -> Objects.equals(app.getStudentId(), student.getId()))
                .filter(app -> app != application)
                .filter(app -> app.getStatus() == ApplicationStatus.PENDING || app.getStatus() == ApplicationStatus.APPROVED)
                .forEach(app -> app.changeApplicationStatus(ApplicationStatus.WITHDRAWN));
    }

    // method for student to request to withdraw application
    public WithdrawalRequest requestWithdrawal(Student student, Application application, String reason) {
        verifyOwnership(student, application);
        if (application.validStatusTransition(application.getStatus(), ApplicationStatus.WITHDRAWN)) {
            WithdrawalRequest request = new WithdrawalRequest(application, reason);
            systemRepository.addWithdrawalRequest(request);
            return request;
        }
        throw new IllegalStateException("Application is already " + application.getStatus() + " and cannot be withdrawn.");
    }

    // method for company rep to process applications
    public void reviewApplication(Application application, boolean approve) {
        if (approve) {
            application.changeApplicationStatus(ApplicationStatus.APPROVED);
        } else {
            application.changeApplicationStatus(ApplicationStatus.REJECTED);
        }
    }

    // method for career staff to process withdrawals
    public void processWithdrawal(WithdrawalRequest request, boolean approve) {
        if (approve) {
            request.approve();
            request.getApplication().changeApplicationStatus(ApplicationStatus.WITHDRAWN);
        } else {request.setDecision(RequestDecision.REJECTED);}
        systemRepository.removeWithdrawalRequest(request);
    }

    // ---- helper methods --- \\

    // verify that the application belongs to the student
    private void verifyOwnership(Student student, Application application) {
        if (!Objects.equals(application.getStudentId(), student.getId())) {
            throw new IllegalArgumentException("Application does not belong to this student.");
        }
    }
    // get all applications by a student
    public List<Application> getApplicationsByStudent(Student student) {
        List<Application> applications = systemRepository.getApplications();
        return applications.stream()
            .filter(app -> Objects.equals(app.getStudentId(), student.getId()))
            .toList();
    }
    // get all withdrawal requests by a student
    public List<WithdrawalRequest> getWithdrawalRequestsByStudent(Student student) {
        List<WithdrawalRequest> requests = systemRepository.getWithdrawalRequests();
        return requests.stream()
            .filter(req -> Objects.equals(req.getApplication().getStudentId(), student.getId()))
            .toList();
    }
}
