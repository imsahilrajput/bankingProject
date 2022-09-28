package com.perfios.banking.services;

import Util.SortByDate;
import com.perfios.banking.domain.Account;
import com.perfios.banking.domain.Branch;
import com.perfios.banking.domain.Transactions;
import com.perfios.banking.domain.User;
import com.perfios.banking.dto.AccountDTO;
import com.perfios.banking.dto.ChangeBranchDTO;
import com.perfios.banking.dto.GetAccountDTO;
import com.perfios.banking.dto.GetTansactionDTO;
import com.perfios.banking.repository.AccountRepository;
import com.perfios.banking.repository.BranchRepository;
import com.perfios.banking.repository.TransactionRepository;
import com.perfios.banking.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class AccountService {
    @Autowired
    BranchRepository branchRepo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;


    public Account createAccount(AccountDTO accountDTO) {
        User user;
        Branch branch;

        try {
            user = userRepository.findById(accountDTO.getUserId()).get();
        } catch (Exception ex) {
            throw new EntityNotFoundException();
        }

        try {
            branch = branchRepo.findById(accountDTO.getBranchId()).get();
        } catch (Exception ex) {
            throw new EntityNotFoundException();
        }
        Account account = new Account(accountDTO.getMinimumAmount(), accountDTO.getBalance(), user, branch);
        return accountRepository.save(account);
    }

    public Account changeBranch(ChangeBranchDTO changeBranchDTO){
        Account account;
        Branch branch;
        try{
            account = accountRepository.getReferenceById(changeBranchDTO.getAccountNumber());
        }catch(Exception ex){
            throw new EntityNotFoundException();
        }
        try {
            branch = branchRepo.findById(changeBranchDTO.getNewBranchId()).get();
        } catch (Exception ex) {
            throw new EntityNotFoundException();
        }

        account.setBranch(branch);
        return accountRepository.save(account);

    }
    public Account getAccount(Integer accountNumber){
        Account account;
        try{
            account =accountRepository.getReferenceById(accountNumber);
        }catch (Exception ex){
            throw new EntityNotFoundException();
        }
        return account;
    }


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

    public Date lastUpdated(Integer accountNumber){
        List<GetTansactionDTO>  allTransactions = new ArrayList<>();
        try{
            getTransactionsById(accountNumber);
            return allTransactions.get(0).getDate();
        }catch(Exception e){
            return new Date();
        }




    }

    public List<GetAccountDTO> getAccountOfAUser(Integer userId){
        List<GetAccountDTO> accountsDTOList=new ArrayList<>();
        List<Account> accountsList ;
        User user;
        try{
            user=userRepository.findById(userId).get();

        }catch (Exception ex){
            throw new EntityNotFoundException("User does not exists.");
        }

            accountsList=accountRepository.findAllByUser(user);
            if(accountsList.isEmpty()){
                throw new EntityNotFoundException("Account with this user id not found.");
            }
            for(Account account:accountsList){
                GetAccountDTO getAccountDTO =new GetAccountDTO();
                getAccountDTO.setAccountNumber(account.getAccountNumber());
                getAccountDTO.setMinimumAmount(account.getMinimumAmount());
                getAccountDTO.setBalance(account.getBalance());
                getAccountDTO.setUserId(account.getUser().getUserId());
                getAccountDTO.setUserName(account.getUser().getUserName());
                getAccountDTO.setFirstName(account.getUser().getFirstName());
                getAccountDTO.setLastName(account.getUser().getLastName());
                getAccountDTO.setBranchId(account.getBranch().getBranchId());
                getAccountDTO.setBranchName(account.getBranch().getBranchName());
                getAccountDTO.setLastUpdated(lastUpdated(account.getAccountNumber()));
                accountsDTOList.add(getAccountDTO);
            }
            return accountsDTOList;

    }
}
