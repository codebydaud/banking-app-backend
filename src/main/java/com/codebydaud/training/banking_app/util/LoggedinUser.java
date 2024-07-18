package com.codebydaud.training.banking_app.util;

import lombok.val;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import com.codebydaud.training.banking_app.exception.NotFoundException;

public class LoggedinUser {

    /*
     * Returns the account number of the currently logged in user. If there is
     * no user logged in, an exception is thrown.
     *
     * @return The account number of the currently logged in user.
     * @throws NotFoundException If there is no user logged in.
     */

    public static String getAccountNumber() {
        val authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new NotFoundException("No User is Currently Logged in.");
        }
        val principal = (User) authentication.getPrincipal();
        return principal.getUsername();
    }

}