package com.trinetra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class wishlist {
	
	@GetMapping("/wish")
	public String Mapping() {
		
		return "wishlist";
	}
	
	

}
