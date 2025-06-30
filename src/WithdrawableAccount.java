// interface to ensure withdrawals can be done from any class
public interface WithdrawableAccount {
    void withdraw(double amount, String reason) throws MyCustomException;
}