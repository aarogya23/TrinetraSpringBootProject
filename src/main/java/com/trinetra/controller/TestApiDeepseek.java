package com.trinetra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.trinetra.service.ChatbotService;

@RestController
public class TestApiDeepseek {

	

	    @Autowired
	    private ChatbotService chatbotService;
	    
	    @GetMapping("/testmap")
	    public String letsGo() {
	    	
	    	return "testapi";
	    }

	    @PostMapping("/chat")
	    public String chat(@RequestBody String userMessage) {
	        return chatbotService.getResponse(userMessage);
	    }
	}

