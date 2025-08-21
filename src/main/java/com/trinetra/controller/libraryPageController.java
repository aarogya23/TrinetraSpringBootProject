package com.trinetra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class libraryPageController {
	
	@GetMapping("/library")
	
	public String libraryPage(HttpSession session, Model model) {
		String activeUser =(String) session.getAttribute("activeUser");
		//check if user is logged in
		if(activeUser != null) {
			
			model.addAttribute("Username",activeUser);	
		return "library";
	}
		else {
			model.addAttribute("error","Please Login to access Payment Process");
			return "redirect:/login";
		}

}
}
