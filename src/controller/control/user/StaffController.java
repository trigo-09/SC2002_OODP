package controller.control.user;

import java.util.*;

import controller.database.IRepository;
import controller.service.AuthenticationService;
import controller.service.RequestService;
import entity.internship.InternshipOpportunity;
import entity.user.CareerStaff;
import util.FilterCriteria;

public class StaffController extends UserController {

	private CareerStaff staff;
    public StaffController(AuthenticationService authenticationService, IRepository repo, RequestService request, CareerStaff staff) {
        super(authenticationService,repo,request);
        this.staff = staff;
    }

    public void launch(){
        throw new  UnsupportedOperationException("Not supported yet.");
    }

	/**
	 * 
	 * @param rep
	 */
	public void approveRep(String rep) {
		// TODO - implement StaffController.approveRep
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param rep
	 */
	public void rejectRep(String rep) {
		// TODO - implement StaffController.rejectRep
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param intern
	 */
	public void approveInternship(String intern) {
		// TODO - implement StaffController.approveInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param intern
	 */
	public void rejectInternship(String intern) {
		// TODO - implement StaffController.rejectInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param app
	 */
	public void approveWithdrawal(String app) {
		// TODO - implement StaffController.approveWithdrawal
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param app
	 */
	public void rejectWithdrawal(String app) {
		// TODO - implement StaffController.rejectWithdrawal
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param filter
	 */
	public List<InternshipOpportunity> generateReport(FilterCriteria filter) {
		// TODO - implement StaffController.generateReport
		throw new UnsupportedOperationException();
	}

}