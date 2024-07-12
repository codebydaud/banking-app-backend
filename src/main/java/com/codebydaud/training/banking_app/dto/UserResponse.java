package com.codebydaud.training.banking_app.dto;

import com.codebydaud.training.banking_app.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private String name;
    private String email;
    private String countryCode;
    private String phoneNumber;
    private String address;
    private String accountNumber;

    public UserResponse() {
    }

    public UserResponse(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.countryCode = user.getCountryCode();
        this.phoneNumber = user.getPhoneNumber();
        this.address = user.getAddress();
        this.accountNumber = user.getAccount().getAccountNumber();
    }
    @Override
    public String toString() {
        return "{ \"name\": \"" + name
                + "\", \"email\": \"" + email
                + "\", \"address\": \"" + address
                + "\", \"countryCode\": \"" + countryCode
                + "\", \"phoneNumber\": \"" + phoneNumber
                + "\", \"accountNumber\": \"" + accountNumber + "\" }";
    }

}