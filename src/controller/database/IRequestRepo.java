package controller.database;

import entity.request.*;
import entity.user.CompanyRep;

import java.util.List;

/**
 * contain methods related to request for repository to implement
 */
public interface IRequestRepo extends IUser{

    /**
     * get all request
     * able to decide which type of request due to use of generics
     * @param type request
     * @return list of request type class or its child
     * @param <T> type of request class
     */
    <T extends Request> List<T> getAllRequests(Class<T> type);

    /**
     * get request using its ID
     * @param requestId request id
     */
    Request getRequest(String requestId);

    /**
     * add request object to the request map
     * @param request Request object
     */
    void addRequest(Request request);

    /**
     * removed request from repo using it ID.
     * @param requestId request id
     */
    void removeRequest(String requestId);

}
