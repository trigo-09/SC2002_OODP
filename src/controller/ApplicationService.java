package controller;

import entity.application.*;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import entity.user.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ApplicationService {

    private final List<Application> applications;
    private final List<WithdrawalRequest> withdrawalRequests = new ArrayList<>();

    private static final int MAX_ACTIVE_APPLICATIONS = 3;

    public ApplicationService() {
        this.applications = new ArrayList<>();
    }

    // method to apply for internship
    public Application apply(Student student, InternshipOpportunity internship) {
        // check eligibility
        if (!isEligible(student, internship)) {
            throw new IllegalArgumentException("Student is not eligible to apply for this internship.");
        }
        // check active applications
        long count = applications.stream()
                .filter(app -> Objects.equals(app.getStudentId(), student.getId()))
                .filter(app -> app.getStatus() == ApplicationStatus.PENDING)
                .count();

        if (count >= MAX_ACTIVE_APPLICATIONS) {
            throw new IllegalStateException("Student already has " + MAX_ACTIVE_APPLICATIONS + " active applications");
        }

        Application application = new Application(student.getId(), internship.getId());
        applications.add(application);
        return application;
    }

    // eligibility check
    public boolean isEligible(Student student, InternshipOpportunity internship) {
        boolean levelMismatch = student.getYear() < 3 && internship.getLevel() != InternshipLevel.BASIC;
        boolean notVisible = !internship.isVisible();
        boolean majorMismatch = !Objects.equals(internship.getPreferredMajors(), "Any")
                                && !Objects.equals(internship.getPreferredMajors(), student.getMajor());

        return !(levelMismatch || notVisible || majorMismatch);
    }

    // method for student to accept application
    public void acceptApplication(Student student, Application application) {
        verifyOwnership(student, application);
        application.changeApplicationStatus(ApplicationStatus.ACCEPTED);
        // withdraw other pending or successful applications
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
            WithdrawalRequest request = new WithdrawalRequest(application, student, reason);
            withdrawalRequests.add(request);
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
            request.setDecision(WithdrawalDecision.APPROVED);
            request.getApplication().changeApplicationStatus(ApplicationStatus.WITHDRAWN);
        } else {request.setDecision(WithdrawalDecision.REJECTED);}
        withdrawalRequests.remove(request);
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
        return applications.stream()
            .filter(app -> Objects.equals(app.getStudentId(), student.getId()))
            .toList();
    }
    // get all withdrawal requests
    public List<WithdrawalRequest> getWithdrawalRequests() {
        return new ArrayList<>(withdrawalRequests);
    }   
}
