//  interface that allows for the notifications, this is the open/closed principle
public interface Notification {
    // using an abstract method to send a notification
    void send(String message);
}
