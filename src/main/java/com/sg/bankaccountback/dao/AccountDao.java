package com.sg.bankaccountback.dao;

import com.sg.bankaccountback.exception.BadRequestException;
import com.sg.bankaccountback.exception.NotAllowedException;
import com.sg.bankaccountback.exception.NotFoundException;
import com.sg.bankaccountback.model.*;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
This class manage bank Account and Transaction
* */
@Repository
public class AccountDao {
    public static ArrayList<Account> accounts = new ArrayList<>();

    static {
        try {
            Date createdAt = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("01/02/2019 10:03");
            accounts.add(new Account(1, "MM Y A", "30003 01199 11150000182 66", 100, Status.BLOCKED, createdAt));
            accounts.add(new Account(2, "MR X x", "31113 01199 00050333182 66", 300, Status.ACTIVE, createdAt));
            accounts.add(new Account(3, "MR Y Y", "32223 01199 00050000182 77", 10, Status.ACTIVE, createdAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /*
    This function deposit funds in account : create a new transaction and set the balance
    @param account the bank account
    @param amount the amount to depose in the account
    @throws BadRequestException if amount < 0
    @throws NotFoundException if account is null
    * */
    public void deposit(Account account, float amount) {
        if(account!=null){
            if(amount>0){
                addTransaction(account, Operation.DEPOSIT, amount);
                account.setBalance(account.getBalance()+amount);
                account.setLastUpdate(new Date());
            }
            else
                throw new BadRequestException("INVALID AMOUNT");
        }
        else
            throw new NotFoundException("NULL ACCOUNT");
    }

    /*
    This function withdraws funds from account : create a new transaction and set the balance
    @param account the bank account
    @param amount the amount to withdraw in the account
    @throws BadRequestException if amount > balance or amount<=0
    @throws NotFoundException if account is null
    * */
    public void withdraw(Account account, float amount, Operation type) {
        if(account!=null) {
            if (account.getStatus()!=Status.ACTIVE)
                throw new NotAllowedException(account.getStatus()+" ACCOUNT");

            float amountToRetrieve = amount;
            if (type.equals(Operation.WITHDRAWAL_ALL)) amountToRetrieve = account.getBalance();

            if(amountToRetrieve<=0)
                throw new BadRequestException("INVALID AMOUNT");

            if(amountToRetrieve>account.getBalance())
                throw new BadRequestException("NOT ENOUGH FUNDS");

            if (amountToRetrieve <= account.getBalance()) {
                addTransaction(account, Operation.WITHDRAWAL, amountToRetrieve);
                account.setBalance(account.getBalance() - amountToRetrieve);
                account.setLastUpdate(new Date());
            }
        }
        else
            throw  new BadRequestException("NULL ACCOUNT");
    }

    /*
    This function search account by id
    @param id and account's id
    @return Account if it exits otherwise return null
    * */
    public Account getAccountById(int id) {
        for (Account account : accounts) {
            if (account.getId() == id) {
                return account;
            }
        }
        return null;
    }

    /*
    This function create a transaction depends on its type
    @param account the bank account
    @param operation the bank operation (DEPOSIT or WITHDRAWAL)
    @throws NotFoundException if account is null
    @throws NotAllowedException if the account is BLOCKED or CLOSED
    * */
    public void addTransaction(Account account, Operation operation, float amount){
        if(account!=null){
            Transaction transaction = null;
            if(operation == Operation.DEPOSIT)
                transaction = new Transaction(new Date(), account.getId(), operation, amount, amount+account.getBalance());
            else if(operation == Operation.WITHDRAWAL){
                if (account.getStatus()!=Status.ACTIVE)
                    throw new NotAllowedException(account.getStatus()+" ACCOUNT");
                transaction = new Transaction(new Date(), account.getId(), operation, amount, account.getBalance()-amount);
            }
            account.setTransactions(transaction);
        }
        else
            throw new NotFoundException("NULL ACCOUNT");
    }

}


