package com.trinetra.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trinetra.model.ChatMessage;
import com.trinetra.service.ChatMessageService;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "*")
public class ChatController {
    
    @Autowired
    private ChatMessageService chatMessageService;
    
    // Save chat message
    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveMessage(@RequestBody Map<String, String> request) {
        try {
            String userQuestion = request.get("userQuestion");
            String aiResponse = request.get("aiResponse");
            String modelUsed = request.get("modelUsed");
            
            ChatMessage saved = chatMessageService.saveMessage(userQuestion, aiResponse, modelUsed);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", saved.getId());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    // Get recent messages (optional - for viewing stored data)
    @GetMapping("/recent")
    public ResponseEntity<List<ChatMessage>> getRecentMessages() {
        try {
            List<ChatMessage> messages = chatMessageService.getRecentMessages();
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}