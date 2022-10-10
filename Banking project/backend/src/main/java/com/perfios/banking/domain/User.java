package com.perfios.banking.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String address;
    private String email;
    private String phoneNumber;
    private String role;

    private Date dob;
    private Integer cibilScore;
    private Double monthlySalary;



    private String documentName;

    private String documentType;

    @Lob
    private byte[] documentData;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Account> accountsList;




    public User(String userName,String firstName, String lastName, String password, String address, String email, String phoneNumber,String role,Date dob,Integer cibilScore,Double monthlySalary) {
        this.userName= userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role=role;
        this.dob=dob;
        this.cibilScore=cibilScore;
        this.monthlySalary=monthlySalary;
    }
    public User(String userName,String firstName, String lastName, String password, String address, String email, String phoneNumber,String role,Date dob,Integer cibilScore,Double monthlySalary,String documentName, String documentType, byte[] documentData) {
        this.userName= userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role=role;
        this.dob=dob;
        this.cibilScore=cibilScore;
        this.monthlySalary=monthlySalary;
        this.documentName = documentName;
        this.documentType = documentType;
        this.documentData = documentData;

    }
    public User(Integer userId,String documentName, String documentType, byte[] documentData) {
        this.userId=userId;
        this.documentName = documentName;
        this.documentType = documentType;
        this.documentData = documentData;
    }
}
