package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    public Account createAccount(Account account){
        return accountRepository.save(account);
    }
    public boolean usernameExists(String username){
        return accountRepository.findByUsername(username) != null;
    }

    public Account login(String username, String password){
        
        Account account = accountRepository.findByUsername(username);
        if(account != null && account.getPassword().equals(password)){
            return account;
        }
        return null;
    }

    
}  
