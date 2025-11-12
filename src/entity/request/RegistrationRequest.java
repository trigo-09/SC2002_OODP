package entity.request;

import entity.user.CompanyRep;
import entity.user.RepStatus;

public class RegistrationRequest extends Request {
    private final CompanyRep companyRep;

    /**
     * constructor of RegistrationRequest class
     * @param companyRep
     */
   public RegistrationRequest(CompanyRep companyRep) {
       super(companyRep.getId());
       this.companyRep = companyRep;
   }

    /**
     * once approved, company rep status set to registered
     */
   public void approve() {
       companyRep.setStatus(RepStatus.REGISTERED);
   }

    /**
     * company rep account to be set rejected
     */
   public void reject() {
       companyRep.setStatus(RepStatus.REJECTED);
   }

    /**
     *
     * @return company rep account
     */
   public CompanyRep getCompanyRep() {
       return companyRep;
   }


    /**
     *
     * @return string for displaying purpose
     */
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
