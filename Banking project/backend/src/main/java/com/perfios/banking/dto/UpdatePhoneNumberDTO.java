package com.perfios.banking.dto;


import lombok.Data;

@Data
public class UpdatePhoneNumberDTO {
    Integer userId;
    String phoneNumber;
}
