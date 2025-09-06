package com.trinetra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.trinetra.model.Game;
import com.trinetra.repository.GameRepository;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Controller
public class GameDetailController {

    @Autowired
    private GameRepository gameRepository;

    
    private static final Logger logger = LoggerFactory.getLogger(GameDetailController.class);
    // Static pages
    @GetMapping("/cart")
    public String cart(HttpSession session, Model model) {
        // Check for active user session
        String username = (String) session.getAttribute("activeUser");
        logger.debug("Cart - Username from session: {}", username);
        if (username == null) {
            logger.info("No active session, redirecting to login");
            model.addAttribute("error", "Please log in to view your cart.");
            return "redirect:/login";
        }

        // Optionally, fetch cart items for the user (requires PurchaseRepository)
        // Example: model.addAttribute("cartItems", purchaseRepository.findByUserId(user.getId()));
        return "cart"; // Maps to cart.html in templates
    }

    @GetMapping("/eafc25")
    public String eafc25() {
        return "eafc25"; // eafc25.html
    }

    @GetMapping("/assassinCreed")
    public String assassinCreed() {
        return "AssassinCreedShadow"; // AssassinCreedShadow.html
    }

   
}


