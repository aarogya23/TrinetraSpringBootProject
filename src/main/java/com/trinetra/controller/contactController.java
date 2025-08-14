package com.trinetra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class contactController {
	
	@GetMapping("/contact")
	
	public String contactPage() {
		return "contact";
	}

}
