package com.trinetra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfilePageController {
	
	 @GetMapping("/profilepage")
	    public String profilePage() {
	    	
	        return "profilebro"; // JSP or HTML view name
	    }

	
}
