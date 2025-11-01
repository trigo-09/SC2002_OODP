package controller;

import entity.application.Application;
import entity.application.WithdrawalRequest;
import entity.internship.InternshipOpportunity;
import entity.user.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemRepository implements Serializable {

    private static final long serialVersionUID = 1L;

    // --- Attributes ---
    private final Map<String, Student> students;
    private final Map<String, CareerStaff> careerStaff;
    private final Map<String, CompanyRep> pendingReps;
    private final Map<String, CompanyRep> approvedReps;
    private final List<InternshipOpportunity> internships;
    private final List<Application> applications;
    private final List<WithdrawalRequest> withdrawalRequests;

    public SystemRepository() {
        this.students = new HashMap<>();
        this.careerStaff = new HashMap<>();
        this.pendingReps = new HashMap<>();
        this.approvedReps = new HashMap<>();
        this.internships = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.withdrawalRequests = new ArrayList<>();
    }

    // --- Getters --
    public Map<String, Student> getStudents() { return students; }
    public Map<String, CareerStaff> getCareerStaff() { return careerStaff; }
    public Map<String, CompanyRep> getPendingReps() { return pendingReps; }
    public Map<String, CompanyRep> getApprovedReps() { return approvedReps; }
    public List<InternshipOpportunity> getInternships() { return internships; }
    public List<Application> getApplications() { return applications; }
    public List<WithdrawalRequest> getWithdrawalRequests() { return withdrawalRequests; }

    // --- Core Methods ---
    public User findUser(String userId) {
        // TODO: Search across students, staff, reps; return User if found
        return null;
    }

    public void addStudent(Student student) {
        // TODO: Add student to students map
    }

    public void addCareerStaff(CareerStaff staff) {
        // TODO: Add staff to careerStaff map
    }

    public void registerCompanyRep(CompanyRep rep) {
        // TODO: Add rep to pendingReps
    }

    public void approveCompanyRep(String repId) {
        // TODO: Move from pendingReps to approvedReps
    }

    public void addInternship(InternshipOpportunity internship) {
        // TODO: Add internship to internships list
    }

    public void addApplication(Application app) {
        // TODO: Add application to applications list
    }

    public void removeApplication(Application app) {
        // TODO: Remove application from applications list
    }

    public void addWithdrawalRequest(WithdrawalRequest request) {
        // TODO: Add withdrawal request to withdrawalRequests list
    }

    public void removeWithdrawalRequest(WithdrawalRequest request) {
        // TODO: Remove withdrawal request from withdrawalRequests list
    }

    public List<Application> appByStudent(String studentId) {
        // TODO: Filter and return applications by student ID
        return null;
    }

    public List<InternshipOpportunity> appForInternship(String internId) {
        // TODO: Filter and return internships by internship ID
        return null;
    }
}
