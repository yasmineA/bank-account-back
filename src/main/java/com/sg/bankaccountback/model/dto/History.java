package com.sg.bankaccountback.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sg.bankaccountback.model.Transaction;
import org.modelmapper.ModelMapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class History {
    private String clientName;
    private String accountNumber;
    private float initialBalance;
    @JsonIgnore
    private Date createdAt;
    private ArrayList<Transaction> transactions;
    private float balance;
    private Date lastUpdate;

    public String getClientName() {
        return clientName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public float getInitialBalance() {
        return initialBalance;
    }

    public ArrayList<TransactionDto> getTransactions() {
        ArrayList<TransactionDto> transactionsDto = new ArrayList<>();
        for(Transaction transaction : this.transactions){
            TransactionDto transactionDto = new ModelMapper().map(transaction, TransactionDto.class);
            transactionsDto.add(transactionDto);
        }
        return transactionsDto;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setInitialBalance(float initialBalance) {
        this.initialBalance = 0;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public float getBalance() {
        return balance;
    }

    public String getCreatedAt() {
        DateFormat formatD = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatD.format(this.createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdate() {
        DateFormat formatD = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatD.format(this.lastUpdate);
    }

    @Override
    public String toString() {
        return "History{" +
                "clientName='" + clientName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", initialBalance=" + initialBalance +
                ", date='" + createdAt + '\'' +
                ", transactions=" + transactions +
                ", lastBalance=" + balance +
                ", lastUpdate='" + lastUpdate + '\'' +
                '}';
    }

}
