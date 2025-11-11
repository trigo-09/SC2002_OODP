package controller.control.user;

import java.util.*;

import boundary.StaffUI;
import controller.control.SystemController;
import controller.database.IRepository;
import controller.service.AuthenticationService;
import controller.service.InternshipService;
import controller.service.RequestService;
import entity.internship.InternshipOpportunity;
import entity.request.InternshipVetRequest;
import entity.request.RegistrationRequest;
import entity.request.WithdrawalRequest;
import entity.user.CareerStaff;
import util.FilterCriteria;

public class StaffController extends UserController {

	private CareerStaff staff;
	private final InternshipService internshipService;

	public StaffController(AuthenticationService auth, IRepository repository, RequestService requestService,  CareerStaff staff){
		super(auth, repository, requestService);
		this.staff = staff;
		this.internshipService = new InternshipService(repository, requestService);
	}

	public void launch(SystemController systemController){
		StaffUI  staffUI = new StaffUI(systemController, this);
		staffUI.menuLoop();
	}

	/**
	 * 
	 * @param rep
	 */
	public void approveRep(String rep) {
		getRequest().approveRegistrationRequest(rep); // this returns the requestservice
	}

	/**
	 * 
	 * @param rep
	 */
	public void rejectRep(String rep) {
		getRequest().rejectRegistrationRequest(rep);
	}

	/**
	 * 
	 * @param intern
	 */
	public void approveInternship(String intern) {
		getRequest().approveInternshipRequest(intern);
	}

	/**
	 * 
	 * @param intern
	 */
	public void rejectInternship(String intern) {
		getRequest().rejectInternshipRequest(intern);
	}

	/**
	 * 
	 * @param app
	 */
	public void approveWithdrawal(String app) {
		getRequest().acceptWithdrawalRequest(app);
	}

	/**
	 * 
	 * @param app
	 */
	public void rejectWithdrawal(String app) {
		getRequest().rejectWithdrawalRequest(app);
	}

	/**
	 * 
	 * @param filter
	 */
	public List<InternshipOpportunity> viewInternshipsFiltered(FilterCriteria filter) {
		return internshipService.getFilteredInternship(filter);
	}

	public List<RegistrationRequest> viewPendingReg(){
		return getRequest().getPendingRegistration();
	}

	public List<InternshipVetRequest> viewPendingInternshipVet(){
		return getRequest().getPendingInternshipVet();
	}

	public List<WithdrawalRequest> viewPendingWithdrawal(){
		return getRequest().getPendingWithdrawal();
	}

	public CareerStaff getStaff(){
		return this.staff;
	}

}