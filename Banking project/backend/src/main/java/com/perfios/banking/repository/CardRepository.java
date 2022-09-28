package com.perfios.banking.repository;

import com.perfios.banking.domain.Account;
import com.perfios.banking.domain.Card;
import com.perfios.banking.domain.Transactions;
import com.perfios.banking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CardRepository extends JpaRepository<Card,Integer> {
    List<Card> findAllByAccount(Account account);
}
