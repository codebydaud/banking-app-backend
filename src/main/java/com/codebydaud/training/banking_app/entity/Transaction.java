//package com.codebydaud.training.banking_app.entity;
//
//import com.codebydaud.training.banking_app.entity.Account;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//
//
//@Getter
//@Setter
//@Entity
//public class Transaction {
//
//    @Id
//    private Long transactionId;
//
//    @ManyToOne
//    @JoinColumn(name = "source_account_id")
//    private Account sourceAccountId;
//
//    @ManyToOne
//    @JoinColumn(name = "destination_account_id")
//    private Account destinationAccountId;
//
//    private String description;
//    private BigDecimal amount;
//    private LocalDateTime date;
//
//}