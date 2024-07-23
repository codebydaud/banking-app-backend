package com.codebydaud.training.banking_app.dto;

import com.codebydaud.training.banking_app.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {

    private String accountNumber;
    private double balance;
//    private LocalDateTime createdAt;

    public AccountResponse(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
//        this.createdAt=account.getCreatedAt();
    }
}