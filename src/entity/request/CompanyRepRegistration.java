package entity.request;

import entity.user.CompanyRep;

import java.io.Serializable;

public class CompanyRepRegistration extends Request implements Serializable {

    public CompanyRepRegistration(String id,
                                         CompanyRep companyRep) {
        super(id, companyRep,               // requester is the rep
                RequestStatus.PENDING);
    }

}
