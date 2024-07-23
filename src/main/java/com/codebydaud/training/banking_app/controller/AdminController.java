package com.codebydaud.training.banking_app.controller;

import com.codebydaud.training.banking_app.dto.LoginRequest;
import com.codebydaud.training.banking_app.entity.User;
import com.codebydaud.training.banking_app.exception.InvalidTokenException;
import com.codebydaud.training.banking_app.service.AdminService;
import com.codebydaud.training.banking_app.util.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest)
            throws InvalidTokenException {
        return adminService.login(loginRequest);
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/accounts")
    public ResponseEntity<String> getAllAccounts() {
        val accountList = adminService.getAllAccounts();
        return ResponseEntity.ok(JsonUtil.toJson(accountList));
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<String> getUserDetailsByAccountNumber(@PathVariable String accountNumber) {
        val userResponse = adminService.getUserDetailsByAccountNumber(accountNumber);
        return ResponseEntity.ok(JsonUtil.toJson(userResponse));
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/accounts/{accountNumber}/transactions")
    public ResponseEntity<String> getUserTransactions(@PathVariable String accountNumber) {
        val transactions = adminService
                .getUserTransactions(accountNumber);
        return ResponseEntity.ok(JsonUtil.toJson(transactions));
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/update/{accountNumber}")
    public ResponseEntity<String> updateUser(@PathVariable String accountNumber, @RequestBody User user) {
        return adminService.updateUser(accountNumber, user);
    }

    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/accounts/{accountNumber}")
    public ResponseEntity<String> deleteAccount(@PathVariable String accountNumber) {
        adminService.deleteAccount(accountNumber);
        return ResponseEntity.ok("Account deleted successfully.");
    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/logout")
    public ModelAndView logout(@RequestHeader("Authorization") String token)

            throws InvalidTokenException {
        return adminService.logout(token);
    }
}