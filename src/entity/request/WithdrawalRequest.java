package entity.request;

import entity.application.Application;

public class WithdrawalRequest {

        private final Application application;
        private final String reason;
        private WithdrawalDecision decision;

        public WithdrawalRequest(Application application, String reason) {
            this.application = application;
            this.reason = reason;
            this.decision = WithdrawalDecision.PENDING;
        }

        public Application getApplication() {return application;}
        public String getReason() {return reason;}
        public WithdrawalDecision getDecision() { return decision; }
        public void setDecision(WithdrawalDecision decision) { this.decision = decision; }
        public String getStudentId() { return application.getStudentId(); }
    }

