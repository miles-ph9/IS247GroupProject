// using inheritance to extend from account class
// java collections to keep the data together
// this will allow me to group the withdrawal reasons together and continuously sum the totals
import java.util.Map;
import java.util.HashMap;

//checking account will inhereit from the account and implment the deposit, withdrawal and balance
// features
public class CheckingAccount extends Account implements DepositAccount, WithdrawableAccount, BalanceCheck {
    // linking savings so the round-upd can be automatically transferred into the savings acct
    private SavingsAccount linkedSavings;
// constructor to initialize balance and notification system
public CheckingAccount(double initialBalance, Notification notification) throws MyCustomException {
    setBalance(initialBalance);
    setNotification(notification);
}

// link the checking account to the savings account for the round up process
    public void linkSavings(SavingsAccount savings) {
        this.linkedSavings = savings;
    }
// deposit the money into the checking account and have the user provide why to
    // add that into transaction history
    public void deposit(double amount, String reason) throws MyCustomException {
        if (amount <= 0) throw new MyCustomException("Deposit amount must be greater than zero.");
        setBalance(getBalance() + amount);
        addTransaction(new Transaction(amount, "Deposit", reason));
        getNotification().send(String.format("Deposited $%.2f. New Balance: $%.2f", amount, getBalance()));
    }

    // withdraw money from the checking account and allow the user to provide a why for the transaction
    // history and for the spending to later be categorized. It'll also flag any unusual spending for crypto
    public void withdraw(double amount, String reason) throws MyCustomException {
        if (amount <= 0) {
            throw new MyCustomException("Withdrawal amount must be greater than zero.");
        }
        if (amount > getBalance()) {
            throw new MyCustomException("Insufficient funds. Attempted to withdraw: " + amount);
        }
        setBalance(getBalance() - amount);
        addTransaction(new Transaction(amount, "Withdrawal", reason));
        getNotification().send(String.format("Withdrew $%.2f. Remaining Balance: $%.2f", amount, getBalance()));
        // this is where the user will be flagged for odd spending, for simplicity ill keep it at just
        // crypto and any amount more than $300
        if (amount >= 300 || reason.toLowerCase().contains("crypto")) {
            getNotification().send("[!Alert!] Unusual spending: $" + amount + " for: " + reason);
        }

        // round up savings. any spare change will then be transferred to the savings account, this is
        // one of the additonal features
        double roundUp = Math.round((Math.ceil(amount) - amount) * 100.0) / 100.0;
        if (roundUp > 0 && linkedSavings != null) {
            try {
                linkedSavings.deposit(roundUp, "Round-up from: " + reason);
                getNotification().send(String.format("Round-up savings of $%.2f transferred to savings.", roundUp));
            } catch (MyCustomException e) {
                getNotification().send("Round-up failed: " + e.getMessage());
            }
        }
    }
    // return the balance of the checking account
    public double getBalance() {
        return super.getBalance();
    }

    //print the spending with its amount and how much was withdrawn
    public void printSpendingByCategory() {
        //it'll be storted highest amount to lowest amount
        Map<String, Double> categoryTotals = new HashMap<>();
        for (Transaction transaction : getTransactions()){
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