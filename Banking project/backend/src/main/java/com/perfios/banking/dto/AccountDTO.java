package com.perfios.banking.dto;

import lombok.Data;


@Data
public class AccountDTO {

    private Double minimumAmount;
    private Double balance;
    private Integer userId;
    private Integer branchId;

}
