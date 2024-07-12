package com.codebydaud.training.banking_app.entity;


import java.util.Date;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity (name="tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    private String token;
    private Date createdAt = new Date();
    private Date expiryAt;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Token() {
    }

    public Token(String token, Date expiryAt, Account account) {
        this.token = token;
        this.expiryAt = expiryAt;
        this.account = account;
    }
}