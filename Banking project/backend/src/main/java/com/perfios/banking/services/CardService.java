package com.perfios.banking.services;


import com.perfios.banking.domain.Account;
import com.perfios.banking.domain.Card;
import com.perfios.banking.domain.User;
import com.perfios.banking.dto.AddCardDTO;
import com.perfios.banking.dto.CardDTOResponse;
import com.perfios.banking.repository.AccountRepository;
import com.perfios.banking.repository.CardRepository;
import com.perfios.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    UserRepository userRepository;


    public CardDTOResponse addCard(AddCardDTO addCardDTO) {
        Account account;
        User user;
        Card card = new Card();
        CardDTOResponse cardDTOResponse = new CardDTOResponse();

        try {
            account = accountRepository.getReferenceById(addCardDTO.getAccountNumber());
            user = accountRepository.getReferenceById(addCardDTO.getAccountNumber()).getUser();

        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("Account not found");
        }

        card.setPin(addCardDTO.getPin());
        card.setCardType(addCardDTO.getCardType());
        card.setDateOfIssue(new Date());
        card.setStatus("APPLIED");
        if (addCardDTO.getCardType().equals("creditCard")) {
            Double cardLimit = account.getBalance() * 10;

            card.setCardLimit(cardLimit);
            card.setFrequency(2);
            card.setLimitUsed((double) 0);
            card.setRoi(12);
            card.setBalance(card.getCardLimit() - card.getLimitUsed());

        } else {
            card.setCardLimit(null);
            card.setFrequency(null);
            card.setLimitUsed(null);
            card.setRoi(null);
            card.setBalance(account.getBalance());
        }

        card.setAccount(account);

        cardRepository.save(card);

        cardDTOResponse.setCardNumber(card.getCardNumber());
        cardDTOResponse.setAccountNumber(card.getAccount().getAccountNumber());
        cardDTOResponse.setCardLimit(card.getCardLimit());
        cardDTOResponse.setPin(card.getPin());
        cardDTOResponse.setCardType(card.getCardType());
        cardDTOResponse.setLimitUsed(card.getLimitUsed());
        cardDTOResponse.setBalance(card.getBalance());
        cardDTOResponse.setRoi(card.getRoi());
        cardDTOResponse.setStatus(card.getStatus());
        cardDTOResponse.setDateOfIssue(card.getDateOfIssue());
        cardDTOResponse.setFrequency(card.getFrequency());
        cardDTOResponse.setUserId(card.getAccount().getUser().getUserId());

        return cardDTOResponse;


    }

    public List<Card> checkForCards(Integer userId) {
        User user;
        List<Account> accountList=new ArrayList<>();
        List<Card> cardList = new ArrayList<>();
        try {
            user = userRepository.findById(userId).get();
        } catch (EntityNotFoundException ex) {
            throw new EntityNotFoundException("User with user id" + userId + "does not exist.");
        }
        accountList.addAll(accountRepository.findAllByUser(user));
        for(Account account :accountList){
            cardList.addAll(cardRepository.findAllByAccount(account));
        }
        return cardList;
    }

    public CardDTOResponse approveCard(Integer cardNumber){
        Card card;
        CardDTOResponse cardDTOResponse = new CardDTOResponse();
        try{
            card=cardRepository.getReferenceById(cardNumber);
        }catch(EntityNotFoundException ex){
            throw new EntityNotFoundException("Card not found. ");
        }
        if (card.getStatus().equals("APPLIED")) {
            card.setStatus("ACTIVE");
        }
        cardRepository.save(card);
        cardDTOResponse.setCardNumber(card.getCardNumber());
        cardDTOResponse.setAccountNumber(card.getAccount().getAccountNumber());
        cardDTOResponse.setCardLimit(card.getCardLimit());
        cardDTOResponse.setPin(card.getPin());
        cardDTOResponse.setCardType(card.getCardType());
        cardDTOResponse.setLimitUsed(card.getLimitUsed());
        cardDTOResponse.setBalance(card.getBalance());
        cardDTOResponse.setRoi(card.getRoi());
        cardDTOResponse.setStatus(card.getStatus());
        cardDTOResponse.setDateOfIssue(card.getDateOfIssue());
        cardDTOResponse.setFrequency(card.getFrequency());
        cardDTOResponse.setUserId(card.getAccount().getUser().getUserId());

        return cardDTOResponse;


    }



}

