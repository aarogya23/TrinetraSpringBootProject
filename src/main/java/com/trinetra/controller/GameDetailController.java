package com.trinetra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.trinetra.model.Game;
import com.trinetra.repository.GameRepository;

@Controller
public class GameDetailController {

    @Autowired
    private GameRepository gameRepository;

    // Static pages
    @GetMapping("/cart")
    public String cart() {
        return "cart"; // cart.html
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
