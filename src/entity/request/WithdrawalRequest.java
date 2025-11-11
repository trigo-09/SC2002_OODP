package entity.request;

import controller.database.IRepository;
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

        public void approve() {
            application.changeApplicationStatus(ApplicationStatus.WITHDRAWN);
        }


    @Override
    public String getSplitter() {
        return "----------------------------------";
    }

    @Override
    public String getString() {
        return String.format(
                "Request Type: Withdrawal%n" +
                        "Request ID: %s%n" +
                        "Requester ID: %s%n" +
                        "Application ID: %s%n" +
                        "Current Application Status: %s%n",
                getId(),
                getRequesterId(),
                application.getApplicationId(),
                application.getStatus()
        );
    }

}

