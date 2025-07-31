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

import ch.qos.logback.core.model.Model;

@Controller
public class paymentController {
	
	@Autowired
    private PaymentRepository pRepo;
	
	@GetMapping("/payment")
	
	public String paymentPage() {
		return "payment";
	}

	@PostMapping("/paymentbro")
	public String paymentPagePost(@ModelAttribute Payment pay, Model model) {
	    pRepo.save(pay);



	    return "Confirmation";
	}

}
