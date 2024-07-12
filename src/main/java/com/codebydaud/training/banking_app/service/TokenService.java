package com.codebydaud.training.banking_app.service;


import com.codebydaud.training.banking_app.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;
import java.util.function.Function;

public interface TokenService extends UserDetailsService {

    public String generateToken(UserDetails userDetails);

    public String generateToken(UserDetails userDetails, Date expiry);

    public String getUsernameFromToken(String token) throws InvalidTokenException;

    public Date getExpirationDateFromToken(String token) throws InvalidTokenException;

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
            throws InvalidTokenException;

    public void saveToken(String token) throws InvalidTokenException;

    public void validateToken(String token) throws InvalidTokenException;

    public void invalidateToken(String token);
}
