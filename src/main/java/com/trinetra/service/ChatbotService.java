// src/main/java/com/yourpackage/service/ChatbotService.java

package com.trinetra.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatbotService {

    @Value("${deepseek.api.key}")
    private String apiKey;

    @Value("${deepseek.api.url}")
    private String apiUrl;

    @Value("${deepseek.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getResponse(String userMessage) {
        // Prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // Prepare request body (OpenAI-compatible format)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(
            Map.of("role", "system", "content", "You are a helpful assistant."),  // Optional system prompt
            Map.of("role", "user", "content", userMessage)
        ));
        requestBody.put("temperature", 0.7);  // Optional: adjust for creativity
        requestBody.put("max_tokens", 500);   // Optional: limit response length

        // Make the POST request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);

        // Extract the response content
        Map<String, Object> body = response.getBody();
        if (body != null && body.containsKey("choices")) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) body.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            }
        }
        throw new RuntimeException("Failed to get response from DeepSeek API");
    }
}