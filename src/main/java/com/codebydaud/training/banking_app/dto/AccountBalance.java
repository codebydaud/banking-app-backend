package com.codebydaud.training.banking_app.dto;

import com.codebydaud.training.banking_app.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountBalance {

    private double balance;

    public AccountBalance(Account account) {
        this.balance=account.getBalance();
    }
}