package com.codebydaud.training.banking_app.service;

import com.codebydaud.training.banking_app.dto.AccountResponse;
import com.codebydaud.training.banking_app.dto.LoginRequest;
import com.codebydaud.training.banking_app.dto.TransactionDTO;
import com.codebydaud.training.banking_app.dto.UserResponse;
import com.codebydaud.training.banking_app.entity.Account;
import com.codebydaud.training.banking_app.entity.User;
import com.codebydaud.training.banking_app.exception.InvalidTokenException;
import com.codebydaud.training.banking_app.exception.NotFoundException;
import com.codebydaud.training.banking_app.exception.UserInvalidException;
import com.codebydaud.training.banking_app.mapper.UserMapper;
import com.codebydaud.training.banking_app.repository.AccountRepository;
import com.codebydaud.training.banking_app.repository.UserRepository;
import com.codebydaud.training.banking_app.util.ApiMessages;
import com.codebydaud.training.banking_app.util.JsonUtil;
import com.codebydaud.training.banking_app.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {


    private final AccountRepository accountRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TransactionService transactionService;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<String> login(LoginRequest loginRequest)
            throws InvalidTokenException {
        return userService.login(loginRequest, "admin");
    }

    public List<AccountResponse> getAllAccounts() {
//        List<Account> accounts = accountRepository.findAll();
//        return accounts.stream()
//                .map(AccountResponse::new)
//                .collect(Collectors.toList());

        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(account -> {
                    Optional<User> user = userRepository.findByAccountAccountNumber(account.getAccountNumber());
                    String accountHolderName = user.isPresent()?user.get().getName():"Unknowns";
                    return new AccountResponse(account, accountHolderName);
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserDetailsByAccountNumber(String accountNumber) {
        val user = userRepository.findByAccountAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ApiMessages.USER_NOT_FOUND_BY_ACCOUNT.getMessage(), accountNumber)));

        return new UserResponse(user);
    }

    @Override
    public List<TransactionDTO> getUserTransactions(String accountNumber) {
        return transactionService.getAllTransactionsByAccountNumber(accountNumber);
    }

    @Override
    public ResponseEntity<String> updateUser(String accountNumber, User updatedUser) {
        log.info(updatedUser.toString());
        User existingUser = userService.getUserByAccountNumber(accountNumber);


        updateUserDetails(existingUser, updatedUser);
        val savedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(JsonUtil.toJson(new UserResponse(savedUser)));
    }

    private void updateUserDetails(User existingUser, User updatedUser) {
        if(updatedUser.getPassword()==null)
        {
            updatedUser.setPassword(existingUser.getPassword());
        }
        ValidationUtil.validateUserDetails(updatedUser);
        updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        userMapper.updateUser(updatedUser, existingUser);
    }

    public void deleteAccount(String accountNumber) {
        val user = userRepository.findByAccountAccountNumber(accountNumber)
                .orElseThrow(() -> new UserInvalidException(
                        String.format(ApiMessages.USER_NOT_FOUND_BY_ACCOUNT.getMessage(), accountNumber)));

        userRepository.delete(user);
    }

    @Override
    public ModelAndView logout(String token) throws InvalidTokenException {
        return userService.logout(token);
    }
}