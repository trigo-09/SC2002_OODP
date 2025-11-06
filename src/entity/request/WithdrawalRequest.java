package entity.request;

import entity.application.Application;

public class WithdrawalRequest {

        private final Application application;
        private final String reason;
        private RequestDecision decision;

        public WithdrawalRequest(Application application, String reason) {
            this.application = application;
            this.reason = reason;
            this.decision = RequestDecision.PENDING;
        }

        public Application getApplication() {return application;}
        public String getReason() {return reason;}
        public RequestDecision getDecision() { return decision; }
        public void setDecision(RequestDecision decision) { this.decision = decision; } //rmbr to call student withdraw if accepted
        public String getStudentId() { return application.getStudentId(); }
    }

