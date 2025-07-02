import java.util.*;
// my abstract class that represents the bank accounts
public abstract class Account {
    // i need each member to be accessible to the class and subclasses and
    // using encapsulation
    private double balance;
    private Notification notification;
    private List<Transaction> transactions = new ArrayList<>();

    // getter for balance
    public double getBalance() {
        return balance;
    }

    public void setBalance(double newBalance) throws MyCustomException {
        if (newBalance < 0) {
            throw new MyCustomException("Balance cannot be negative.");
        }
        this.balance = newBalance;
    }
    public Notification getNotification() {
        return this.notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
// method returns the transaction history into a list
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions); // Prevents external modification
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
// method overriding is used to print all transactions
    public void printTransactions() {
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }
}