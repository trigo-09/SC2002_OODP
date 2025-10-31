package entity.application;

import entity.user.Student;

public class WithdrawalRequest {

    private final Student student;
    private final Application application;
    private final String reason;
    private WithdrawalDecision decision;

    public WithdrawalRequest(Application application, Student student, String reason) {
        this.student = student;
        this.application = application;
        this.reason = reason;
    }

    public Student getStudent() {return student;}
    public Application getApplication() {return application;}
    public String getReason() {return reason;}
    public WithdrawalDecision getDecision() { return decision; }
    public void setDecision(WithdrawalDecision decision) { this.decision = decision; }
}