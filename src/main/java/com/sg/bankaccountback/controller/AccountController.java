package com.sg.bankaccountback.controller;

import com.sg.bankaccountback.dao.AccountDao;
import com.sg.bankaccountback.exception.BadRequestException;
import com.sg.bankaccountback.exception.NotFoundException;
import com.sg.bankaccountback.model.Account;
import com.sg.bankaccountback.model.dto.AccountDto;
import com.sg.bankaccountback.model.dto.History;
import com.sg.bankaccountback.model.dto.SuccessResponse;
import com.sg.bankaccountback.model.Transaction;
import com.sg.bankaccountback.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(description = "API for bank operations")
@RestController
@RequestMapping("/bank")
public class AccountController {
    @Autowired
    AccountService accountService;

    @Autowired
    AccountDao accountDao;

    @ApiOperation(value = "get account statement")
    @GetMapping(value = "/statement/{id}")
    public ResponseEntity statement(@PathVariable int id) {
        Account account = accountDao.getAccountById(id);
        if(account!=null){
            AccountDto accountDto = new ModelMapper().map(account, AccountDto.class);
            return new ResponseEntity(accountDto, HttpStatus.OK);
        }
        return new ResponseEntity(new NotFoundException("ACCOUNT NOT FOUND"), HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/history/{id}")
    public ResponseEntity history(@PathVariable int id) {
        Account account = accountDao.getAccountById(id);
        History history = new ModelMapper().map(account, History.class);
        return new ResponseEntity(history, HttpStatus.OK);
    }

    @PostMapping(value = "/deposit/{id}")
    public ResponseEntity deposit(@PathVariable("id") int id, @Valid @RequestBody Transaction transaction) {
        Account account = accountDao.getAccountById(id);
        accountDao.deposit(account, transaction.getAmount());
        return new ResponseEntity(new SuccessResponse("DEPOSIT WITH SUCCESS"), HttpStatus.OK);
    }

    @PostMapping(value = "/withdrawal/{id}")
    public ResponseEntity withdrawal(@PathVariable("id") int id, @Valid @RequestBody Transaction transaction) {

        Account account = accountDao.getAccountById(id);
        if(!accountService.checkWithdrawalOperation(transaction.getOperation()))
           throw new BadRequestException("INVALID OPERATION, PLEASE CHOOSE BETWEEN 'WITHDRAWAL' OR 'WITHDRAWAL_ALL'");
        accountDao.withdraw(account, transaction.getAmount(), transaction.getOperation());
        return new ResponseEntity(new SuccessResponse("WITHDRAWAL WITH SUCCESS"), HttpStatus.OK);
    }
}
