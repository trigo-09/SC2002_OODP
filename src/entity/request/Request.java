package entity.request;

import entity.user.User;

import java.io.Serializable;

public abstract class Request implements Serializable {

    private String id;
    private User requester;
//    private RequestStatus status;    // i may or may not use the abstract i just KIV for now;) -mega

    public Request(String id, User requester){
        this.id = id;
        this.requester = requester;
//        this.status = status;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public User getRequester(){
        return this.requester;
    }

    public void setRequester(User requester){
        this.requester = requester;
    }
//
//    public RequestStatus getStatus(){
//        return this.status;
//    }
//
//    public void setStatus(RequestStatus status){
//        this.status = status;
//    }

}
