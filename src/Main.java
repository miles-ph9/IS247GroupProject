//Group 1 Budgeting and Banking Application
// This code was combined using lab 1 and lab 2.
// There are several additional features implemented.
// 1. Users will be able to choose if they deposit into their savings or checking account.
// 2. Users will be able to round up excess amounts from withdrawals and have that added to their savings
// 3. The history feature will include time stamps so the user is aware of when they completed that transaction
// 4. When users withdraw, they can give a reason why. This reason will then be created into a category which
//      the user will later be able to see using the 's' to show spending summary.
// 5. The show spending summary will then categorize the withdrawal reasons and list them from highest to lowest amounts
// 6. The user will also receive a notification when there's unusual transactions.


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Notification emailNotification = new EmailNotification();

        SavingsAccount savings = new SavingsAccount(0, emailNotification);
        CheckingAccount checking = new CheckingAccount(500, emailNotification);
        checking.linkSavings(savings);

        while (true) {
            System.out.println("\nWelcome to the Bank!");
            System.out.println("\n=== Banking Menu ===");
            System.out.println("Enter 'd' to deposit, 'w' to withdraw, 'h' to view history, 's' to show spending summary, or 'q' to quit:");
            String choice = scanner.next();

            if (choice.equalsIgnoreCase("q")) {
                System.out.println("Exiting program.");
                System.out.println("Checking Balance: $" + checking.getBalance());
                System.out.println("Savings Balance: $" + savings.getBalance());
                break;
            }

            try {
                if (choice.equalsIgnoreCase("d")) {
                    System.out.print("Deposit to checking or savings? Please enter 'c' or 's': ");
                    String target = scanner.next();

                    System.out.print("Enter deposit amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();

                    String defaultReason = "Deposit";

                    if (target.equalsIgnoreCase("c")) {
                        checking.deposit(amount, defaultReason);
                    } else if (target.equalsIgnoreCase("s")) {
                        savings.deposit(amount, defaultReason);
                    } else {
                        System.out.println("Invalid account type. Please enter 'c' or 's'.");
                    }

                } else if (choice.equalsIgnoreCase("w")) {
                    System.out.print("Enter withdrawal amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter reason for withdrawal (shopping, bills, food, transportation, entertainment): ");
                    String reason = scanner.nextLine();
                    checking.withdraw(amount, reason);
                } else if (choice.equalsIgnoreCase("h")) {
                    System.out.println("\n--- Checking Account Transactions ---");
                    checking.printTransactions();
                    System.out.println("\n--- Savings Account Transactions ---");
                    savings.printTransactions();
                } else if (choice.equalsIgnoreCase("s")) {
                    checking.printSpendingByCategory();
                }
            } catch (MyCustomException e) {
                System.out.println("Banking Exception: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
