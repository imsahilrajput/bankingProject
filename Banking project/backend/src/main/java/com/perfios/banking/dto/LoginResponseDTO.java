package com.perfios.banking.dto;


import lombok.Data;

@Data
public class LoginResponseDTO {
    String message;
    Integer userId;
    String role;
}
