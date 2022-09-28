package com.perfios.banking.repository;

import com.perfios.banking.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Integer> {

    Branch findByBranchName(String branchName);

}
