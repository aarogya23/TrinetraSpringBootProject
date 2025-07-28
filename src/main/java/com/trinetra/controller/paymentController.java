package com.trinetra.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class paymentController {
	
	@GetMapping("/payment")
	
	public String paymentPage() {
		return "payment";
	}

}
