package entity.request;

import entity.user.CompanyRep;
import entity.user.RepStatus;
import util.io.AsciiTableFormatter;

import java.util.List;

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
        List<AsciiTableFormatter.Row> rows = List.of(
                new AsciiTableFormatter.Row("Request Type", "Company Representative Registration"),
                new AsciiTableFormatter.Row("Request ID", getId()),
                new AsciiTableFormatter.Row("Requester ID", getRequesterId()),
                new AsciiTableFormatter.Row("Representative Name", companyRep.getUserName()),
                new AsciiTableFormatter.Row("Company Name", companyRep.getCompanyName()),
                new AsciiTableFormatter.Row("Current Status", String.valueOf(companyRep.getStatus()))
        );

        String table = AsciiTableFormatter.formatTable(rows);
        return table.replace(String.valueOf(companyRep.getStatus()), companyRep.getStatus().coloredString());
    }


}
