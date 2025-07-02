// this file will send notifications out in an email format similar to the one from lab2.

public class EmailNotification implements Notification {
    // overrides the send method and prints the notification as an email to the user
    @Override
    public void send(String message) {
        System.out.println("[Email] " + message);
    }
}