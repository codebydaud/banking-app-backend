package com.codebydaud.training.banking_app.entity;//package com.codebydaud.training.banking_app.entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
////import com.codebydaud.training.banking_app.entity.Token;
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToOne;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.NotNull;
//import lombok.Getter;
//import lombok.Setter;
//
//
//@Getter
//@Setter
//@Entity (name="accounts")
//public class Account {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long accountId;
//
//    private String accountNumber;
//    private double balance;
////    private String Pin;
//
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
////    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
////    private List<Token> tokens = new ArrayList<>();
//
//    @Override
//    public String toString() {
//        return "Account [id=" + accountId + ", accountNumber=" + accountNumber + ", balance=" + balance + ", userId=" + user + "]";
//    }
//
//}




import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.codebydaud.training.banking_app.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity (name = "accounts")
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @NotEmpty
    @Column(unique = true)
    private String accountNumber;

    private double balance;

    @NotEmpty
    private LocalDateTime createdAt;

    @NotNull
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Token> tokens = new ArrayList<>();

}