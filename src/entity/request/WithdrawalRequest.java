package entity.request;

import entity.application.Application;
import entity.user.User;

public class WithdrawalRequest extends Request {

        private final Application application;
        private final String reason;

        public WithdrawalRequest(Application application, String reason, User requester,String id) {
            super(id,requester);
            this.application = application;
            this.reason = reason;
        }

        public Application getApplication() {return application;}
        public String getReason() {return reason;}
    }

