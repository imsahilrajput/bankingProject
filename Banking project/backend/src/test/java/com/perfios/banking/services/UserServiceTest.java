package com.perfios.banking.services;

import com.perfios.banking.domain.Account;
import com.perfios.banking.domain.Branch;
import com.perfios.banking.domain.User;
import com.perfios.banking.dto.AccountDTO;
import com.perfios.banking.dto.UserDTO;
import com.perfios.banking.repository.AccountRepository;
import com.perfios.banking.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;


    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addUserSuccessfully() throws IOException {
        byte[] bytes = new byte[10];
//        User user = new User("helloSahil","Sahil","Singh","hello","blr","sahil@gmail.com","9999999999","client",new Date(),750,(double)123455,"Salary proof","blob",bytes);
//        Branch branch = new Branch("Kormangala","Bengaluru","PERFS0001");
        User user1 = new User(1,"Sahil","Singh","helloSahil","hello","blr","sahil@gmail.com","9999999999","client",new Date(),750,(double)123455,"Salary proof","blob",bytes,new ArrayList<>());

//        Account account = new Account((double)2000, (double)10000, user, branch);
//
//        Account account1 = new Account(1,(double)2000, (double)10000, new ArrayList<>(),new ArrayList<>(),user, branch,new ArrayList<>());


        when(userRepository.saveAndFlush(any())).thenReturn(user1);

        UserDTO userDTO = new UserDTO("helloSahil","Sahil","Singh","hello","blr","sahil@gmail.com","9999999999","client",new Date(),750,(double)123455);

        MultipartFile multipartFile = new MockMultipartFile("Salary proof", bytes);

        assertEquals(userService.addUser(userDTO,multipartFile).getUser().getUserId(),user1.getUserId());

    }
}
