package com.perfios.banking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UserDTO {
     String userName;
     String firstName;
     String lastName;
     String password;
     String address;
     String email;
     String phoneNumber;
     String role;
     private Date dob;
     private Integer cibilScore;
     private Double monthlySalary;

}
