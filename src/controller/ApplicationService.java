package controller;

import entity.application.Application;
import entity.application.ApplicationStatus;
import entity.internship.InternshipLevel;
import entity.internship.InternshipOpportunity;
import entity.user.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ApplicationService {

    private final List<Application> applications;

    private static final int MAX_ACTIVE_APPLICATIONS = 3;

    public ApplicationService() {
        this.applications = new ArrayList<>();
    }

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

}
    
