package com.sg.bankaccountback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

public class Transaction {
    private Date transactionDate;
    @JsonIgnore
    private int accountOwnerId;
    private Operation operation;
    private float amount;
    @JsonIgnore
    private float balance;

    public Transaction(Date transactionDate, int accountOwnerId, Operation operation, float amount, float balance) {
        this.transactionDate = transactionDate;
        this.accountOwnerId = accountOwnerId;
        this.operation = operation;
        this.amount = amount;
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public float getAmount() {
        return amount;
    }


    public Operation getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionDate=" + transactionDate +
                ", accountOwnerId=" + accountOwnerId +
                ", operation=" + operation +
                ", amount=" + amount +
                '}';
    }


}
