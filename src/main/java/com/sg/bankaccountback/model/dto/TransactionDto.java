package com.sg.bankaccountback.model.dto;

import com.sg.bankaccountback.model.Operation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransactionDto {
    private Date transactionDate;
    private Operation operation;
    private float amount;

    public String getTransactionDate() {
        DateFormat formatD = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatD.format(this.transactionDate);
    }

    public float getAmount() {
        return amount;
    }


    public Operation getOperation() {
        return operation;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
