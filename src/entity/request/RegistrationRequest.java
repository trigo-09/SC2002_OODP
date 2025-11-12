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
   }
   public void reject() {
       companyRep.setStatus(RepStatus.REJECTED);
   }

   public CompanyRep getCompanyRep() {
       return companyRep;
   }


    @Override
    public String toString() {
        return String.format(
                "Request Type: Company Representative Registration%n" +
                        "Request ID: %s%n" +
                        "Requester ID: %s%n" +
                        "Representative Name: %s%n" +
                        "Company Name: %s%n" +
                        "Current Status: %s%n",
                getId(),
                getRequesterId(),
                companyRep.getUserName(),
                companyRep.getCompanyName(),
                companyRep.getStatus()
        );
    }

}
