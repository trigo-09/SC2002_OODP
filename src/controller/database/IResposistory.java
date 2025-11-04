package controller.database;

import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.request.*;
import entity.user.*;
import java.util.*;
public interface IResposistory {

	/**
	 * 
	 * @param app
	 */
	void addApplication(Application app);

	/**
	 * 
	 * @param staff
	 */
	void addCareerStaff(CareerStaff staff);

	/**
	 * 
	 * @param intern
	 */
	void addInternship(InternshipOpportunity intern);

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
     * @param withdrawalRequest
     */
    public void removeWithdrawalRequest(WithdrawalRequest withdrawalRequest);

    /**
     *
     * @param registrationRequest
     */
    public void removeRegistrationRequest(RegistrationRequest registrationRequest);

    /**
     *
     * @param internshipVetRequest
     */
    public void removeInternshipVetRequest(InternshipVetRequest internshipVetRequest);

	/**
	 * 
	 * @param studentId
	 */
	List<Application> appByStudent(String studentId);

	/**
	 * 
	 * @param internId
	 */
	List<InternshipOpportunity> appForInternship(String internId);

	/**
	 * 
	 * @param repId
	 */
	CompanyRep approveCompanyRep(String repId);

	/**
	 * 
	 * @param userId
	 */
	User findUser(String userId);

	List<Application> getApplications();

	Map<String, CompanyRep> getApprovedReps();

	Map<String, CareerStaff> getCareerStaff();

	List<InternshipOpportunity> getInternships();

	Map<String, CompanyRep> getPendingReps();

	Map<String, Student> getStudents();

    List<InternshipVetRequest> getInternshipVetRequests();

    List<RegistrationRequest> getRegistrationRequests();

    List<WithdrawalRequest> getWithdrawalRequests();

	/**
	 * 
	 * @param rep
	 */
	void registerCompanyRep(CompanyRep rep);

}