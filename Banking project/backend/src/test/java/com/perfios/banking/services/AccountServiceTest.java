package com.perfios.banking.services;

import com.perfios.banking.domain.Account;
import com.perfios.banking.domain.Branch;
import com.perfios.banking.domain.User;
import com.perfios.banking.dto.AccountDTO;
import com.perfios.banking.dto.BranchDTO;
import com.perfios.banking.repository.AccountRepository;
import com.perfios.banking.repository.BranchRepository;
import com.perfios.banking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;
    @Mock
    UserRepository userRepository;
    @Mock
    BranchRepository branchRepository;
    @InjectMocks
    AccountService accountService;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addAccountSuccessfully() {

        byte[] bytes = new byte[10];
        User user = new User(1,"Sahil","Singh","helloSahil","hello","blr","sahil@gmail.com","9999999999","client",new Date(),750,(double)123455,"Salary proof","blob",bytes,new ArrayList<>());
        Branch branch = new Branch("Kormangala","Bengaluru","PERFS0001");

        Account account = new Account((double)2000, (double)10000, user, branch);

        Account account1 = new Account(1,(double)2000, (double)10000, new ArrayList<>(),new ArrayList<>(),user, branch,new ArrayList<>());


        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(branchRepository.findById(any())).thenReturn(Optional.of(branch));
        when(accountRepository.save(any())).thenReturn(account1);

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance((double)10000);
        accountDTO.setBranchId(1);
        accountDTO.setMinimumAmount((double)2000);
        accountDTO.setUserId(1);

        assertEquals(accountService.createAccount(accountDTO).getAccountNumber(),account1.getAccountNumber());

    }
}
