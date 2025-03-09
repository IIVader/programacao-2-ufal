package atv2025_02_26.application;

import atv2025_02_26.model.entities.Account;
import atv2025_02_26.model.exceptions.InsufficientBalanceException;
import atv2025_02_26.model.exceptions.WithdrawalExceedsLimitException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Enter account data");

            System.out.print("Number: ");
            int accountNumber = scanner.nextInt();

            System.out.print("Holder: ");
            String accountHolder = scanner.next();

            System.out.print("Initial balance: ");
            double accountBalance = scanner.nextDouble();

            System.out.print("Withdraw limit: ");
            double accountWithdrawLimit = scanner.nextDouble();

            Account account = new Account(accountNumber, accountHolder, accountBalance, accountWithdrawLimit);

            System.out.println();

            System.out.print("Enter amount for withdraw: ");
            double amountWithdraw = scanner.nextInt();

            account.withdraw(amountWithdraw);

            System.out.println("New balance: " + account.getBalance());

        } catch (WithdrawalExceedsLimitException | InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
