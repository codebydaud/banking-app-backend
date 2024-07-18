package com.codebydaud.training.banking_app.controller;

import com.codebydaud.training.banking_app.dto.FundTransferRequest;
import com.codebydaud.training.banking_app.service.AccountService;
import com.codebydaud.training.banking_app.util.ApiMessages;
import com.codebydaud.training.banking_app.util.LoggedinUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/fund-transfer")
    public ResponseEntity<String> fundTransfer(@RequestBody FundTransferRequest fundTransferRequest) {
        accountService.fundTransfer(
                LoggedinUser.getAccountNumber(),
                fundTransferRequest.targetAccountNumber(),
                fundTransferRequest.amount());

        return ResponseEntity.ok(ApiMessages.CASH_TRANSFER_SUCCESS.getMessage());
    }
}