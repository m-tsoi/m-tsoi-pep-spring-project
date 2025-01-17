package com.example.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.entity.*;
import com.example.exception.*;
import com.example.service.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
//@RequestMapping("/8080")
public class SocialMediaController {
    // Add setup here idk autowire the service thingos
    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;


    // 1) POST localhost:8080/register Account
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account newAccount) {
        try{
            Account account = accountService.createAccount(newAccount);
            return ResponseEntity.status(200).body(account);
        } catch (DuplicatesException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); //if user no exist
        } catch (InvalidInputException e){
            return ResponseEntity.status(400).build(); 
        }
    }

    // 2) POST localhost:8080/login Account
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account loginAccount) {
        try{
            Account account = accountService.loginAccount(loginAccount);
            return ResponseEntity.status(200).body(account);
        } catch (InvalidInputException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    // 3) POST localhost:8080/messages Message
    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message newMessage) {
        try{
            Message message = messageService.createMessage(newMessage);
            return ResponseEntity.status(200).body(message);
        } catch (InvalidInputException e){
            return ResponseEntity.status(400).build();
        }
    }
    
    // 4) GET localhost:8080/messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages);
    }


    // 5) GET localhost:8080/messages/{messageId}
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message>  getMessageById(@PathVariable int messageId){
        Message message = messageService.getMessageById(messageId);
        if (message == null){
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(200).body(message);
        }
    }

    // 6) DELETE localhost:8080/messages/{messageId}
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int messageId) {
        Integer rowsDeleted = messageService.deleteMessage(messageId);
        if (rowsDeleted > 0){
            return ResponseEntity.status(200).body(rowsDeleted);
        } else {
            return ResponseEntity.status(200).build();
        }
    }


    // 7) PATCH localhost:8080/messages/{messageId}
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessage(@PathVariable int messageId, @RequestBody Message newMessage) {
        try{
            Integer rowsUpdated = messageService.updateMessage(messageId, newMessage.getMessageText());
            return ResponseEntity.status(200).body(rowsUpdated);
        } catch (InvalidInputException e){
            return ResponseEntity.status(400).build();
        }
    }

    // 8) GET localhost:8080/accounts/{accountId}/messages
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccount(@PathVariable int accountId) {
        List<Message> messages = messageService.getMessagesByPostedBy(accountId);
        return ResponseEntity.status(200).body(messages);
    }
    
    

}
