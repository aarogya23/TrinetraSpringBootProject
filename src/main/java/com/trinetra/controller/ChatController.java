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
    public String askQuestion(@RequestParam String question, 
                              @RequestParam(required = false) String imageUrl) {
        try {
            if ((question == null || question.trim().isEmpty()) && 
                (imageUrl == null || imageUrl.trim().isEmpty())) {
                return "Please enter a valid question or provide an image URL.";
            }

            // Call OpenRouter API
            String answer = callOpenRouterAPI(question.trim(), imageUrl);

            // Save to DB
            ChatMessage message = new ChatMessage(question, answer);
            chatRepository.save(message);

            return answer;

        } catch (Exception e) {
            System.err.println("Error processing question: " + e.getMessage());
            return "Sorry, I'm having trouble processing your request. Please try again.";
        }
    }

    /**
     * Call OpenRouter API (supports text + image input)
     */
    private String callOpenRouterAPI(String question, String imageUrl) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + API_KEY);
            headers.set("Content-Type", "application/json");
            headers.set("HTTP-Referer", "http://localhost:8081");
            headers.set("X-Title", "Trinetra Chat App");

            // Message content (text + optional image)
            List<Map<String, Object>> content = new java.util.ArrayList<>();

            if (question != null && !question.isEmpty()) {
                content.add(Map.of("type", "text", "text", question));
            }
            if (imageUrl != null && !imageUrl.isEmpty()) {
                content.add(Map.of(
                    "type", "image_url",
                    "image_url", Map.of("url", imageUrl)
                ));
            }

            // Request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "openrouter/horizon-beta"); // same as python example
            requestBody.put("messages", List.of(
                Map.of("role", "user", "content", content)
            ));
            requestBody.put("max_tokens", 1000);
            requestBody.put("temperature", 0.7);

            // HTTP entity
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // API call
            Map<String, Object> response = restTemplate.postForObject(
                "https://openrouter.ai/api/v1/chat/completions",
                request,
                Map.class
            );

            // Extract answer
            if (response != null && response.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) choice.get("message");
                    String contentResp = (String) message.get("content");
                    return contentResp != null ? contentResp : "I couldn't generate a response.";
                }
            }

            return "No response received from AI service.";

        } catch (Exception e) {
            System.err.println("API call failed: " + e.getMessage());
            throw new RuntimeException("Failed to get response from AI service", e);
        }
    }
}
