package com.trinetra.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trinetra.model.Game;
import com.trinetra.model.UserClass;
import com.trinetra.repository.AdminRepository;
import com.trinetra.repository.GameRepository;
import com.trinetra.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserRepository uRepo;
    
    @Autowired
    private AdminRepository arepo;

    
    @Autowired
    private GameRepository grepo;
    @GetMapping("/login")
    public String loginPage() {
    	
    	
        return "Login";
    }

  

    @GetMapping("/home")
    public String homePage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("activeUser");

        if (username != null) {
            UserClass user = uRepo.findByUsername(username);
            if (user != null) {
                model.addAttribute("user", user); // send user to Home page
            }
        }
        
        List<Game> allGames = grepo.findAll();

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

        return "Home";
    }


    
    //first lets add session
    @PostMapping("/login")
    public String loginPagePost(@ModelAttribute UserClass uc, Model model, HttpSession session) {

        String username = uc.getUsername();
        String password = uc.getPassword();

        boolean Userresult = uRepo.existsByUsernameAndPassword(username, password);

        
        boolean Adminresult = arepo.existsByUsernameAndPassword(username, password);
        if (Userresult) {
        	
        	session.setAttribute("activeUser", username);
        	
        	//set session timeout 
        	
        	session.setMaxInactiveInterval(300);
           
            return "Home";
        }
        else if(Adminresult){
        	
        	session.setAttribute("activeAdmin", username);
        	
        	//set session timeout
        	session.setMaxInactiveInterval(300);
        	
        	return "Dashboard";
        	
        }
        else {
            model.addAttribute("error", "Invalid username or password");
            return "Login";
        }
    }
    
    //adding logout functionality too
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	session.invalidate();
    	return "Login";
    }

    // for profile
    @GetMapping("profile")
    public String profilePage(HttpSession session, Model model)
    {
    	String username = (String) session.getAttribute("activeUser");
    	
    	if(username == null) {
    		model.addAttribute("error","User not found in databases");
    		return "Login";
    		
    	}
    	
    	//fetching the user data from the database if found then
    	
    	UserClass user = uRepo.findByUsername(username);
    	
    	if(user == null) {
    		model.addAttribute("error","user not found bro");
    		return "Home";
    	}
    	
    	model.addAttribute("user", user);
    	return "profile";
    }
    
    
    @PostMapping("/uploadProfileImage")
    public String uploadProfileImage(@RequestParam("image") MultipartFile file, HttpSession session) {
        String username = (String) session.getAttribute("activeUser");
        if (username == null) {
            return "login";
        }

        UserClass user = uRepo.findByUsername(username);
        if (user == null) {
            return "profile";
        }

        try {
            // Directory to save uploaded images
            String uploadDir = "src/main/resources/static/profile/users/";
            Files.createDirectories(Paths.get(uploadDir));

            // Delete previous image if it exists
            String existingImage = user.getProfileImage();
            if (existingImage != null && !existingImage.isEmpty()) {
                Path oldImagePath = Paths.get("src/main/resources/static" + existingImage);
                if (Files.exists(oldImagePath)) {
                    Files.delete(oldImagePath);
                }
            }

            // Create a unique file name
            String fileName = username + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            // Save the new file
            Files.write(filePath, file.getBytes());

            // Update user's profile image path in the database
            user.setProfileImage("/images/users/" + fileName);
            uRepo.save(user);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "profile";  // redirect to refresh and reflect the new image
    }
   
}
