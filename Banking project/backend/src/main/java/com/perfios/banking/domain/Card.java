package com.perfios.banking.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cardNumber;

    private Double cardLimit;
    private String status;
    private Integer pin;
    private  String cardType;
    private Double limitUsed;
    private Double balance;
    private Integer roi;
    private Date dateOfIssue;
    private Integer frequency;


    @ManyToOne
    @JoinColumn(name = "account" )
    private Account account;

}
