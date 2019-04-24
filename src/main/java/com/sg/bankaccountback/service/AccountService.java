package com.sg.bankaccountback.service;

import com.sg.bankaccountback.dao.AccountDao;
import com.sg.bankaccountback.model.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    AccountDao accountDao;

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

}
