package com.perfios.banking.dto;

import com.perfios.banking.domain.User;
import lombok.Data;

@Data
public class UserDTOResponse {
    String message;
    User user;

}
