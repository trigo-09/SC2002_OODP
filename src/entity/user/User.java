package entity.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Serializable {

	private String name;
	private final String id;
	private String hashedPassword;
    private final UserRole role;
    private final List<Notification> notifications;

    /**
     *
     * @param name
     * @param id
     * @param pass
     */
    public User(String name, String id, String pass, UserRole role) {
        this.name = name;
        this.id = id;
        this.hashedPassword = pass;
        this.role = role;
        notifications = new ArrayList<>();
    }


    public String getHashedPassword() {
		return this.hashedPassword;
	}

    public UserRole getRole() {
        return this.role;
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
