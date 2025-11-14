package entity.request;

import controller.database.IRepository;
import entity.application.Application;
import entity.application.ApplicationStatus;
import util.io.AsciiTableFormatter;

import java.util.List;

/**
 * Represents a withdrawal request made for a specific application.
 * allows a student to submit a reason for withdrawing their application.
 * Once approved, the application status is updated to 'WITHDRAWN'.
 */
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
        String placeholder = "#".repeat(String.valueOf(application.getStatus()).length());
        List<AsciiTableFormatter.Row> rows = List.of(
                new AsciiTableFormatter.Row("Request Type", "Withdrawal"),
                new AsciiTableFormatter.Row("Request ID", getId()),
                new AsciiTableFormatter.Row("Requester ID", getRequesterId()),
                new AsciiTableFormatter.Row("Reason", reason),
                new AsciiTableFormatter.Row("Application ID", application.getApplicationId()),
                new AsciiTableFormatter.Row("Application Status", placeholder)
        );

        String table = AsciiTableFormatter.formatTable(rows);
        return table.replace(placeholder, application.getStatus().coloredString());
    }


}

