package com.trinetra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.trinetra.model.UserClass;
import com.trinetra.repository.UserRepository;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class FrontPageController {

	@Autowired
	private UserRepository urepo;
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