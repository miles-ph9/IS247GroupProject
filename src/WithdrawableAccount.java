// interface to ensure withdrawals can be done from any class
public interface WithdrawableAccount {
    //same method as the deposit account, it'll allow withdrawals and exception for
    // when there's insufficient funds
    void withdraw(double amount, String reason) throws MyCustomException;
}