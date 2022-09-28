package com.perfios.banking.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer branchId;

    private String branchName;
    private String branchAddress;
    private String ifsc;

    @JsonIgnore
    @OneToMany(mappedBy = "branch")
    private List<Account> accountsList;


    public Branch(String branchName, String branchAddress, String ifsc) {
        this.branchName = branchName;
        this.branchAddress = branchAddress;
        this.ifsc = ifsc;
    }
}
