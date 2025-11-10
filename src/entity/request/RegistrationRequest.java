package entity.request;

import entity.user.CompanyRep;
import entity.user.RepStatus;

public class RegistrationRequest extends Request {
    private final CompanyRep companyRep;
   public RegistrationRequest(CompanyRep companyRep) {
       super(companyRep.getId());
       this.companyRep = companyRep;
   }

   public void approve() {
       companyRep.setStatus(RepStatus.REGISTERED);
       companyRep.addNotification("Account has been approved");
   }
   public void reject() {
       companyRep.addNotification("Account has been rejected");
   }
}
