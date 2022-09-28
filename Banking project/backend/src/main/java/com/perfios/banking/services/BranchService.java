package com.perfios.banking.services;

import com.perfios.banking.domain.Branch;
import com.perfios.banking.dto.BranchDTO;
import com.perfios.banking.dto.UpdateBranchDTO;
import com.perfios.banking.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BranchService {
    @Autowired
    BranchRepository branchRepo;


    public Branch createBranch(Branch branch) {
        List<Branch> branches = branchRepo.findAll();
        for (Branch eachBranch : branches) {
            if (eachBranch.getBranchName().equals(branch.getBranchName())) {
                throw new EntityExistsException("Branch with branch name: " + branch.getBranchName() + " already exists.");
            }
        }
        return branchRepo.save(branch);
    }


    public List<Branch> getBranches() {
        List<Branch> branchList = branchRepo.findAll();
        if (branchList.isEmpty())
            throw new IllegalStateException("No Branch found.");
        return branchList;
    }

    public Branch getBranch(int branchID) {
        Branch branch;
        try {
            branch = branchRepo.findById(branchID).get();

        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("No branch with id " + branchID + " found.");
        }
        return branch;
    }

    public Branch updateBranch(UpdateBranchDTO updateBranchDTO){
        Branch branchOld=branchRepo.findById(updateBranchDTO.getBranchId()).get();
        branchOld.setBranchAddress(updateBranchDTO.getBranchDTO().getBranchAddress());
        branchOld.setBranchName(updateBranchDTO.getBranchDTO().getBranchName());
        branchOld.setIfsc(updateBranchDTO.getBranchDTO().getIfsc());
        branchRepo.save(branchOld);
        return branchOld;
    }
}
