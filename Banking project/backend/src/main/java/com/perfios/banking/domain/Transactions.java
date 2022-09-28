package com.perfios.banking.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transactionId;
    private String transactionType;//UPI,NEFT,RTGS
    private Double amount;

    private Date date;
    @ManyToOne
    @JoinColumn(name = "debitAcc")
    private Account debit;//debit refers to debitAcc in transaction table and it is a foreing key for account table

    @ManyToOne
    @JoinColumn(name = "creditAcc")
    private Account credit;

    public Transactions(String transactionType, Double amount, Account debit, Account credit, Date date) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.debit = debit;
        this.credit = credit;
        this.date =date;
    }

    public Transactions(String transactionType, Double amount, Account debit,Date date) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.debit = debit;
        this.date =date;

    }

    public Transactions(String transactionType,Account credit, Double amount,Date date) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.credit = credit;
        this.date =date;

    }

}
