package com.codebydaud.training.banking_app.controller;

import com.codebydaud.training.banking_app.dto.LoginRequest;
import com.codebydaud.training.banking_app.entity.Account;
import com.codebydaud.training.banking_app.exception.InvalidTokenException;
import com.codebydaud.training.banking_app.service.AccountService;
import com.codebydaud.training.banking_app.service.AdminService;
import com.codebydaud.training.banking_app.util.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/accounts")
    public ResponseEntity<String> getAllAccounts() {
        val accountList=adminService.getAllAccounts();
        return ResponseEntity.ok(JsonUtil.toJson(accountList));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request)
            throws InvalidTokenException {
        return adminService.login(loginRequest, request);
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/logout")
    public ModelAndView logout(@RequestHeader("Authorization") String token)

            throws InvalidTokenException {
        return adminService.logout(token);
    }

//    @GetMapping("/{accountId}")
//    public ResponseEntity<Account> getAccountById(@PathVariable Long accountId) {
//        return ResponseEntity.ok(accountService.getAccountById(accountId));
//    }
//
//    @PostMapping
//    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
//        return ResponseEntity.ok(accountService.createAccount(account));
//    }
//
//    @PutMapping("/{accountId}")
//    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody Account account) {
//        return ResponseEntity.ok(accountService.updateAccount(accountId, account));
//    }
//
//    @DeleteMapping("/{accountId}")
//    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
//        accountService.deleteAccount(accountId);
//        return ResponseEntity.noContent().build();
//    }
}