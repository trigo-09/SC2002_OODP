package entity.request;

import controller.database.IRepository;
import entity.application.Application;
import entity.application.ApplicationStatus;

public class WithdrawalRequest extends Request {

        private final Application application;
        private final String reason;

    /**
     * Constructor for withdrawal request
     * @param application
     * @param reason
     * @param requesterId
     */
        public WithdrawalRequest(Application application, String reason, String requesterId) {
            super(requesterId);
            this.application = application;
            this.reason = reason;
        }

    /**
     *
     * @return application
     */
    public Application getApplication() {return application;}

    /**
     *
     * @return reason for request
     */
    public String getReason() {return reason;}

    /**
     * change application to withdraw once approved
     */
    public void approve() {
        application.changeApplicationStatus(ApplicationStatus.WITHDRAWN);
    }

    public void reject() {}


    /**
     *
     * @return string for displaying
     */
    @Override
    public String toString() {
        return String.format(
                "Request Type: Withdrawal%n" +
                        "Request ID: %s%n" +
                        "Requester ID: %s%n" +
                        "Reason: %s%n" +
                        "Application ID: %s%n" +
                        "Current Application Status: %s%n",
                getId(),
                getRequesterId(),
                getReason(),
                application.getApplicationId(),
                application.getStatus()
        );
    }

}

