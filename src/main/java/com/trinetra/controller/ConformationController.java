package com.trinetra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConformationController {
	
	@GetMapping("/orderConfirmation")
	
	public String OrderConformation() {
		
		return "Confirmation";
	}

}
