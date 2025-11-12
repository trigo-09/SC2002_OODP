package controller.database;

import entity.request.*;
import java.util.List;

/**
 * contain methods related to request for repository to implement
 */
public interface IRequestRepo {

    /**
     * get all request
     * able to decide which type of request due to use of generics
     * @param type request
     * @return
     * @param <T> type of request class
     */
    <T extends Request> List<T> getAllRequests(Class<T> type);

    /**
     * get request using its ID
     * @param requestId request id
     */
    Request getRequest(String requestId);

    /**
     * add withdrawal request to request map
     * @param withdrawalRequest request object
     */
   void addWithdrawalRequest(WithdrawalRequest withdrawalRequest);

    /**
     * remove withdrawal request from map
     * @param requestId
     */
     void removeWithdrawalRequest(String requestId);

    /**
     * add registration request to request map
     * @param registrationRequest
     */
     void addRegistrationRequest(RegistrationRequest registrationRequest);

    /**
     * remove registration request from request map
     * @param requestId
     */
    void removeRegistrationRequest(String requestId);

    /**
     * add internship request to request map
     * @param internshipVetRequest
     */
    void addInternshipVetRequest(InternshipVetRequest internshipVetRequest);

    /**
     * remove internship request from request map
     * @param requestId
     */
     void removeInternshipVetRequest(String requestId);
}
