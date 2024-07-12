package com.codebydaud.training.banking_app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String accountNumber;
    private String password;
}
