package com.trinetra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class aiAskController {
	
	//lets maintain session here too
	@GetMapping("/aiask") 
	public String nextPage(HttpSession session, Model model) {
	  
		String activeUser = (String) session.getAttribute("activeUser");
		
		//Checking if user is logged in
		
		if(activeUser != null) {
			//user is logged in, allowed acess to Ai ask page
			model.addAttribute("username",activeUser);
			return "aiask";
		}
		else {
			//No user is logged in, redirect to login page
			model.addAttribute("error","Please Login to access ti Ai Assistant");
			
			return "redirect:/login";
		}
	}

}
