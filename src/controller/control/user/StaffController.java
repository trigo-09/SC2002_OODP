package controller.control.user;

import boundary.StaffUI;
import controller.control.SystemController;
import controller.database.IRepository;
import controller.service.ApplicationService;
import controller.service.AuthenticationService;
import controller.service.InternshipService;
import controller.service.RequestService;
import entity.internship.InternshipOpportunity;
import entity.request.InternshipVetRequest;
import entity.request.RegistrationRequest;
import entity.request.WithdrawalRequest;
import entity.user.CareerStaff;
import java.util.*;
import util.FilterCriteria;

public class StaffController extends UserController {

	private final CareerStaff staff;
	private final InternshipService internshipService;
    private final ApplicationService applicationService;

	public StaffController(AuthenticationService auth, IRepository repository, RequestService requestService,  CareerStaff staff){
		super(auth, repository, requestService);
		this.staff = staff;
		this.internshipService = new InternshipService(repository);
        this.applicationService = new ApplicationService(repository);

	}

	public void launch(SystemController systemController){
		StaffUI  staffUI = new StaffUI(systemController, this);
		staffUI.menuLoop();
	}

	/**
	 * 
	 * @param rep
	 */
	public void approveRep(String rep) throws Exception {
		getRequest().approveRegistrationRequest(rep); // this returns the requestservice
	}

	/**
	 * 
	 * @param rep
	 */
	public void rejectRep(String rep) throws Exception {
		getRequest().rejectRegistrationRequest(rep);
	}

	/**
	 * 
	 * @param intern
	 */
	public void approveInternship(String intern) throws Exception {
		getRequest().approveInternshipRequest(intern);
	}

	/**
	 * 
	 * @param intern
	 */
	public void rejectInternship(String intern) throws Exception {
		getRequest().rejectInternshipRequest(intern);
	}

	/**
	 * 
	 * @param app
	 */
	public void approveWithdrawal(String app) throws Exception {
		getRequest().acceptWithdrawalRequest(app);
        internshipService.removeApplicationFromInternship(applicationService.findApplication(app));
	}

	/**
	 * 
	 * @param app
	 */
	public void rejectWithdrawal(String app) throws Exception {
		getRequest().rejectWithdrawalRequest(app);
	}

	/**
	 * 
	 * @param filter
	 */
	public List<InternshipOpportunity> viewInternshipsFiltered(FilterCriteria filter) {
		List<InternshipOpportunity> internships = getRepo().getAllInternships();
		return internshipService.getFilteredInternship(internships, filter);
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