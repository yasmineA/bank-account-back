package com.sg.bankaccountback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;

public class Account {
    @JsonIgnore
    private int id;
    private String accountNumber;
    private String clientName;
    private float balance;
    private Date lastUpdate;
    private Status status;
    private Date createdAt;
    private ArrayList<Transaction> transactions = new ArrayList();

    public Account(int id, String clientName, String accountNumber, float balance, Status status, Date createdAt) {
        this.id = id;
        this.clientName = clientName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.lastUpdate = new Date();
        this.transactions.add(new Transaction(new Date(), this.getId(), Operation.DEPOSIT, balance, balance));
    }

    public int getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Date getCreatedAt() {
        return  createdAt;
    }

    public String getClientName() {
        return clientName;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Status getStatus() {
        return status;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction transaction) {
        this.transactions.add(transaction);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", client=" + clientName +
                ", balance=" + balance +
                ", status=" + status +
                ", transactions=" + transactions +
                '}';
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }
}


