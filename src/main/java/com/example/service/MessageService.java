package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        if (message.getMessageText() == null ||
            message.getMessageText().trim().isBlank() ||
            message.getMessageText().length() > 255) {
            return null;
        }
        if (message.getPostedBy() == null || !accountRepository.existsById(message.getPostedBy())) {
             return null;
            }
            return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Integer messageId) {
        Optional<Message> findMessage = messageRepository.findById(messageId);
        return findMessage.orElse(null);
    }

    public Integer deleteMessageById(Integer messageId) {
        return messageRepository.deleteByMessageId(messageId);
    }

    public int updateMessageText(Integer messageId, String newText) {
        
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (!existingMessage.isPresent()) {
            return 0;
        }
        return messageRepository.updateMessageText(messageId, newText);
    }
        
    public List<Message> getMessagesByUser(Integer accountId) {
        return messageRepository.findByPostedBy(accountId);
    }



}
