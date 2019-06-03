package com.sg.bankaccountback.dao;

import com.sg.bankaccountback.model.Balance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepo extends CrudRepository<Balance, Integer> {

}
