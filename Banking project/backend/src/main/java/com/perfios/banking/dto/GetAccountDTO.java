package com.perfios.banking.dto;


import lombok.Data;

import java.util.Date;

@Data
public class GetAccountDTO {
    private Integer accountNumber;
    private Double minimumAmount;
    private Double balance;
    private Integer userId;
    private String userName;
    private String firstName;
    private String lastName;
    private Integer branchId;
    private String branchName;
    private Date lastUpdated;
}
