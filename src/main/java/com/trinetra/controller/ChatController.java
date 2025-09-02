package com.trinetra.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.trinetra.model.ChatMessage;
import com.trinetra.repository.ChatMessageRepository;

@Controller
@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private ChatMessageRepository chatRepository;

    /**
     * Show chat page
     */
    @GetMapping("/modelchat")
    public String home(Model model) {
        return "testapi";
    }

    /**
     * Save chat message to MySQL database
     */
    @PostMapping("/api/save-message")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveMessage(@RequestBody Map<String, Object> data) {
        Map<String, Object> response = new HashMap<>();

        try {
            String question = (String) data.get("question");
            String answer = (String) data.get("answer");
            String model = (String) data.get("model");
            Boolean hasImage = (Boolean) data.get("hasImage");
            String imageUrl = (String) data.get("imageUrl");
            Long responseTime = data.get("responseTime") != null ? Long.parseLong(data.get("responseTime").toString()) : null;

            ChatMessage message = new ChatMessage();
            message.setQuestion(question);
            message.setAnswer(answer);
            message.setModel(model);
            message.setHasImage(hasImage);
            message.setImageUrl(imageUrl);
            message.setResponseTime(responseTime);
            message.setCreatedAt(LocalDateTime.now());

            ChatMessage savedMessage = chatRepository.save(message);

            response.put("success", true);
            response.put("message", "Saved successfully");
            response.put("messageId", savedMessage.getId());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Failed to save: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Get chat history
     */
    @GetMapping("/api/history")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getHistory() {
        try {
            List<ChatMessage> messages = chatRepository.findAll();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("messages", messages);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("error", "Failed to load history");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    /**
     * Clear chat history
     */
    @PostMapping("/api/clear-history")
    @ResponseBody
    public ResponseEntity<Map<String, String>> clearHistory() {
        try {
            chatRepository.deleteAll();
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }
}