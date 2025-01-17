package com.example.service;
import org.springframework.stereotype.Service;

import com.example.exception.*;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
//import javax.transaction.Transactional;
import java.util.Optional;

@Service
//@Transactional
public class AccountService {
    AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /**
     * 1) Create a new Account.
     * @param account json (no id)
     * @return account json with id
     */
    public Account createAccount(Account account){
        // add checks
        if (account.getUsername().isEmpty() || account.getPassword().length() < 4){
            throw new InvalidInputException("Invalid Account");
        } else if (accountRepository.findAccountByUsername(account.getUsername()).isPresent()){
            throw new DuplicatesException("Duplicate Username");
        } else {
            return accountRepository.save(account);
        }
    }

    /**
     * 2) Login to Account.
     * @param account json
     * @return 
     */
    public Account loginAccount(Account account){
        Optional<Account> accountOptional = accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());
        if (accountOptional.isPresent()){
            return accountOptional.get();
        } else {
            throw new InvalidInputException("Invalid Account");
        }
    }
}
