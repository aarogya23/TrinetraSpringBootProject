package com.trinetra.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.trinetra.model.ChatMessage;
import com.trinetra.repository.ChatMessageRepository;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageRepository chatRepository;

    // Replace with your actual OpenRouter API key
    private final String API_KEY = "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";

    /**
     * Home page - displays chat interface with message history
     */
    @GetMapping("/modelchat")
    public String home(Model model) {
        List<ChatMessage> messages = chatRepository.findAll();
        model.addAttribute("messages", messages);
        return "testapi";
    }

    /**
     * Handle chat questions via AJAX
     */
    @PostMapping("/ask")
    @ResponseBody
    public String askQuestion(@RequestParam String question) {
        try {
            // Validate input
            if (question == null || question.trim().isEmpty()) {
                return "Please enter a valid question.";
            }

            // Call OpenRouter API
            String answer = callOpenRouterAPI(question.trim());

            // Save to database
            ChatMessage message = new ChatMessage(question.trim(), answer);
            chatRepository.save(message);

            return answer;

        } catch (Exception e) {
            System.err.println("Error processing question: " + e.getMessage());
            return "Sorry, I'm having trouble processing your request. Please try again.";
        }
    }

    /**
     * Call OpenRouter API to get AI response
     */
    private String callOpenRouterAPI(String question) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Set up headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_KEY);
            headers.set("Content-Type", "application/json");
            headers.set("HTTP-Referer", "http://localhost:8081");
            headers.set("X-Title", "Trinetra Chat App");

            // Create request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "deepseek/deepseek-chat-v3-0324:free");
            requestBody.put("messages", List.of(
                Map.of("role", "user", "content", question)
            ));
            requestBody.put("max_tokens", 1000);
            requestBody.put("temperature", 0.7);

            // Create HTTP entity
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Make API call
            Map<String, Object> response = restTemplate.postForObject(
                "https://openrouter.ai/api/v1/chat/completions", 
                request, 
                Map.class
            );

            // Extract and return the answer
            if (response != null && response.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) choice.get("message");
                    String content = (String) message.get("content");
                    return content != null ? content : "I'm sorry, I couldn't generate a response.";
                }
            }

            return "No response received from AI service.";

        } catch (Exception e) {
            System.err.println("API call failed: " + e.getMessage());
            throw new RuntimeException("Failed to get response from AI service", e);
        }
    }
}