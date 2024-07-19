package com.codebydaud.training.banking_app.service;

import com.codebydaud.training.banking_app.dto.AccountResponse;
import com.codebydaud.training.banking_app.entity.Account;
import com.codebydaud.training.banking_app.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AccountRepository accountRepository;

    public List<AccountResponse> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(AccountResponse::new)
                .collect(Collectors.toList());
    }

}