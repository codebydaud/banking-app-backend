package com.codebydaud.training.banking_app.dto;

import com.codebydaud.training.banking_app.entity.Account;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private String accountNumber;
    private double balance;
    private String accountHolderName;
    private Date createdAt=new Date();

    public AccountResponse(Account account, String accountHolderName) {
        this.accountNumber = account.getAccountNumber();
        this.accountHolderName=accountHolderName;
        this.balance = account.getBalance();
        this.createdAt=account.getCreatedAt();
    }
}