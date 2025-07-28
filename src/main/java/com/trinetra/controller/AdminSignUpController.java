package com.trinetra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.trinetra.model.AdminClass;
import com.trinetra.repository.AdminRepository;
import com.trinetra.repository.UserRepository;

@Controller
public class AdminSignUpController {
	
	@Autowired
    private AdminRepository aRepo;
	
	@GetMapping("/adminsignup")
    public String signupPage() {
        return "adminSignUp"; // JSP or HTML view name
    }

    @PostMapping("/adminSigUp")
    public String signupPagePost(@ModelAttribute AdminClass ac) {
        aRepo.save(ac);
        return "FrontPage"; // Redirect to front page after signup
    }

}
