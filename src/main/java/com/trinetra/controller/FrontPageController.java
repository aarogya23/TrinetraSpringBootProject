package com.trinetra.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.trinetra.model.Game;
import com.trinetra.model.UserClass;
import com.trinetra.repository.GameRepository;
import com.trinetra.repository.UserRepository;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class FrontPageController {

    @Autowired
    private UserRepository urepo;

    @Autowired
    private GameRepository gameRepository;

    // Main front page with session management and sorting
    @GetMapping("/")
    public String myFirstPage(HttpSession session, Model model, @RequestParam(required = false) String sort) {
        String activeUser = (String) session.getAttribute("activeUser");
        String activeAdmin = (String) session.getAttribute("activeAdmin");

        // Redirect if user or admin is logged in
        if (activeUser != null) {
            return "redirect:/home";
        }
        if (activeAdmin != null) {
            return "redirect:/dashboard";
        }

        // Fetch all games
        List<Game> allGames = new ArrayList<>(gameRepository.findAll());

        // Apply sorting if a sort parameter is provided
        if (sort != null && !sort.isEmpty()) {
            switch (sort.toLowerCase()) {
                case "category":
                    allGames.sort((g1, g2) -> g1.getCategory().compareToIgnoreCase(g2.getCategory()));
                    break;
                case "category-desc": // Added Z to A category sorting
                    allGames.sort((g1, g2) -> g2.getCategory().compareToIgnoreCase(g1.getCategory()));
                    break;
                case "price-asc":
                    allGames.sort((g1, g2) -> g1.getGameprice().compareTo(g2.getGameprice()));
                    break;
                case "price-desc":
                    allGames.sort((g1, g2) -> g2.getGameprice().compareTo(g1.getGameprice()));
                    break;
                case "name":
                    allGames.sort((g1, g2) -> g1.getGamename().compareToIgnoreCase(g2.getGamename()));
                    break;
                default:
                    // No sorting if invalid option
                    break;
            }
        }

        // Group games by category
        Map<String, List<Game>> gamesByCategory = new HashMap<>();
        for (Game game : allGames) {
            String category = game.getCategory();
            if (gamesByCategory.containsKey(category)) {
                gamesByCategory.get(category).add(game);
            } else {
                List<Game> gameList = new ArrayList<>();
                gameList.add(game);
                gamesByCategory.put(category, gameList);
            }
        }

        // Sort categories in Z to A order if category-desc is selected
        if ("category-desc".equalsIgnoreCase(sort)) {
            Map<String, List<Game>> sortedGamesByCategory = new TreeMap<>(Comparator.reverseOrder());
            sortedGamesByCategory.putAll(gamesByCategory);
            gamesByCategory = sortedGamesByCategory;
        }

        // Add to model
        model.addAttribute("gamesByCategory", gamesByCategory);
        model.addAttribute("selectedSort", sort != null ? sort : "default"); // For dropdown state
        return "frontPage";
    }
    
    @GetMapping("/user") 
    public String nextPage() {
        return "UserList";
    }
}