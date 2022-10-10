package com.perfios.banking.services;




import com.perfios.banking.domain.Branch;
import com.perfios.banking.dto.BranchDTO;
import com.perfios.banking.repository.BranchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BranchServiceTest {

    @Mock
    BranchRepository branchRepository;
    @InjectMocks
    BranchService branchService;

    @Test
    void addBranchSuccessfully() {
        Branch branch = new Branch("Kormangala","Bengaluru","PERFS0001");

        Branch branch1 = new Branch(1,"Kormangala","Bengaluru","PERFS0001",new ArrayList<>());

        when(branchRepository.save(branch)).thenReturn(branch1);


        BranchDTO branchDTO = new BranchDTO();
        branchDTO.setBranchName("Kormangala");
        branchDTO.setBranchAddress("Bengaluru");
        branchDTO.setIfsc("PERFS0001");
        assertEquals(branchService.createBranch(branch),branch1);
    }
}