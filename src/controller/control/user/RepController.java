package controller.control.user;

import java.util.*;

import controller.database.IResposistory;
import controller.service.AuthenticationService;
import controller.service.RequestService;
import entity.internship.InternshipOpportunity;
import entity.user.CompanyRep;
import entity.application.Application;
import util.FilterCriteria;

public class RepController extends UserController {
	private CompanyRep rep;

    public RepController(AuthenticationService auth, IResposistory repo, RequestService request, CompanyRep rep) {
        super(auth, repo, request);
        this.rep = rep;
    }

    public void launch(){
        throw new  UnsupportedOperationException("Not supported yet.");
    }

	public InternshipOpportunity createInternship() {
		// TODO - implement RepController.createInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param internshipid
	 */
	public void toggleVisibility(String internshipid) {
		// TODO - implement RepController.toggleVisibility
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param internshipId
	 */
	public List<Application> getApplication(String internshipId) {
		// TODO - implement RepController.getApplication
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param appId
	 * @param internshipid
	 */
	public void approveApp(String appId, String internshipid) {
		// TODO - implement RepController.approveApp
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param appid
	 * @param internshipId
	 */
	public void rejectApp(String appid, String internshipId) {
		// TODO - implement RepController.rejectApp
		throw new UnsupportedOperationException();
	}

}