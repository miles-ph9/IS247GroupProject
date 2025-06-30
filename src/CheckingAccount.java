// java collections to keep the data together
// this will allow me to group the withdrawal reasons together and continuously sum the totals
import java.util.Map;
import java.util.HashMap;

public class CheckingAccount extends Account implements DepositAccount, WithdrawableAccount, BalanceCheck {
    private SavingsAccount linkedSavings;

    public CheckingAccount(double initialBalance, Notification notification) {
        this.balance = initialBalance;
        this.notification = notification;
    }

    public void linkSavings(SavingsAccount savings) {
        this.linkedSavings = savings;
    }

    public void deposit(double amount, String reason) throws MyCustomException {
        if (amount <= 0) throw new MyCustomException("Deposit amount must be greater than zero.");
        balance += amount;
        transactions.add(new Transaction(amount, "Deposit", reason));
        notification.send(String.format("Deposited $%.2f. New Balance: $%.2f", amount, balance));
    }
    public void withdraw(double amount, String reason) throws MyCustomException {
        if (amount > balance) throw new MyCustomException("Insufficient funds. Attempted to withdraw: " + amount);
        balance -= amount;
        transactions.add(new Transaction(amount, "Withdrawal", reason));
        notification.send(String.format("Withdrew $%.2f. Remaining Balance: $%.2f", amount, balance));

        // this is where the user will be flagged for odd spending, for simplicity ill keep it at just
        // crypto and any amount more than $300
        if (amount >= 300 || reason.toLowerCase().contains("crypto")) {
            notification.send("[!Alert!] Unusual spending: $" + amount + " for: crypto");
        }

        // round up savings. any spare change will then be transferred to the savings account, this is
        // one of the additonal features
        double roundUp = Math.round((Math.ceil(amount) - amount) * 100.0) / 100.0;
        if (roundUp > 0 && linkedSavings != null) {
            try {
                linkedSavings.deposit(roundUp, "Round-up from: " + reason);
                notification.send(String.format("Round-up savings of $%.2f transferred to savings.", roundUp));
            } catch (MyCustomException e) {
                notification.send("Round-up failed: " + e.getMessage());
            }
        }
    }
    // return the balance
    public double getBalance() {
        return balance;
    }

    //print the spending with its amount and how much was withdrawn
    public void printSpendingByCategory() {
        //
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Transaction transaction : transactions) {
            if (transaction.type.equalsIgnoreCase("Withdrawal")) {
                categoryTotals.put(transaction.reason, categoryTotals.getOrDefault(transaction.reason, 0.0) + transaction.amount);
            }
        }

// sorts the reasons from the highest amount spent to the lowest, includes the API
        System.out.println("\n--- Spending by Category ---");
        categoryTotals.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue()))
                .forEach(entry -> System.out.printf("%s: $%.2f%n", entry.getKey(), entry.getValue()));
    }
}