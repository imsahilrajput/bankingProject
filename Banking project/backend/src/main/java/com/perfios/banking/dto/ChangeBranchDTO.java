package com.perfios.banking.dto;


import lombok.Data;

@Data
public class ChangeBranchDTO {
    Integer accountNumber;
    Integer newBranchId;
}
