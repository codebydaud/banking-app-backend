package com.codebydaud.training.banking_app.service;

import com.codebydaud.training.banking_app.dto.AccountResponse;
import com.codebydaud.training.banking_app.entity.Account;
import com.codebydaud.training.banking_app.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AdminService {

    public List<AccountResponse> getAllAccounts();

}
