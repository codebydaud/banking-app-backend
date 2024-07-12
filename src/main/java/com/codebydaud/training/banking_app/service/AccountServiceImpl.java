package com.codebydaud.training.banking_app.service;

import com.codebydaud.training.banking_app.entity.Account;
import com.codebydaud.training.banking_app.entity.User;
import com.codebydaud.training.banking_app.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    final private AccountRepository accountRepository;
//    final private PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
//        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Account createAccount(User user) {
        Account account = new Account();
        account.setAccountNumber(generateUniqueAccountNumber());
        account.setBalance(0.0);
        account.setUser(user);
        return accountRepository.save(account);
    }

//    @Override
//    public boolean isPinCreated(String accountNumber) {
//        Account account = accountRepository.findByAccountNumber(accountNumber);
//        if (account == null) {
//            throw new ChangeSetPersister.NotFoundException("Account not found");
//        }
//
//        return account.getPin() != null;
//    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            // Generate a UUID as the account number
            accountNumber = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        } while (accountRepository.findByAccountNumber(accountNumber) != null);

        return accountNumber;
    }

//    private void validatePin(String accountNumber, String pin) {
//        Account account = accountRepository.findByAccountNumber(accountNumber);
//        if (account == null) {
//            throw new ChangeSetPersister.NotFoundException("Account not found");
//        }
//
//        if (account.getPin() == null) {
//            throw new UnauthorizedException("PIN not created");
//        }
//
//        if (pin == null || pin.isEmpty()) {
//            throw new UnauthorizedException("PIN cannot be empty");
//        }
//
//        if (!passwordEncoder.matches(pin, account.getPin())) {
//            throw new UnauthorizedException("Invalid PIN");
//        }
//    }
//
//    private void validatePassword(String accountNumber, String password) {
//        Account account = accountRepository.findByAccountNumber(accountNumber);
//        if (account == null) {
//            throw new ChangeSetPersister.NotFoundException("Account not found");
//        }
//
//        if (password == null || password.isEmpty()) {
//            throw new UnauthorizedException("Password cannot be empty");
//        }
//
//        if (!passwordEncoder.matches(password, account.getUser().getPassword())) {
//            throw new UnauthorizedException("Invalid password");
//        }
//    }
//
//    @Override
//    public void createPin(String accountNumber, String password, String pin) {
//        validatePassword(accountNumber, password);
//
//        Account account = accountRepository.findByAccountNumber(accountNumber);
//        if (account.getPin() != null) {
//            throw new UnauthorizedException("PIN already created");
//        }
//
//        if (pin == null || pin.isEmpty()) {
//            throw new InvalidPinException("PIN cannot be empty");
//        }
//
//        if (!pin.matches("[0-9]{4}")) {
//            throw new InvalidPinException("PIN must be 4 digits");
//        }
//
//        account.setPin(passwordEncoder.encode(pin));
//        accountRepository.save(account);
//    }
//
//
//    private void validateAmount(double amount) {
//        if (amount <= 0) {
//            throw new InvalidAmountException("Amount must be greater than 0");
//        }
//
//        if (amount % 100 != 0) {
//            throw new InvalidAmountException("Amount must be in multiples of 100");
//        }
//
//        if (amount > 100000) {
//            throw new InvalidAmountException("Amount cannot be greater than 100,000");
//        }
//    }

}