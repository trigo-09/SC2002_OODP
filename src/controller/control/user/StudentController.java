package controller.control.user;

import java.util.*;

import controller.database.IRepository;
import controller.service.AuthenticationService;
import controller.service.RequestService;
import entity.application.Application;
import entity.user.Student;
import entity.internship.InternshipOpportunity;


public class StudentController extends UserController {

	private Student student;

    public StudentController(AuthenticationService auth, IRepository repo, RequestService request, Student student) {
        super(auth, repo,request);
        this.student = student;
    }

    public void launch(){
        throw new  UnsupportedOperationException("Not supported yet.");
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