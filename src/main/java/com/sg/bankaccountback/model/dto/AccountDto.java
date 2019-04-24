package com.sg.bankaccountback.model.dto;

import com.sg.bankaccountback.model.Status;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountDto {
    private String accountNumber;
    private Date createdAt;
    private String clientName;
    private float balance;
    private Status status;
    public String getCreatedAt() {
        DateFormat formatD = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatD.format(this.createdAt);
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public float getBalance() {
        return balance;
    }

    public Status getStatus() {
        return status;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
