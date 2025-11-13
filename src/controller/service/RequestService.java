package controller.service;

import controller.database.IRepository;
import controller.database.IRequestRepo;
import entity.application.Application;
import entity.internship.InternshipOpportunity;
import entity.user.CompanyRep;
import entity.request.*;
import util.exceptions.ObjectAlreadyExistsException;
import util.exceptions.ObjectNotFoundException;
import java.util.List;

/**
 * Service class for managing request
 * Handles request creation, deletion, approval and rejection
 */
public class RequestService {
    private final IRepository repo;

    /**
     * constructor of request service class
     * @param repo
     */
    public RequestService(IRepository repo) {
        this.repo = repo;
    }

    /**
     * get request
     * @param requestId request id
     * @return request
     */
    public Request getRequest(String requestId) {
        return repo.getRequest(requestId);
    }

	/**
     * create request to withdraw application and add to request repository
     * @param studentId
     * @param app
     * @param reason
     * @throws ObjectNotFoundException if application can not be found
     * @throws ObjectNotFoundException if there has been a request created already
     * @throws SecurityException if illegal access of application by student
     */
	public void createWithdrawalRequest(String studentId, Application app, String reason) throws ObjectAlreadyExistsException, ObjectNotFoundException{
        if(repo.findApplication(app.getApplicationId())==null){
            throw new ObjectNotFoundException("Application not found");
        }
        if(!app.getStudentId().equals(studentId)){
            throw new SecurityException("Cannot create withdrawal request for other student");
        }
        for (WithdrawalRequest wr : getPendingWithdrawal()){
            boolean sameUser = wr.getRequesterId().equals(studentId);
            boolean sameApp = wr.getApplication().equals(app);
            if (sameUser && sameApp){
                throw new ObjectAlreadyExistsException("Withdrawal request already exists");
            }

        }
        Request request = new WithdrawalRequest(app,reason,studentId);
		repo.addRequest(request);
	}

	/**
     * accept withdrawal request and delete request from repository
     * @param requestId
     * @throws ObjectNotFoundException if request cannot be found
     */
	public void acceptWithdrawalRequest(String requestId) throws ObjectNotFoundException{
        Request req = getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.approve();
        repo.removeRequest(req.getId());
	}

    /**
     * reject withdrawal request and delete request from repositoru
     * @param requestId request ID
     * @throws ObjectNotFoundException if request cannot be found
     */
    public void rejectWithdrawalRequest(String requestId) throws ObjectNotFoundException {
        Request req = getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.reject();
        repo.removeRequest(req.getId());
    }

	/**
	 * create registration request from new company rep account and add request to repository
	 * @param rep
	 */
	public void createRegistrationRequest(CompanyRep rep) {
        Request request = new RegistrationRequest(rep);
        repo.addRequest(request);
	}

	/**
	 *accept request and delete request from repository
	 * @param requestId
     * @throws ObjectNotFoundException if request cannot be found
	 */
	public void approveRegistrationRequest(String requestId) throws ObjectNotFoundException{
        Request req = getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.approve();
        repo.approveCompanyRep(req.getRequesterId());
        repo.removeRequest(req.getId());
	}

    /**
     *reject request and delete request from repository
     * @param requestId
     * @throws ObjectNotFoundException if request cannot be found
     */
    public void rejectRegistrationRequest(String requestId) throws ObjectNotFoundException {
        Request req = getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.reject();
        repo.removeRequest(req.getId());
    }

	/**
	 * create new internship request and add to repository
	 * @param internship
     * @param repId
     * @throws ObjectAlreadyExistsException if there is already request made
	 */
	public void createInternshipRequest(String repId, InternshipOpportunity internship) throws ObjectAlreadyExistsException {
        for (InternshipVetRequest ivr : getPendingInternshipVet()){
            boolean sameReqTitle = ivr.getInternship().getTitle().equals(internship.getTitle());
            if (sameReqTitle) {
                throw new ObjectAlreadyExistsException("Internship vetting request with identical internship title already exists");
            }
        }
        for (InternshipOpportunity i: repo.getAllInternships()){
            boolean sameInternTile = i.getTitle().equals(internship.getTitle());
            if (sameInternTile){
                throw new ObjectAlreadyExistsException("Internship opportunity with identical title already exists");
            }
        }
        Request req = new InternshipVetRequest(internship,repId);
        repo.addRequest(req);
	}

    /**
     *accept request and delete request from repository
     * @param requestId
     * @throws ObjectNotFoundException if request cannot be found
     */
	public void approveInternshipRequest(String requestId) throws ObjectNotFoundException{
        Request req = getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.approve();
        repo.removeRequest(req.getId());
	}

    /**
     *reject request and delete request from repository
     * @param requestId
     * @throws ObjectNotFoundException if request cannot be found
     */
    public void rejectInternshipRequest(String requestId) throws ObjectNotFoundException{
        Request req = getRequest(requestId);
        if(req==null){
            throw new ObjectNotFoundException("Invalid requestId, Request not found");
        }
        req.reject();
        repo.removeRequest(req.getId());
    }

    /**
     * delete internshipRequest
     * @param internshipId
     */
    public void deleteInternshipRequest(String internshipId) {
       Request request = getPendingInternshipVet().stream().filter(i-> i.getInternship().getId().equals(internshipId)).findFirst().get();
       repo.removeRequest(request.getId());
    }

    /**
     * get all requests
     * @return
     */
    public List<Request> getAllRequests() {
        return repo.getAllRequests(Request.class);
    }

    /**
     * get all pendingRegistration request
     * @return
     */
    public List<RegistrationRequest> getPendingRegistration(){
        return repo.getAllRequests(RegistrationRequest.class);
    }

    /**
     * get all pending internship request
     * @return
     */
    public List<InternshipVetRequest> getPendingInternshipVet(){
        return repo.getAllRequests(InternshipVetRequest.class);
    }

    /**
     * get all pending withdrawal request
     * @return
     */
    public List<WithdrawalRequest> getPendingWithdrawal(){
        return repo.getAllRequests(WithdrawalRequest.class);
    }

}