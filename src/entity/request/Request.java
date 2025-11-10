package entity.request;

import entity.Displayable;

import java.io.Serializable;
import java.util.UUID;

public abstract class Request implements Serializable, Displayable {

    private final String requesterId;
    private final String requestId;

    public Request(String requestId) {
        this.requestId = UUID.randomUUID().toString();
        this.requesterId = requestId;
    }

    public String getId(){
        return this.requestId;
    }


    public String getRequesterId(){
        return this.requesterId;
    }


    public void approve(){
        System.out.println("please code");
    };

    public void reject(){
        System.out.println("please code");
    };

}
