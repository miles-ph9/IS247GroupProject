// java library usage to create the time stamps for the history selection
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// this will include the type of transaction, the reason and what date and time
public class Transaction {
    double amount;
    String type;
    String reason;
    LocalDateTime timestamp;

    public Transaction(double amount, String type, String reason) {
        this.amount = amount;
        this.type = type;
        this.reason = reason;
        this.timestamp = LocalDateTime.now();
    }
    // this is to format the date of the transaction
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy hh:mm a");
        return String.format("[%s] %s - $%.2f | Reason: %s", timestamp.format(formatter), type, amount, reason);
    }
}