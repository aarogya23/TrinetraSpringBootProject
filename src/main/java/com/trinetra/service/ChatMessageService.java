package com.trinetra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trinetra.model.ChatMessage;
import com.trinetra.repository.ChatMessageRepository;

@Service
public class ChatMessageService {
    
    @Autowired
    private ChatMessageRepository repository;
    
    public ChatMessage saveMessage(String userQuestion, String aiResponse, String modelUsed) {
        ChatMessage message = new ChatMessage(userQuestion, aiResponse, modelUsed);
        return repository.save(message);
    }
    
    public List<ChatMessage> getAllMessages() {
        return repository.findAllByOrderByCreatedAtDesc();
    }
    
    public List<ChatMessage> getRecentMessages() {
        return repository.findTop10ByOrderByCreatedAtDesc();
    }
}