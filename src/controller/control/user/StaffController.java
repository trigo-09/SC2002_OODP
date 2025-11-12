package controller.control.user;

import boundary.usermenu.StaffUI;
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

/**
 * controller class for Career staff
 * act as layer between user and services
 * allow staff to view internship,process request and generate report
 */
public class StaffController extends UserController {

	private final CareerStaff staff;
	private final InternshipService internshipService;
    private final ApplicationService applicationService;

    /**
     * constructor of staff controller
     * @param auth authentication service
     * @param repository system repository
     * @param requestService request service
     * @param internshipService internship service
     * @param applicationService application service
     * @param staff staff
     */
	public StaffController(AuthenticationService auth, IRepository repository, RequestService requestService,InternshipService internshipService, ApplicationService applicationService , CareerStaff staff){
		super(auth, repository, requestService);
		this.staff = staff;
		this.internshipService = internshipService;
        this.applicationService = applicationService;

	}

    /**
     * create staff UI and launch the menu of staff UI
     * @param systemController systemController
     */
	public void launch(SystemController systemController){
		StaffUI  staffUI = new StaffUI(systemController, this);
		staffUI.menuLoop();
	}

	/**
	 * approve company rep account
	 * @param reqId company rep's Id
	 */
	public void approveRep(String reqId) throws Exception {
		getRequestService().approveRegistrationRequest(reqId);
	}

	/**
	 * reject company rep account
	 * @param reqId company rep's Id
	 */
	public void rejectRep(String reqId) throws Exception {
		getRequestService().rejectRegistrationRequest(reqId);
	}

	/**
	 * approve internship
	 * @param reqId request id
	 */
	public void approveInternship(String reqId) throws Exception {
		getRequestService().approveInternshipRequest(reqId);
	}

	/**
	 * reject internship
	 * @param reqId request id
	 */
	public void rejectInternship(String reqId) throws Exception {
		getRequestService().rejectInternshipRequest(reqId);
	}

	/**
	 * approve withdrawal of application
	 * @param reqId request id
	 */
	public void approveWithdrawal(String reqId) throws Exception {
        WithdrawalRequest request = (WithdrawalRequest) requestService.getRequest(reqId);
        internshipService.removeApplicationFromInternship(request.getApplication());
		requestService.acceptWithdrawalRequest(reqId);
	}

	/**
	 * reject withdrawal request
	 * @param reqId request id
	 */
	public void rejectWithdrawal(String reqId) throws Exception {
		getRequestService().rejectWithdrawalRequest(reqId);
	}

	/**
	 * get all filtered internship
	 * @param filter filter
	 */
	public List<InternshipOpportunity> viewInternshipsFiltered(FilterCriteria filter) {
		List<InternshipOpportunity> internships = repo.getAllInternships();
		return internshipService.getFilteredInternship(internships, filter);
	}

    /**
     *
     * @return pending registration request
     */
	public List<RegistrationRequest> viewPendingReg(){
		return getRequestService().getPendingRegistration();
	}

    /**
     *
     * @return pending internship request
     */
	public List<InternshipVetRequest> viewPendingInternshipVet(){
		return getRequestService().getPendingInternshipVet();
	}

    /**
     *
     * @return pending withdrawal request
     */
	public List<WithdrawalRequest> viewPendingWithdrawal(){
		return getRequestService().getPendingWithdrawal();
	}

    /**
     *
     * @return staff
     */
	public CareerStaff getStaff(){
		return this.staff;
	}

}