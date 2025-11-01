package controller.database;

import java.util.*;
import entity.user.*;
import entity.internship.InternshipOpportunity;
import entity.application.Application;


public class SystemResposistory implements IResposistory {

	private static final long serialVersionUID = 1L;
	private final Map<String, Student> students = new HashMap<>();
	private final Map<String, CareerStaff> careerStaff = new HashMap<>();
	private final Map<String, CompanyRep> pendingReps = new HashMap<>();
	private final Map<String, CompanyRep> approvedReps = new HashMap<>();
	private final List<InternshipOpportunity> internships = new ArrayList<>();
	private final List<Application> applications = new ArrayList<>();

	/**
	 * 
	 * @param app
	 */
	public void addApplication(Application app) {
		// TODO - implement SystemResposistory.addApplication
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param staff
	 */
	public void addCareerStaff(CareerStaff staff) {
		// TODO - implement SystemResposistory.addCareerStaff
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param intern
	 */
	public void addInternship(InternshipOpportunity intern) {
		// TODO - implement SystemResposistory.addInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param student
	 */
	public void addStudent(Student student) {
		// TODO - implement SystemResposistory.addStudent
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param studentId
	 */
	public List<Application> appByStudent(String studentId) {
		// TODO - implement SystemResposistory.appByStudent
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param internId
	 */
	public List<InternshipOpportunity> appForInternship(String internId) {
		// TODO - implement SystemResposistory.appForInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param repId
	 */
	public CompanyRep approveCompanyRep(String repId) {
		// TODO - implement SystemResposistory.approveCompanyRep
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param userId
	 */
	public User findUser(String userId) {
		// TODO - implement SystemResposistory.findUser
		throw new UnsupportedOperationException();
	}

	public List<Application> getApplications() {
		return this.applications;
	}

	public Map<String, CompanyRep> getApprovedReps() {
		return this.approvedReps;
	}

	public Map<String, CareerStaff> getCareerStaff() {
		return this.careerStaff;
	}

	public List<InternshipOpportunity> getInternships() {
		return this.internships;
	}

	public Map<String, CompanyRep> getPendingReps() {
		return this.pendingReps;
	}

	public Map<String, Student> getStudents() {
		return this.students;
	}

	/**
	 * 
	 * @param rep
	 */
	public void registerCompanyRep(CompanyRep rep) {
		// TODO - implement SystemResposistory.registerCompanyRep
		throw new UnsupportedOperationException();
	}

}