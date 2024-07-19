package com.codebydaud.training.banking_app.service;

import com.codebydaud.training.banking_app.dto.LoginRequest;
import com.codebydaud.training.banking_app.entity.User;
import com.codebydaud.training.banking_app.exception.InvalidTokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public interface UserService {

    public ResponseEntity<String> registerUser(User user);


    public ResponseEntity<String> login(LoginRequest loginRequest, String requestMaker, HttpServletRequest request)
            throws InvalidTokenException;

    public User saveUser(User user);

    public User getUserByIdentifier(String identifier);

    public User getUserByAccountNumber(String accountNo);

    public User getUserByEmail(String email);

    public ModelAndView logout(String token) throws InvalidTokenException;
}