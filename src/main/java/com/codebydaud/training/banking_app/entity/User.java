package com.codebydaud.training.banking_app.entity;//package com.codebydaud.training.banking_app.entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//@Entity(name="users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    private String name;
//    private String password;
//    private String email;
//    private String countryCode;
//    private String phoneNumber;
//    private String address;
//
//    // Establishing a one-to-one relationship with the account
//    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//    private Account account;
//
//    public void setAccount(Account account) {
//        this.account = account;
//        account.setUser(this);
//    }
//}



import com.codebydaud.training.banking_app.entity.Account;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Entity (name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty
    private String name;

    @NotEmpty
    private String password;

    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    private String countryCode;

    @NotEmpty
    @Column(unique = true)
    private String phoneNumber;

    @NotEmpty
    private String address;

    @NotEmpty
    private String role;

    // Establishing a one-to-one relationship with the account
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Account account;

}