package com.trinetra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.trinetra.model.UserClass;
import com.trinetra.repository.UserRepository;

@Controller
public class LoginController {
    
    @Autowired
    private UserRepository uRepo;
    
    @GetMapping("/login")
    public String loginPage() {
    	
        return "Login"; // JSP or HTML view name
    }
    
    @PostMapping("/login")
    public String loginPagePost(@RequestParam String username, 
                               @RequestParam String password, 
                               Model model) {
        
        // Find user by username
        UserClass user = uRepo.findByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            // Login successful
            return "Home"; // Redirect to dashboard or home page
        } else {
            // Login failed
            model.addAttribute("error", "Invalid username or password");
            return "Login"; // Return to login page with error
        }
    }
}