package com.trinetra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontPageController {

	/*
	 * Controller is just like servlet technology 
	 * It handles all the getter and setter functions
	 * with the help of Http Protocal
	 * 
	 */
	@GetMapping("/")
	public String myFirstPage() {
		return "frontPage";
	}
	
	@GetMapping("/user") 
	public String nextPage() {
	  
	  return "UserList";
	}
	
	
}