package entity.request;

import entity.user.User;

import java.io.Serializable;

public abstract class Request implements Serializable {

    private String requesterId;
    private String requestId;

    public Request(String requestId) {
        this.requestId = UUID.randomUUID().toString();
        this.requesterId = requestId;
    }

    public String getId(){
        return this.requestId;
    }


    public User getRequester(){
        return this.requester;
    }


    public void approve(){
        System.out.println("please code");
    };

    public void reject(){
        System.out.println("please code");
    };

}
