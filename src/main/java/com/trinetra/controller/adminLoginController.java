package com.trinetra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.trinetra.model.AdminClass;
import com.trinetra.repository.AdminRepository;

@Controller
public class adminLoginController {
	


		@Autowired
		private AdminRepository aRepo;
		
		
		@GetMapping("/adminLogin")
		public String loginPage()
		{
			return "adminLogin";
		}
		
		
		
		@PostMapping("/adminLogin")
		
		public String LoginForm(@ModelAttribute AdminClass ac) {
			
			String username = ac.getUsername();
			
			String password = ac.getPassword();
			
			
			
		boolean result = aRepo.existsByUsernameAndPassword(username, password);
		
		if (result) {
	        return "Dashboard";
	    } else {
	        return "adminLogin";
	    }
			
		}
}



