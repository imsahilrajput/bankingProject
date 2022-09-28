package com.perfios.banking.dto;


import lombok.Data;

@Data
public class UpdateBranchDTO {
    Integer branchId;
    BranchDTO branchDTO;
}
