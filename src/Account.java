import java.util.*;
// my abstract class that represents the bank accounts
public abstract class Account {
    protected double balance;
    protected Notification notification;
    protected List<Transaction> transactions = new ArrayList<>();

    public void printTransactions() {
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}