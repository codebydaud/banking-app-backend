package com.codebydaud.training.banking_app.controller;

import com.codebydaud.training.banking_app.dto.LoginRequest;
import com.codebydaud.training.banking_app.dto.UserResponse;
import com.codebydaud.training.banking_app.entity.User;
import com.codebydaud.training.banking_app.exception.InvalidTokenException;
import com.codebydaud.training.banking_app.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(
            UserController.class);

    public UserController(
            UserService userService,
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            UserDetailsService userDetailsService) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        UserResponse userResponse = new UserResponse(registeredUser);

        return ResponseEntity.ok(userResponse.toString());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request)
            throws InvalidTokenException {

        final String accountNumber = loginRequest.getAccountNumber();

        logger.info("Authenticating Account: {}", accountNumber);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                accountNumber,
                loginRequest.getPassword()));

        logger.info("Account: {} authenticated successfully", accountNumber);

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(accountNumber);

        final String token = tokenService.generateToken(userDetails);
        tokenService.saveToken(token);

        logger.info("Account: {} logged in successfully", accountNumber);

        return ResponseEntity.ok("{ \"token\": \"" + token + "\" }");
    }

//    @PostMapping("/update")
//    public ResponseEntity<String> updateUser(@RequestBody User user) {
//        String accountNumber = LoggedinUser.getAccountNumber();
//
//        logger.info("Authenticating account: {} ...", accountNumber);
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                accountNumber,
//                user.getPassword()));
//        logger.info("Account: {} authenticated successfully", accountNumber);
//
//        logger.info("Updating account: {} ...", accountNumber);
//        User updatedUser = userService.updateUser(user);
//
//        logger.info("Account: {} is updated successfully", accountNumber);
//
//        UserResponse userResponse = new UserResponse(updatedUser);
//
//        return ResponseEntity.ok(JsonUtil.toJson(userResponse));
//    }

//    @GetMapping("/logout")
//    public ModelAndView logout(@RequestHeader("Authorization") String token)
//            throws InvalidTokenException {
//
//        token = token.substring(7);
//        tokenService.validateToken(token);
//        tokenService.invalidateToken(token);
//
//        logger.info("User logged out successfully {}",
//                tokenService.getUsernameFromToken(token));
//
//        return new ModelAndView("redirect:/logout");
//    }

}