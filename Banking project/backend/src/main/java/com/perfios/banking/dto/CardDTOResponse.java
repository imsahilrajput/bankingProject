package com.perfios.banking.dto;

import lombok.Data;

import java.util.Date;


@Data
public class CardDTOResponse {
    private Integer cardNumber;
    private Integer accountNumber;
    private Double cardLimit;
    private String status;
    private Integer userId;
    private Integer pin;
    private String cardType;
    private Double limitUsed;
    private Double balance;
    private Integer roi;
    private Date dateOfIssue;
    private Integer frequency;
}
