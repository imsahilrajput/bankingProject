package com.perfios.banking.dto;


import com.perfios.banking.domain.User;
import lombok.Data;

@Data
public class UpdateUserDTO {
    String message;
    User user;
}
