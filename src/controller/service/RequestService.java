package controller.service;

import controller.database.IRepository;
import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.user.CompanyRep;
import entity.request.*;
import util.exceptions.ObjectNotFoundException;
import util.exceptions.PasswordIncorrectException;

import java.util.List;


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
	public void createWithdrawalRequest(String studentId, Application app, String reason) throws ObjectNotFoundException{
        if(repo.findApplication(app.getApplicationId())==null){
            throw new ObjectNotFoundException("Application not found");
        }
        if(!app.getStudentId().equals(studentId)){
            throw new SecurityException("Cannot create withdrawal request for other student");
        }
        WithdrawalRequest request = new WithdrawalRequest(app,reason,studentId);
		repo.addWithdrawalRequest(request);
	}

	/**
     *
     * @param requestId
     */
	public void acceptWithdrawalRequest(String requestId) throws ObjectNotFoundException{
        WithdrawalRequest req = (WithdrawalRequest) repo.getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.approve();
        repo.removeWithdrawalRequest(req.getId());
	}

    /**
     *
     * @param requestId
     */
    public void rejectWithdrawalRequest(String requestId) throws ObjectNotFoundException {
        WithdrawalRequest req = (WithdrawalRequest) repo.getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.reject();
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
	public void approveRegistrationRequest(String requestId) throws ObjectNotFoundException{
        RegistrationRequest req = (RegistrationRequest) repo.getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.approve();
        repo.approveCompanyRep(req.getId());
        repo.removeRegistrationRequest(req.getId());
	}

    public void rejectRegistrationRequest(String requestId) throws ObjectNotFoundException {
        RegistrationRequest req = (RegistrationRequest) repo.getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
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
	public void approveInternshipRequest(String requestId) throws ObjectNotFoundException{
        InternshipVetRequest req = (InternshipVetRequest) repo.getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.approve();
        repo.removeInternshipVetRequest(req.getId());
	}

    public void rejectInternshipRequest(String requestId) throws ObjectNotFoundException{
        InternshipVetRequest req = (InternshipVetRequest) repo.getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.reject();
        repo.removeInternshipVetRequest(req.getId());
    }


    public void deleteInternshipRequest(String internshipId) {
       Request request = getPendingInternshipVet().stream().filter(i-> i.getInternship().getId().equals(internshipId)).findFirst().get();
       repo.removeInternshipVetRequest(request.getId());
    }

    public List<Request> getAllRequests() {
        return repo.getAllRequests(Request.class);
    }

    public List<RegistrationRequest> getPendingRegistration(){
        return repo.getAllRequests(RegistrationRequest.class);
    }

    public List<InternshipVetRequest> getPendingInternshipVet(){
        return repo.getAllRequests(InternshipVetRequest.class);
    }

    public List<WithdrawalRequest> getPendingWithdrawal(){
        return repo.getAllRequests(WithdrawalRequest.class);
    }

}