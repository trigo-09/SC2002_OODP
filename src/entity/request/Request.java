package entity.request;

import entity.user.User;

import java.io.Serializable;

public abstract class Request implements Serializable {

    private String id;
    private User requester;
    private RequestDecision status;

    public Request(String id, User requester){
        this.id = id;
        this.requester = requester;
        this.status = RequestDecision.PENDING;
    }

    public String getId(){
        return this.id;
    }


    public User getRequester(){
        return this.requester;
    }


    public RequestDecision getStatus(){
        return this.status;
    }

    public void approve(){
        this.status = RequestDecision.APPROVED;
    }
    public void reject(){
        this.status = RequestDecision.REJECTED;
    }

}
