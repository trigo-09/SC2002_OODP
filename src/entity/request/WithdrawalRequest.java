package entity.request;

import controller.database.IResposistory;
import entity.application.Application;
import entity.application.ApplicationStatus;

public class WithdrawalRequest extends Request {

        private final Application application;
        private final String reason;

        public WithdrawalRequest(Application application, String reason, String requesterId) {
            super(requesterId);
            this.application = application;
            this.reason = reason;
        }

        public Application getApplication() {return application;}
        public String getReason() {return reason;}

        public void approve(IResposistory repo) {
            application.changeApplicationStatus(ApplicationStatus.WITHDRAWN);
            repo.findUser(super.getRequesterId()).addNotification("Withdrawal request approved");
        }

        public void reject(IResposistory repo) {
            repo.findUser(super.getRequesterId()).addNotification("Withdrawal request has been rejected");
        }

    }

