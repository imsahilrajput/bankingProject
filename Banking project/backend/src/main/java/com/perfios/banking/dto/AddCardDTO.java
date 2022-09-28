package com.perfios.banking.dto;


import lombok.Data;

@Data
public class AddCardDTO {

    String cardType;

    Integer accountNumber;
    Integer pin;

}
