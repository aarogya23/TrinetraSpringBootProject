package com.trinetra.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	/*
	 * Controller is just like servlet technology 
	 * It handles all the getter and setter functions
	 * with the help of Http Protocal
	 * 
	 */
	@Autowired
	private GameRepository gameRepository;

	
	//lets maintain session
	@GetMapping("/")
	public String myFirstPage(HttpSession session, Model model) {
	   
	    String activeUser = (String) session.getAttribute("activeUser");
	    String activeAdmin = (String) session.getAttribute("activeAdmin");
	    
	    //If user is logged in, redirect to the home page
	    
	    if(activeUser !=null) {
	    	return "redirect:/home";
	    }
	    
	    if(activeAdmin !=null) {
	    	return "redirect:/dashboard";
	    }
	    
	    List<Game> allGames = gameRepository.findAll();
	    // Group games by category without using lambda
	    Map<String, List<Game>> gamesByCategory = new HashMap<>();
	    for (Game game : allGames) {
	        String category = game.getCategory();
	        // Check if category already exists in the map
	        if (gamesByCategory.containsKey(category)) {
	            // If exists, add game to the existing list
	            List<Game> gameList = gamesByCategory.get(category);
	            gameList.add(game);
	        } else {
	            // If not exists, create a new list and put it in the map
	            List<Game> gameList = new ArrayList<>();
	            gameList.add(game);
	            gamesByCategory.put(category, gameList);
	        }
	    }
	    // Add to model
	    model.addAttribute("gamesByCategory", gamesByCategory);
	    return "frontPage"; // → frontPage.html for public users
	}
	
	@GetMapping("/user") 
	public String nextPage() {
	  
	  return "UserList";
	  
	}
	

	
	
	
}