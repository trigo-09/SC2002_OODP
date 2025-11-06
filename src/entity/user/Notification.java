package entity.user;


import java.io.Serializable;


public class Notification implements Serializable {
    private final String message;
    private boolean read = false;

    public Notification(String message) {
        this.message = message;
    }
    public Notification(){
        this("no message");
    }

    public String getMessage() { return message; }
    public boolean isRead() { return read; }

    public void markAsRead() { this.read = true; }


}
