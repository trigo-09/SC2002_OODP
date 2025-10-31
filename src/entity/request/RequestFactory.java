package entity.request;

import entity.internship.InternshipOpportunity;
import entity.user.CompanyRep;
import entity.user.Student;

public class RequestFactory {
    public static CompanyRepRegistration createRegistration(String id, CompanyRep rep){
        return new CompanyRepRegistration(id, rep); /* this rep should be a newly created one with repstatus set to pending
        , either create here or before calling
        this method */
    }

    public static Application createApplication(String id,
                                                Student student,
                                                InternshipOpportunity opportunity,
                                                String studentID,
                                                String internshipID){
        return new Application(id, student, opportunity, studentID, internshipID);
    }

    public static Withdrawal createWithdrawal(String id,
                                              Student student,
                                              InternshipOpportunity opportunity,
                                              String studentID,
                                              String internshipID,
                                              Application app){
        return new Withdrawal(id, student, opportunity, studentID, internshipID, app);
    }
}
