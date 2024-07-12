package com.codebydaud.training.banking_app.service;

import com.codebydaud.training.banking_app.entity.User;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    public User registerUser(User user);

    public User saveUser(User user);

    public boolean doesEmailExist(String email);

    public boolean doesPhoneNumberExist(String phoneNumber);

    public boolean doesAccountExist(String accountNumber);

    public Optional<User> getUserByAccountNumber(String accountNumber);

    public Optional<User> getUserByEmail(String email);
}