package com.trinetra.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.trinetra.model.AdminClass;
import com.trinetra.model.Payment;
import com.trinetra.repository.AdminRepository;
import com.trinetra.repository.PaymentRepository;


import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
public class paymentController {
	
	@Autowired
    private PaymentRepository pRepo;
	
	@GetMapping("/payment")
	
	public String paymentPage(HttpSession session, Model model) {
		
		String activeUser =(String) session.getAttribute("activeUser");
		//check if user is logged in
		if(activeUser != null) {
			
			model.addAttribute("Username",activeUser);
			return "payment";
		}
		else {
			model.addAttribute("error","Please Login to access Payment Process");
			return "redirect:/login";
		}
		
	}

	@PostMapping("/paymentbro")
	public String paymentPagePost(@ModelAttribute Payment pay, Model model) {
	    pRepo.save(pay);



	    return "Confirmation";
	}

}
