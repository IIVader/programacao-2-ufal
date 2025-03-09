package atv2025_02_26.model.entities;

import atv2025_02_26.model.exceptions.InsufficientBalanceException;
import atv2025_02_26.model.exceptions.WithdrawalExceedsLimitException;

public class Account {

    private final int number;
    private final String holder;
    private double balance;
    private double withdrawLimit;

    public Account(int number, String holder, double balance, double withdrawLimit) {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.withdrawLimit = withdrawLimit;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) throws WithdrawalExceedsLimitException, InsufficientBalanceException {

        if(amount > this.withdrawLimit) {
            throw new WithdrawalExceedsLimitException("Withdraw error: The amount exceeds withdraw limit.");
        } else {
            this.balance -= amount;
        }

        if(amount > this.balance) throw new InsufficientBalanceException("Withdraw error: Not enough balance.");
    }

    public int getNumber() {
        return number;
    }

    public String getHolder() {
        return holder;
    }

    public double getBalance() {
        return balance;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }
}
