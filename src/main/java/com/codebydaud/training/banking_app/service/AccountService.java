package com.codebydaud.training.banking_app.service;

import com.codebydaud.training.banking_app.entity.Account;
import com.codebydaud.training.banking_app.entity.User;

public interface AccountService {

    public Account createAccount(User user);
    public void fundTransfer(String sourceAccountNumber, String targetAccountNumber, double amount);
}