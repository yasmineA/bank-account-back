package com.sg.bankaccountback.service;

import com.sg.bankaccountback.dao.AccountDao;
import com.sg.bankaccountback.dao.BalanceRepo;
import com.sg.bankaccountback.exception.BadRequestException;
import com.sg.bankaccountback.exception.NotAllowedException;
import com.sg.bankaccountback.exception.NotFoundException;
import com.sg.bankaccountback.model.Account;
import com.sg.bankaccountback.model.Balance;
import com.sg.bankaccountback.model.Operation;
import com.sg.bankaccountback.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountService {
    @Autowired
    AccountDao accountDao;

   /* @Autowired
    BalanceRepo balanceRepo;*/

    /*
    This function check if an operation is an operation of Withdrawal
    @param operation
    @return True if it is and False otherwise
    * */
    public Boolean checkWithdrawalOperation(Operation operation) {
        if ( operation != Operation.WITHDRAWAL && operation != Operation.WITHDRAWAL_ALL)
            return false;
        return true;
    }

    /*
    This function deposit funds in account : create a new transaction and set the balance
    @param account the bank account
    @param amount the amount to depose in the account
    @throws BadRequestException if amount < 0
    @throws NotFoundException if account is null
    * */
    public void deposit(int id, float amount) {
        Account account =accountDao.getAccountById(id);
        if(account!=null){
            if(amount>0){
                accountDao.deposit(account, amount);
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
    public void withdraw(int id, float amount, Operation type) {
        Account account =accountDao.getAccountById(id);
        if(account!=null) {
            if (account.getStatus()!= Status.ACTIVE)
                throw new NotAllowedException(account.getStatus()+" ACCOUNT");

            float amountToRetrieve = amount;
            if (type.equals(Operation.WITHDRAWAL_ALL)) amountToRetrieve = account.getBalance();

            if(amountToRetrieve<=0)
                throw new BadRequestException("INVALID AMOUNT");

            if(amountToRetrieve>account.getBalance())
                throw new BadRequestException("NOT ENOUGH FUNDS");

            if (amountToRetrieve <= account.getBalance())
                accountDao.withdraw(account, amountToRetrieve);
        }
        else
            throw  new BadRequestException("NULL ACCOUNT");
    }


    /*
   This function search account by id
   @param id and account's id
   @return Account if it exits otherwise return null
   * */
    public Account history(int id) {
        return accountDao.getAccountById(id);
    }

    /*public Balance balance(int id) {
        return balanceRepo.findById(id).get();
    }*/
}

