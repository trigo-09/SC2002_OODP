package controller.control.user;

import java.util.*;

import controller.service.AuthenticationService;
import entity.application.Application;
import entity.user.Student;
import entity.internship.InternshipOpportunity;
import util.FilterCriteria;

public class StudentController extends UserController {

	private Student student;
	private FilterCriteria filter;

    public StudentController(FilterCriteria filter, AuthenticationService auth, Student student) {
        super(auth);
        this.filter = filter;
        this.student = student;
    }

	public List<InternshipOpportunity> ableToApply() {
		// TODO - implement StudentController.ableToApply
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param internshipId
	 */
	public void applyInternship(String internshipId) {
		// TODO - implement StudentController.applyInternship
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param applicationId
	 */
	public void acceptPlacement(String applicationId) {
		// TODO - implement StudentController.acceptPlacement
		throw new UnsupportedOperationException();
	}

	public List<Application> myApplications() {
		// TODO - implement StudentController.myApps
		throw new UnsupportedOperationException();
	}

}