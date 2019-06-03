package com.sg.bankaccountback;

import com.sg.bankaccountback.exception.BadRequestException;
import com.sg.bankaccountback.exception.NotAllowedException;
import com.sg.bankaccountback.exception.NotFoundException;
import com.sg.bankaccountback.model.Account;
import com.sg.bankaccountback.model.Operation;
import com.sg.bankaccountback.model.Status;
import com.sg.bankaccountback.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAccountBackApplication.class)
public class AccountTest {

    @Autowired
    AccountService accountService;

    Account accountB = new Account(1, "MM Y A", "30003 01199 11150000182 66", 100,Status.BLOCKED, new Date());
    Account account = new Account(2, "MM Y A", "30003 01199 11150000182 66", 100,Status.ACTIVE, new Date());


    @Test
    public void testDepositAmount(){
        float initialBalance = accountService.history(account.getId()).getBalance();
        float amount = 10;
        accountService.deposit(account.getId(), amount);
        assertEquals((amount+initialBalance), accountService.history(account.getId()).getBalance(), 0.0d);
    }

    @Test
    public void testWithdrawal(){
        float initialBalance = accountService.history(account.getId()).getBalance();
        float amount = 10;
        accountService.withdraw(account.getId(), 10, Operation.WITHDRAWAL);
        assertEquals((initialBalance-amount), accountService.history(account.getId()).getBalance(), 0.0d);
    }

    @Test
    public void testGetHistory(){
        assertEquals(accountService.history(account.getId()).getStatus(),accountService.history(account.getId()).getStatus());
    }

    @Test(expected = BadRequestException.class)
    public void testWithdrawHighAmount(){
        accountService.withdraw(account.getId(),1000, Operation.WITHDRAWAL);
    }

    @Test(expected = NotAllowedException.class)
    public void testWithdrawalBlockedAcc(){
        accountService.withdraw(accountB.getId(), 10, Operation.WITHDRAWAL);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNonExistAccount(){
        accountService.history(13);
    }

    @Test(expected = BadRequestException.class)
    public void testDepositNegativeAmount(){
        accountService.deposit(accountB.getId(), -1000);
    }


}
