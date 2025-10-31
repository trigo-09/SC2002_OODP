package entity.request;

import entity.internship.InternshipOpportunity;
import entity.user.Student;

import java.io.Serializable;

public class Withdrawal extends Request implements Serializable {

    private InternshipOpportunity opportunity;
    public String studentID;
    public String internshipID;
    public Application app;

    public Withdrawal(String id,
                       Student student,              // student is the requester
                       InternshipOpportunity opportunity,
                       String studentID,
                       String internshipID,
                      Application app){
        super(id, student, RequestStatus.PENDING);
        this.opportunity = opportunity;
        this.studentID = studentID;
        this.internshipID = internshipID;
        this.app = app;

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

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

}
