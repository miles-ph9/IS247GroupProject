
import java.util.List;

public class TransactionGenerics {
    // generic method to print any list of transactions or subclass of Transaction
    public static <T extends Transaction> void printTransactionList(List<T> list) {
        for (T t : list) {
            System.out.println(t);
        }
    }

    //  generic method to find total amount of any transaction list
    public static <T extends Transaction> double calculateTotalAmount(List<T> list) {
        double total = 0;
        for (T t : list) {
            total += t.amount;
        }
        return total;
    }
}
