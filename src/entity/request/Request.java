package entity.request;

import entity.Displayable;

import java.io.Serializable;
import java.util.UUID;

public abstract class Request implements Serializable, Displayable {

    private final String requesterId;
    private final String requestId;

    /**
     * constructor for request abstract class
     * @param requesterId
     */
    public Request(String requesterId) {
        this.requestId = UUID.randomUUID().toString();
        this.requesterId = requesterId;
    }

    /**
     *
     * @return id of request
     */
    public String getId(){
        return this.requestId;
    }

    /**
     *
     * @return id of requestor
     */
    public String getRequesterId(){
        return this.requesterId;
    }

    /**
     * abstract approve method
     */
    public abstract void approve();

    /**
     * abstract reject method
     */
    public abstract void reject();

}
