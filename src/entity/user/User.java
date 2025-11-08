package entity.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Serializable {

	private String name;
	private String id;
	private String hashedPassword;
    private final List<Notification> notifications;

    /**
     *
     * @param name
     * @param id
     * @param pass
     */
    protected User(String name, String id, String pass) {
        this.name = name;
        this.id = id;
        this.hashedPassword = pass;
        notifications = new ArrayList<>();
    }


    public String getHashedPassword() {
		return this.hashedPassword;
	}

    /**
     *
     * @param pass
     */
    public void setHashedPassword(String pass) {
        this.hashedPassword = pass;
    }

	public String getUserName() {
		return this.name;
	}

    public String getId() {
        return this.id;
    }

    public void addNotification(String message) {
        notifications.add(new Notification(message));
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

//    public void showUnreadNotifications() {
//        for (Notification n : notifications) {
//            if (!n.isRead()) {
//                System.out.println(n);
//                n.markAsRead();
//            }
//        }
//    }
//          shld be under boundary

}
