package controller.service;

import controller.ApplicationService;
import controller.database.IResposistory;
import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.user.CareerStaff;
import entity.user.CompanyRep;
import entity.user.Student;
import entity.request.*;

public class RequestService {

    private final IResposistory repo;
    private ApplicationService lol;
    public RequestService(IResposistory repo) {
        this.repo = repo;
    }

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
	 * @param req
	 */
	public void acceptWithdrawalRequest( WithdrawalRequest req) {
        req.approve();
        lol.processWithdrawal(req.getApplication(),true);
        repo.deleteRequest(req);
	}

    /**
     *
     * @param req
     */
    public void rejectWithdrawalRequest( WithdrawalRequest req) {
        req.reject();
        lol.processWithdrawal(req.getApplication(),false);
        repo.deleteRequest(req);
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