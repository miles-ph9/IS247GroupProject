public interface DepositAccount {
    void deposit(double amount, String reason) throws MyCustomException;
}