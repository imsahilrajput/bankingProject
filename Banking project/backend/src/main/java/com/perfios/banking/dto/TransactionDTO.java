package com.perfios.banking.dto;

import com.perfios.banking.domain.Account;
import lombok.Data;


@Data
public class TransactionDTO {
    private String transactionType;   //UPI,NEFT,RTGS
    private Double amount;
    private Integer debit;
    private Integer credit;
}
