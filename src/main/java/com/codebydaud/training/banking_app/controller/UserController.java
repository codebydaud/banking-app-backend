package com.codebydaud.training.banking_app.controller;

import com.codebydaud.training.banking_app.dto.LoginRequest;
import com.codebydaud.training.banking_app.entity.User;
import com.codebydaud.training.banking_app.exception.InvalidTokenException;
import com.codebydaud.training.banking_app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banking-app")
public class UserController {

    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(
            UserController.class);

    public UserController(
            UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request)
            throws InvalidTokenException {
        return userService.login(loginRequest, request);
    }

}