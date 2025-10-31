package entity.request;
import entity.internship.InternBuilder;
import entity.internship.InternshipOpportunity;

import entity.user.Student;

import java.io.Serializable;

public class Application extends Request implements Serializable {

	private InternshipOpportunity opportunity;
	public String studentID;
	public String internshipID;

	public Application(String id,
					   Student student,   // student is the requester
					   InternshipOpportunity opportunity,
					   String studentID,
					   String internshipID){
		super(id, student, RequestStatus.PENDING);
		this.opportunity = opportunity;
		this.studentID = studentID;
		this.internshipID = internshipID;

	}

	public InternshipOpportunity getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(InternshipOpportunity opportunity) {
		this.opportunity = opportunity;
	}

	public String getStudentID(){
		return this.studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getInternshipID() {
		return internshipID;
	}

	public void setInternshipID(String internshipID) {
		this.internshipID = internshipID;
	}

}