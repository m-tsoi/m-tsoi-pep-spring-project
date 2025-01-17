package com.example.service;
import org.springframework.stereotype.Service;

import com.example.exception.*;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.transaction.Transactional;

import java.util.List;

@Service
@Transactional
public class MessageService {

    private AccountRepository accountRepository;
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    /**
     * 3) Create a new Message.
     * @param message json (no id)
     * @return message json with id
     */
    public Message createMessage(Message message){
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255 || !accountRepository.findAccountByAccountId(message.getPostedBy()).isPresent()){
            throw new InvalidInputException("Invalid Account");
        } else {
            return messageRepository.save(message);
        }
    }

    /**
     * 4) Get all Messages
     * @param none
     * @return list of messages json
     */
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }

    /**
     * 5) Get Message by Id
     * @param long id
     * @return message if exist or null (will give no body)
     */
    public Message getMessageById(int messageId){
        return messageRepository.findByMessageId(messageId).orElse(null);
    }

    /**
     * 6) delete message
     * @param long id
     * @return deleted int rows
     */
    public Integer deleteMessage(int messageId){
        long before = messageRepository.count();
        messageRepository.deleteByMessageId(messageId);
        long after = messageRepository.count();
        return (int)(before - after);
    }

    /**
     * 7) update message
     * @param long id
     * @return updated int rows
     */
    public Integer updateMessage(int messageId, String newMessageText){
        if (!messageRepository.findByMessageId(messageId).isPresent() || newMessageText.isBlank()|| newMessageText.length() <= 0 || newMessageText.length() > 255){
            throw new InvalidInputException("Invalid Message");
        } else {
            Message message = messageRepository.findByMessageId(messageId).get();
            message.setMessageText(newMessageText);
            messageRepository.save(message);
            return 1;
        }
    }

    /**
     * 8) Get all Messages
     * @param none
     * @return list of messages json
     */
    public List<Message> getMessagesByPostedBy(int accountId){
        return messageRepository.findByPostedBy(accountId);
    }
}
