package controller.database;

import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.request.*;
import entity.user.*;
import java.util.*;
public interface IResposistory {

	/**
     *
     * @param studentId
     * @param app
     */
	void addApplication(String studentId,Application app);

	/**
	 * 
	 * @param staff
	 */
	void addCareerStaff(CareerStaff staff);

	/**
     *
     * @param repId
     * @param intern
     */
	void addInternship(String repId,InternshipOpportunity intern);

	/**
	 * 
	 * @param student
	 */
	void addStudent(Student student);

    /**
     *
     * @param withdrawalRequest
     */
    public void addWithdrawalRequest(WithdrawalRequest withdrawalRequest);


    /**
     *
     * @param registrationRequest
     */
    public void addRegistrationRequest(RegistrationRequest registrationRequest);


    /**
     *
     * @param internshipVetRequest
     */
    public void addInternshipVetRequest(InternshipVetRequest internshipVetRequest);

    /**
     *
     * @param requestId
     */
    public void removeWithdrawalRequest(String requestId);

    /**
     *
     * @param requestId
     */
    public void removeRegistrationRequest(String requestId);

    /**
     *
     * @param requestId
     */
    public void removeInternshipVetRequest(String requestId);

	/**
	 * 
	 * @param studentId
	 */
	List<Application> applicationByStudent(String studentId);

	/**
	 * 
	 * @param internId
	 */
	List<Application> applicationForInternship(String internId);

	/**
	 * 
	 * @param repId
	 */
	void approveCompanyRep(String repId);

	/**
	 * 
	 * @param userId
	 */
	User findUser(String userId);

    Application findApplication(String applicationId);

    InternshipOpportunity findInternshipOpportunity(String internshipId);

	List<Application> getAllApplications();

	Map<String, CompanyRep> getApprovedReps();

	Map<String, CareerStaff> getCareerStaff();

	List<InternshipOpportunity> getAllInternships();

	Map<String, CompanyRep> getPendingReps();

	Map<String, Student> getStudents();

    public <T extends Request> List<T> getAllRequests(Class<T> type);

    /**
	 * 
	 * @param rep
	 */
	void registerCompanyRep(CompanyRep rep);


    void deleteApplication(String studentId, String applicationId);

    Request getRequest(String requestId);
}