package entity.request;

import entity.user.CompanyRep;
import entity.user.RepStatus;
import entity.user.User;

public class RegistrationRequest extends Request {
    private final CompanyRep company;
   public RegistrationRequest(CompanyRep companyRep) {
       super(companyRep.getId());
       this.company = companyRep;

   }

   public void approve() {
       this.status = RequestDecision.APPROVED;
       company.setStatus(RepStatus.REGISTERED);
   }
   public void reject() {
       this.status = RequestDecision.REJECTED;
   }
}
