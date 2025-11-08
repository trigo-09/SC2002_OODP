package entity.request;

import entity.user.CompanyRep;
import entity.user.RepStatus;

public class RegistrationRequest extends Request {
    private final CompanyRep company;
   public RegistrationRequest(CompanyRep companyRep) {
       super(companyRep.getId());
       this.company = companyRep;
   }

   public void approve() {
       company.setStatus(RepStatus.REGISTERED);
       company.addNotification("Account has been approved");
   }
   public void reject() {
       company.addNotification("Account has been rejected");
   }
}
