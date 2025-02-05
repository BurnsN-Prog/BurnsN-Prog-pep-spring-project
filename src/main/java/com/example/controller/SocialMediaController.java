package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.entity.Message;
import com.example.service.MessageService;
import java.util.List;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService){
        this.accountService = accountService;
        this.messageService = messageService;
    }
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount) {
         if (newAccount.getUsername() == null || newAccount.getUsername().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (newAccount.getPassword() == null || newAccount.getPassword().length() < 4) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        if (accountService.usernameExists(newAccount.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        Account savedAccount = accountService.createAccount(newAccount);

        return ResponseEntity.ok(savedAccount);      
    }
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account loginRequest) {
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isBlank() ||
            loginRequest.getPassword() == null || loginRequest.getPassword().isEmpty()) {
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Account account = accountService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (account == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(account);
        }

    @PostMapping("/messages")
     public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) {
        Message savedMessage = messageService.createMessage(newMessage);
        if (savedMessage == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    
        return ResponseEntity.ok(savedMessage);
        }
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        
        return ResponseEntity.ok(messages);
    }
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            return ResponseEntity.ok(message);
        }
       return ResponseEntity.ok(null);
    }
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable Integer messageId) {
        Integer rowsDeleted = messageService.deleteMessageById(messageId);
        if (rowsDeleted != null && rowsDeleted > 0) {
            return ResponseEntity.ok(rowsDeleted);
        }
        
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId,
                                           @RequestBody Message updateRequest) {
    
        String newText = updateRequest.getMessageText();
        
        if (newText == null || newText.trim().isEmpty() || newText.length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        int rowsUpdated = messageService.updateMessageText(messageId, newText);
        
        if (rowsUpdated > 0) {
            return ResponseEntity.ok(rowsUpdated);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
     }

     @GetMapping("/accounts/{accountId}/messages")
     public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
         List<Message> messages = messageService.getMessagesByUser(accountId);
         
         return ResponseEntity.ok(messages);
     }








}
