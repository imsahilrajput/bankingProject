package com.perfios.banking.repository;

import com.perfios.banking.domain.Account;
import com.perfios.banking.domain.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transaction;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions,Integer> {
    List<Transactions> findByDebit(Account debit);
    List<Transactions> findByCredit(Account credit);
}
