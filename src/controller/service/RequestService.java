package controller.service;

import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.user.CareerStaff;
import entity.user.CompanyRep;
import entity.user.Student;
import entity.request.*;

public class RequestService {

	/**
	 * 
	 * @param s
	 * @param app
	 * @param reason
	 */
	public void createWithdrawalRequest(Student s, Application app, String reason) {
		// TODO - implement RequestService.createWithdrawalRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param staff
	 * @param req
	 * @param approve
	 */
	public void processWithdrawalRequest(CareerStaff staff, WithdrawalRequest req, boolean approve) {
		// TODO - implement RequestService.processWithdrawalRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param rep
	 */
	public void createRegistrationRequest(CompanyRep rep) {
		// TODO - implement RequestService.createRegistrationRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param staff
	 * @param reg
	 * @param approve
	 */
	public void processRegistrationRequest(CareerStaff staff, RegistrationRequest reg, boolean approve) {
		// TODO - implement RequestService.processRegistrationRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param rep
	 * @param I
	 */
	public void createInternshipRequest(CompanyRep rep, InternshipOpportunity I) {
		// TODO - implement RequestService.createInternshipRequest
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param staff
	 * @param req
	 * @param approve
	 */
	public void processInternshipRequest(CareerStaff staff, InternshipVetRequest req, boolean approve) {
		// TODO - implement RequestService.processInternshipRequest
		throw new UnsupportedOperationException();
	}

}