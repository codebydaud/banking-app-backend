package com.codebydaud.training.banking_app.service;

import com.codebydaud.training.banking_app.entity.Account;
import com.codebydaud.training.banking_app.entity.User;

import java.util.List;

public interface AccountService {

    public Account createAccount(User user);
    public void fundTransfer(String sourceAccountNumber, String targetAccountNumber, String description, double amount);
}