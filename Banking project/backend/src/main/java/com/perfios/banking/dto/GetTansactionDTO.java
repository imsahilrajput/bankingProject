package com.perfios.banking.dto;

import lombok.Data;

import java.security.PrivateKey;
import java.util.Date;

@Data
public class GetTansactionDTO {
    private Integer transactionId;
    private String transactionType;   //UPI,NEFT,RTGS
    private Double amount;
    private Date date;
    private Integer debitAccountNumber;
    private Integer creditAccountNumber;
    private Boolean deposit;
    private  Boolean withdraw;
    private Boolean transfer;
}
