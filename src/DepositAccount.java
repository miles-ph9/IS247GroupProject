// interface that creates the deposit account
public interface DepositAccount {
    // this method is to deposit an amount into the account with a reason and if the
    //amount is invalid, it'll throw an exception error
    void deposit(double amount, String reason) throws MyCustomException;
}