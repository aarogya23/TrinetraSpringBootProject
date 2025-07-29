package com.trinetra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class libraryPageController {
	
	@GetMapping("/library")
	
	public String libraryPage() {
		
		return "library";
	}

}
