public class SavingsAccount extends Account implements DepositAccount, BalanceCheck {
    // my constructor to create the savings account with the balance of 0
    public SavingsAccount(double initialBalance, Notification notification) {
        this.balance = 0;
        this.notification = notification;
    }
    // here this will deposit the money into the savings account and makes sure its a positive amount
    // it'll take it in as a transaction and it'll send out a notification to let the user know about the transaction
    public void deposit(double amount, String reason) throws MyCustomException {
        if (amount <= 0) {
            throw new MyCustomException("The deposit amount must be greater than 0!");
        }
        // adds the money to the balnce of whats in the account
        balance += amount;
        transactions.add(new Transaction(amount, "Deposit", reason));
        notification.send(String.format("Deposited $%.2f. New Balance: $%.2f", amount, balance));
    }
    // returns the current balance
    public double getBalance() {
        return balance;
    }
}
