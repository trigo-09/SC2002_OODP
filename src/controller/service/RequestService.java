package controller.service;

import controller.database.IRepository;
import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.user.CompanyRep;
import entity.request.*;

public class RequestService {

    private final IRepository repo;
    public RequestService(IRepository repo) {
        this.repo = repo;
    }

	/**
     *
     * @param studentId
     * @param app
     * @param reason
     */
	public void createWithdrawalRequest(String studentId, Application app, String reason) {
        WithdrawalRequest request = new WithdrawalRequest(app,reason,studentId);
		repo.addWithdrawalRequest(request);
	}

	/**
     *
     * @param requestId
     */
	public void acceptWithdrawalRequest(String requestId) {
        WithdrawalRequest req = (WithdrawalRequest) repo.getRequest(requestId);
        req.approve(repo);
        repo.removeWithdrawalRequest(req.getId());
	}

    /**
     *
     * @param requestId
     */
    public void rejectWithdrawalRequest(String requestId) {
        WithdrawalRequest req = (WithdrawalRequest) repo.getRequest(requestId);
        req.reject(repo);
        repo.removeWithdrawalRequest(req.getId());
    }

	/**
	 * 
	 * @param rep
	 */
	public void createRegistrationRequest(CompanyRep rep) {
        RegistrationRequest request = new RegistrationRequest(rep);
        repo.addRegistrationRequest(request);
	}

	/**
	 * 
	 * @param requestId
	 */
	public void approveRegistrationRequest(String requestId) {
        RegistrationRequest req = (RegistrationRequest) repo.getRequest(requestId);
        req.approve();
        repo.approveCompanyRep(req.getId());
        repo.removeRegistrationRequest(req.getId());
	}

    public void rejectRegistrationRequest(String requestId) {
        RegistrationRequest req = (RegistrationRequest) repo.getRequest(requestId);
        req.reject();
        repo.removeRegistrationRequest(req.getId());
    }

	/**
	 * 
	 * @param internship
     * @param repId
	 */
	public void createInternshipRequest(String repId, InternshipOpportunity internship) {
        InternshipVetRequest req = new InternshipVetRequest(internship,repId);
        repo.addInternshipVetRequest(req);
	}

	/**
	 * 
	 * @param requestId
	 */
	public void approveInternshipRequest(String requestId) {
        InternshipVetRequest req = (InternshipVetRequest) repo.getRequest(requestId);
        req.approve();
        repo.removeInternshipVetRequest(req.getId());
	}

    public void rejectInternshipRequest(String requestId) {
        InternshipVetRequest req = (InternshipVetRequest) repo.getRequest(requestId);
        req.reject();
        repo.removeInternshipVetRequest(req.getId());
    }

    public void viewRequest(String requestId) {
        Request request = repo.getRequest(requestId);
        // to be filled \\
    }

}