package com.sg.bankaccountback;

import com.sg.bankaccountback.dao.AccountDao;
import com.sg.bankaccountback.exception.BadRequestException;
import com.sg.bankaccountback.exception.NotAllowedException;
import com.sg.bankaccountback.exception.NotFoundException;
import com.sg.bankaccountback.model.Account;
import com.sg.bankaccountback.model.Operation;
import com.sg.bankaccountback.model.Status;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.slf4j.Logger;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAccountBackApplication.class)
public class AccountTest {

    @Autowired
    AccountDao accountDao;

    Account account = new Account(1, "MM Y A", "30003 01199 11150000182 66", 100,Status.ACTIVE, new Date());
    Account accountB = new Account(2, "MM Y A", "30003 01199 11150000182 66", 100,Status.BLOCKED, new Date());
    Account accountC = new Account(3, "MM Y A", "30003 01199 11150000182 66", 100,Status.CLOSED, new Date());


    @BeforeEach
    public void printS(){
    }

    @Test
    public void testGetAccount(){
        try {
            assertEquals(2, accountDao.getAccountById(2).getId());
        } catch (AssertionError e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testDepositAmount(){
        float initialBalance = account.getBalance();
        float amount = 10;
        accountDao.deposit(account, amount);
        assertEquals((amount+initialBalance),account.getBalance(), 0.0d);
    }

    @Test
    public void testDepositNegativeAmount(){
        try {
            accountDao.deposit(account, -1000);
        } catch (BadRequestException e) {
        }
    }

    @Test
    public void testWithdrawal(){
        float initialBalance = account.getBalance();
        float amount = 10;
        accountDao.withdraw(account, 10, Operation.WITHDRAWAL);
        assertEquals((initialBalance-amount),account.getBalance(), 0.0d);
    }

    @Test
    public void testWithdrawHighAmount(){
        try {
            accountDao.withdraw(account,1000, Operation.WITHDRAWAL);
        } catch (BadRequestException e) {
        }
    }

    @Test
    public void testWithdrawalBlockedAcc(){
        try {
            accountDao.withdraw(accountB, 10, Operation.WITHDRAWAL);
        } catch (NotAllowedException e) {
        }
    }

    @Test
    public void testWithdrawalClosedAcc(){
        try {
            accountDao.withdraw(accountC,10, Operation.WITHDRAWAL);
        } catch (NotAllowedException e) {
        }
    }

    @Test
    public void testGetNonExistAccount(){
        try {
            assertNull(accountDao.getAccountById(13));
        } catch (NotFoundException e) {
        }
    }


    @Test
    public void testAddTransaction(){
        try {
            accountDao.addTransaction(null, Operation.DEPOSIT, 10);
        } catch (NotFoundException e) {
        }
    }

    @Test
    public void testAddTransactionNullAccount(){
        try {
            accountDao.addTransaction(null, Operation.DEPOSIT, 10);
        } catch (NotFoundException e) {
        }
    }

}
