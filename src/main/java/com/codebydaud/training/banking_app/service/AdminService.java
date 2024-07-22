package com.codebydaud.training.banking_app.service;

import com.codebydaud.training.banking_app.dto.AccountResponse;
import com.codebydaud.training.banking_app.dto.LoginRequest;
import com.codebydaud.training.banking_app.dto.TransactionDTO;
import com.codebydaud.training.banking_app.dto.UserResponse;
import com.codebydaud.training.banking_app.entity.Account;
import com.codebydaud.training.banking_app.entity.User;
import com.codebydaud.training.banking_app.exception.InvalidTokenException;
import com.codebydaud.training.banking_app.repository.AccountRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


public interface AdminService {

    public ResponseEntity<String> login(LoginRequest loginRequest, HttpServletRequest request)
            throws InvalidTokenException;

    public List<AccountResponse> getAllAccounts();

    UserResponse getUserDetailsByAccountNumber(String accountNumber);

    List<TransactionDTO> getUserTransactions(String accountNumber);

    public ResponseEntity<String> updateUser(String accountNUmber, User user);

    public void deleteAccount(String accountNumber);

    public ModelAndView logout(String token) throws InvalidTokenException;

}
