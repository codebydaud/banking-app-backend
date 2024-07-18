package com.codebydaud.training.banking_app.dto;

public record FundTransferRequest(String sourceAccountNumber, String targetAccountNumber, double amount) {
}
