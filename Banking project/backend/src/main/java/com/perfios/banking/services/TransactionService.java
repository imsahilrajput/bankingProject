package com.perfios.banking.services;


import Util.SortByDate;
import com.perfios.banking.domain.Account;
import com.perfios.banking.domain.Transactions;
import com.perfios.banking.domain.User;
import com.perfios.banking.dto.*;
import com.perfios.banking.repository.AccountRepository;
import com.perfios.banking.repository.TransactionRepository;
import com.perfios.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    public TransactionResponseDTO transaction(TransactionDTO transactionDTO) {
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        Account accountDebited;
        Account accountCredited;
        try {
             accountDebited = accountRepository.findById(transactionDTO.getDebit()).get();
             accountCredited = accountRepository.findById(transactionDTO.getCredit()).get();



        } catch (Exception e) {
            throw new EntityNotFoundException("failed");
        }
        Double accountDebitedBalance = accountDebited.getBalance();

        if (accountDebitedBalance < transactionDTO.getAmount()) {
            throw new IllegalStateException("Low Balance");
        }

        accountDebitedBalance = accountDebitedBalance - transactionDTO.getAmount();
        accountDebited.setBalance(accountDebitedBalance);

        Double accountCreditedBalance = accountCredited.getBalance();
        accountCreditedBalance = accountCreditedBalance + transactionDTO.getAmount();
        accountCredited.setBalance(accountCreditedBalance);
        System.out.println(accountDebited.getAccountNumber());
        System.out.println(accountCredited.getAccountNumber());

        Transactions transactions = new Transactions(transactionDTO.getTransactionType(), transactionDTO.getAmount(), accountDebited, accountCredited, new Date());

        transactionRepository.save(transactions);

        accountRepository.saveAndFlush(accountDebited);
        accountRepository.saveAndFlush(accountCredited);
        transactionResponseDTO.setMessage("Successful");

        return transactionResponseDTO;
    }

    public Transactions withdraw(WithdrawDTO withdrawDTO) {
        Double balance = accountRepository.findById(withdrawDTO.getDebit()).get().getBalance();
        if (balance < withdrawDTO.getAmount()) {
            throw new IllegalStateException("Low Balance");
        }
        balance = balance - withdrawDTO.getAmount();

        Account account = accountRepository.findById(withdrawDTO.getDebit()).get();
        account.setBalance(balance);
        accountRepository.save(account);

        Transactions transactions = new Transactions("ATM Withdrawl", withdrawDTO.getAmount(), account, new Date());
        return transactionRepository.save(transactions);


    }

    public DepositResponseDTO deposit(DepositDTO depositDTO) {
        DepositResponseDTO depositResponseDTO = new DepositResponseDTO();
        Double balance = accountRepository.findById(depositDTO.getCredit()).get().getBalance();
        balance = balance + depositDTO.getAmount();
        Account account = accountRepository.findById(depositDTO.getCredit()).get();
        account.setBalance(balance);
        accountRepository.save(account);

        Transactions transactions = new Transactions("Bank Deposit", account, depositDTO.getAmount(), new Date());
        transactionRepository.save(transactions);
        depositResponseDTO.setMessage("successful");
        return depositResponseDTO;
    }


    public List<GetTansactionDTO> getTransactions() {
        List<Transactions> transactionsList;
        transactionsList = transactionRepository.findAll();
        List<GetTansactionDTO> getTansactionDTOList = new ArrayList<>();


        for (Transactions transactions : transactionsList) {

            GetTansactionDTO getTansactionDTO = new GetTansactionDTO();
            getTansactionDTO.setTransactionId(transactions.getTransactionId());
            getTansactionDTO.setAmount(transactions.getAmount());
            getTansactionDTO.setTransactionType(transactions.getTransactionType());

            //if deposited by user by hand
            if (transactions.getCredit() != null && transactions.getDebit() == null) {
                getTansactionDTO.setCreditAccountNumber(transactions.getCredit().getAccountNumber());
                getTansactionDTO.setDebitAccountNumber(null);
                getTansactionDTO.setDeposit(true);
                getTansactionDTO.setWithdraw(false);
                getTansactionDTO.setTransfer(false);
            } else if (transactions.getDebit() != null && transactions.getCredit() == null) {//if withdrawn by user by hand
                getTansactionDTO.setDebitAccountNumber(transactions.getDebit().getAccountNumber());
                getTansactionDTO.setCreditAccountNumber(null);
                getTansactionDTO.setWithdraw(true);
                getTansactionDTO.setDeposit(false);
                getTansactionDTO.setTransfer(false);
            } else if (transactions.getDebit() != null && transactions.getCredit() != null) {//transfer
                getTansactionDTO.setDebitAccountNumber(transactions.getDebit().getAccountNumber());
                getTansactionDTO.setCreditAccountNumber(transactions.getCredit().getAccountNumber());
                getTansactionDTO.setDeposit(false);
                getTansactionDTO.setWithdraw(false);
                getTansactionDTO.setTransfer(true);

            }
            getTansactionDTO.setDate(transactions.getDate());


            getTansactionDTOList.add(getTansactionDTO);
        }
        return getTansactionDTOList;


    }
