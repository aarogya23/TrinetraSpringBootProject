package com.trinetra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.trinetra.model.UserClass;
import com.trinetra.repository.UserRepository;

@Controller
public class SignupController {

    @Autowired
    private UserRepository uRepo;

    @GetMapping("/signup")
    public String signupPage() {
        return "Signup"; // JSP or HTML view name
    }

    @PostMapping("/signup")
    public String signupPagePost(@ModelAttribute UserClass ur) {
        uRepo.save(ur);
        return "FrontPage"; // Redirect to front page after signup
    }
}
