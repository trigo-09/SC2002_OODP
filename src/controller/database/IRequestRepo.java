package controller.database;

import entity.request.*;

import java.util.List;

public interface IRequestRepo {

    public <T extends Request> List<T> getAllRequests(Class<T> type);

    /**
     *
     * @param requestId
     *
     */
    Request getRequest(String requestId);

    /**
     *
     * @param withdrawalRequest
     */
    public void addWithdrawalRequest(WithdrawalRequest withdrawalRequest);

    /**
     *
     * @param requestId
     */
    public void removeWithdrawalRequest(String requestId);

    /**
     *
     * @param registrationRequest
     */
    public void addRegistrationRequest(RegistrationRequest registrationRequest);

    /**
     *
     * @param requestId
     */
    public void removeRegistrationRequest(String requestId);

    /**
     *
     * @param internshipVetRequest
     */
    public void addInternshipVetRequest(InternshipVetRequest internshipVetRequest);

    /**
     *
     * @param requestId
     */
    public void removeInternshipVetRequest(String requestId);
}