//    public List<GetTansactionDTO> getTransactionOfAUser(Integer userId){
//        try{
//            User user = userRepository.findById(userId).get();
//        }catch (EntityNotFoundException ex){
//            throw new EntityNotFoundException();
//        }
//
//    }

    public List<GetTansactionDTO> getTransactionsById(Integer accountNumber) {
        try {
            Account account = accountRepository.getReferenceById(accountNumber);
            Integer accNumber = account.getAccountNumber();
        } catch (Exception ex) {
            throw new EntityNotFoundException();
        }
        List<Transactions> debitList;
        List<Transactions> creditList;
        List<Transactions> transactionsList = new ArrayList<>();
        debitList = transactionRepository.findByDebit(accountRepository.findById(accountNumber).get());
        creditList = transactionRepository.findByCredit(accountRepository.findById(accountNumber).get());

        transactionsList.addAll(creditList);
        transactionsList.addAll(debitList);

        List<GetTansactionDTO> getTansactionDTOList = new ArrayList<>();

        System.out.println(transactionsList.size());
        for (Transactions transactions : transactionsList) {

            GetTansactionDTO getTansactionDTO = new GetTansactionDTO();
            getTansactionDTO.setTransactionId(transactions.getTransactionId());
            getTansactionDTO.setAmount(transactions.getAmount());
            getTansactionDTO.setTransactionType(transactions.getTransactionType());

            //if deposited by user by hand
            if (transactions.getCredit() != null && transactions.getDebit() == null) {
                getTansactionDTO.setCreditAccountNumber(transactions.getCredit().getAccountNumber());
                getTansactionDTO.setDebitAccountNumber(null);
                getTansactionDTO.setDeposit(true);
                getTansactionDTO.setWithdraw(false);
                getTansactionDTO.setTransfer(false);
            } else if (transactions.getDebit() != null && transactions.getCredit() == null) {//if withdrawn by user by hand
                getTansactionDTO.setDebitAccountNumber(transactions.getDebit().getAccountNumber());
                getTansactionDTO.setCreditAccountNumber(null);
                getTansactionDTO.setWithdraw(true);
                getTansactionDTO.setDeposit(false);
                getTansactionDTO.setTransfer(false);
            } else if (transactions.getDebit() != null && transactions.getCredit() != null) {//transfer
                getTansactionDTO.setDebitAccountNumber(transactions.getDebit().getAccountNumber());
                getTansactionDTO.setCreditAccountNumber(transactions.getCredit().getAccountNumber());
                getTansactionDTO.setDeposit(false);
                getTansactionDTO.setWithdraw(false);
                getTansactionDTO.setTransfer(true);

            }
            getTansactionDTO.setDate(transactions.getDate());


            getTansactionDTOList.add(getTansactionDTO);
        }
        Collections.sort(getTansactionDTOList, new SortByDate());

        return getTansactionDTOList;

    }

    public List<GetTansactionDTO> getTansactionDTOListOfAUser(Integer userId) {
        List<GetAccountDTO> Accounts = new ArrayList<>();
        List<GetTansactionDTO> transationList;
        List<GetTansactionDTO> finalTransactionList = new ArrayList<>();
        List<Integer> transactionIdList = new ArrayList<>();

        try {
            User user = userRepository.getReferenceById(userId);
            Accounts = accountService.getAccountOfAUser(userId);//List of accounts of that user

        } catch (Exception ex) {
            throw new EntityNotFoundException();
        }
        for (GetAccountDTO account : Accounts) {
            transationList = getTransactionsById(account.getAccountNumber());
            //List of transactions from each account
            for(GetTansactionDTO transactionDTO : transationList){
                finalTransactionList.add(transactionDTO);
            }
        }
        Set<GetTansactionDTO> set = new LinkedHashSet<>();
        set.addAll(finalTransactionList);
        finalTransactionList.clear();
        finalTransactionList.addAll(set);

        Collections.sort(finalTransactionList, new SortByDate());

        return finalTransactionList;
    }


}
