package com.trinetra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class aiAskController {
	
	@GetMapping("/aiask") 
	public String nextPage() {
	  
	  return "aiAsk";
	}

}
