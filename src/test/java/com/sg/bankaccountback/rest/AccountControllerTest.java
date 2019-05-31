package com.sg.bankaccountback.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sg.bankaccountback.BankAccountBackApplication;
import com.sg.bankaccountback.model.Account;
import com.sg.bankaccountback.model.Operation;
import com.sg.bankaccountback.model.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*
TODO testing rest api
* */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankAccountBackApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    Account accountB = new Account(1, "MM Y A", "30003 01199 11150000182 66", 100, Status.BLOCKED, new Date());
    Account account = new Account(2, "MR X x", "31113 01199 00050333182 66", 300, Status.ACTIVE, new Date());


    @Before
    public void setup() throws Exception{
        this.mockMvc =  MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void getAccountShouldReturn200AndAccount() throws Exception{

        mockMvc.perform(get("/bank/statement/{id}",account.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.accountNumber").value(account.getAccountNumber()));

    }

    @Test
    public void getAccountShouldReturn404() throws Exception{

        mockMvc.perform(get("/bank/statement/{id}",33))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getHistoryShouldReturn200AndHistory() throws Exception{

        mockMvc.perform(get("/bank/history/{id}",account.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.accountNumber").value(account.getAccountNumber()));

    }

    @Test
    public void depositFundMustReturn200() throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        Object transaction = new Object() {
            public final float amount = 300;
        };
        String json = objectMapper.writeValueAsString(transaction);
        mockMvc.perform(post("/bank/deposit/{id}",account.getId())
                .contentType("application/json;charset=UTF-8")
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void depositNonValidFundMustReturn400() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Object transaction = new Object() {
            public final float amount = -3000;
            public final Operation operation = Operation.WITHDRAWAL;
        };
        String json = objectMapper.writeValueAsString(transaction);
        mockMvc.perform(post("/bank/deposit/{id}", account.getId())
                .contentType("application/json;charset=UTF-8")
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void withdrawalBlockedAccountMustReturn405() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Object transaction = new Object() {
            public final float amount = 10;
            public final Operation operation = Operation.WITHDRAWAL;
        };
        String json = objectMapper.writeValueAsString(transaction);
        mockMvc.perform(post("/bank/withdrawal/{id}", accountB.getId())
                .contentType("application/json;charset=UTF-8")
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void withdrawalExceedingFundMustReturn400() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Object transaction = new Object() {
            public final float amount = 3000;
            public final Operation operation = Operation.WITHDRAWAL;
        };
        String json = objectMapper.writeValueAsString(transaction);
        mockMvc.perform(post("/bank/withdrawal/{id}", account.getId())
                .contentType("application/json;charset=UTF-8")
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void withdrawalMissingOperationTypeMustReturn400() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Object transaction = new Object() {
            public final float amount = 3000;
        };
        String json = objectMapper.writeValueAsString(transaction);
        mockMvc.perform(post("/bank/withdrawal/{id}", accountB.getId())
                .contentType("application/json;charset=UTF-8")
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest());
    }
}
