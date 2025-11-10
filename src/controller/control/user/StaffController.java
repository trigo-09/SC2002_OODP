package controller.control.user;

import java.util.*;

import controller.service.AuthenticationService;
import controller.service.InternshipService;
import controller.service.RequestService;
import entity.internship.InternshipOpportunity;
import entity.user.CareerStaff;
import util.FilterCriteria;

public class StaffController extends UserController {

	private CareerStaff staff;
	private final FilterCriteria filter;
	private final RequestService requestService;
	private final InternshipService internshipService;

	public StaffController(FilterCriteria filter, AuthenticationService auth,RequestService requestService,
						   InternshipService internshipService, CareerStaff staff){
		super(auth);
		this.staff = staff;
		this.filter = filter;
		this.requestService = requestService;
		this.internshipService = internshipService;
	}

	/**
	 * 
	 * @param rep
	 */
	public void approveRep(String rep) {
		requestService.approveRegistrationRequest(rep);
	}

	/**
	 * 
	 * @param rep
	 */
	public void rejectRep(String rep) {
		requestService.rejectRegistrationRequest(rep);
	}

	/**
	 * 
	 * @param intern
	 */
	public void approveInternship(String intern) {
		requestService.approveInternshipRequest(intern);
	}

	/**
	 * 
	 * @param intern
	 */
	public void rejectInternship(String intern) {
		requestService.rejectInternshipRequest(intern);
	}

	/**
	 * 
	 * @param app
	 */
	public void approveWithdrawal(String app) {
		requestService.acceptWithdrawalRequest(app);
	}

	/**
	 * 
	 * @param app
	 */
	public void rejectWithdrawal(String app) {
		requestService.rejectWithdrawalRequest(app);
	}

	/**
	 * 
	 * @param filter
	 */
	public List<InternshipOpportunity> generateReport(FilterCriteria filter) {
		return internshipService.getFilteredInternship(filter);
	}

}