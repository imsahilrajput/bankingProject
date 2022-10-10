package com.perfios.banking.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer accountNumber;

    private Double minimumAmount;
    private Double balance;

    @JsonIgnore
    @OneToMany(mappedBy = "debit")
    private List<Transactions> debitedTransactionList;


    @JsonIgnore
    @OneToMany(mappedBy = "credit")
    private List<Transactions> creditedTransactionList;

    @ManyToOne
    @JoinColumn(name = "user" )
    private User user;

    @ManyToOne
    @JoinColumn(name = "branch" )
    private Branch branch;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    private List<Card> cardList;

    public Account(Double minimumAmount, Double balance, User user, Branch branch) {
        this.minimumAmount = minimumAmount;
        this.balance = balance;
        this.user = user;
        this.branch = branch;
    }
}
