package entity.application;
import entity.internship.InternshipOpportunity;
import java.io.Serializable;

import entity.user.Student;

public class Application implements Serializable {

	Student applicant;
	InternshipOpportunity opportunity;
	public String studentID;
	public String internshipID;
	public ApplicationStatus status;

	public void accept() {
		// TODO - implement Application.accept
		throw new UnsupportedOperationException();
	}

	public void withdraw() {
		// TODO - implement Application.withdraw
		throw new UnsupportedOperationException();
	}

}