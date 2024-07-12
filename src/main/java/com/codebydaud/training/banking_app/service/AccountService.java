package com.codebydaud.training.banking_app.service;

import com.codebydaud.training.banking_app.entity.Account;
import com.codebydaud.training.banking_app.entity.User;

public interface AccountService {

    Account createAccount(User user);
//    public boolean isPinCreated(String accountNumber) ;
//    public void createPin(String accountNumber, String password, String pin) ;
}