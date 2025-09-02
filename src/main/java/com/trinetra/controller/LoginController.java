package com.trinetra.controller;

import com.trinetra.model.Game;
import com.trinetra.model.Purchase;
import com.trinetra.model.UserClass;
import com.trinetra.repository.AdminRepository;
import com.trinetra.repository.GameRepository;
import com.trinetra.repository.PurchaseRepository;
import com.trinetra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;

@Controller
public class LoginController {

    @Autowired
    private UserRepository uRepo;
    
    @Autowired
    private AdminRepository arepo;

    @Autowired
    private PurchaseRepository purchaseRepo;
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

 // Display profile page with user data and purchase history
    @GetMapping("/profile")
    public String profilePage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("activeUser");
        System.out.println("Profile - Username from session: " + username);
        if (username == null) {
            model.addAttribute("error", "No active session. Please log in again.");
            return "profile";
        }

        UserClass user = uRepo.findByUsername(username);
        System.out.println("Profile - User found: " + (user != null));
        if (user == null) {
            model.addAttribute("error", "User not found in the database.");
            return "profile";
        }

        List<Purchase> purchases = purchaseRepo.findByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("purchases", purchases);
        return "profile";
    }

    // Handle profile update form
    @PostMapping("/updateProfile")
    public String updateProfile(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "currentPassword", required = true) String currentPassword,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            HttpSession session, Model model) {
        String activeUser = (String) session.getAttribute("activeUser");
        if (activeUser == null) {
            model.addAttribute("error", "Please log in.");
            return "login";
        }

        UserClass user = uRepo.findByUsername(activeUser);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "profile";
        }

        if (!user.getPassword().equals(currentPassword)) {
            model.addAttribute("error", "Incorrect password.");
            model.addAttribute("user", user);
            model.addAttribute("purchases", purchaseRepo.findByUserId(user.getId()));
            return "profile";
        }

        if (username != null && !username.isEmpty()) {
            user.setUsername(username);
        }
        if (email != null && !email.isEmpty()) {
            user.setEmail(email);
        }
        if (country != null && !country.isEmpty()) {
            user.setCountry(country);
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }

        uRepo.save(user);
        model.addAttribute("user", user);
        model.addAttribute("purchases", purchaseRepo.findByUserId(user.getId()));
        model.addAttribute("success", "Profile updated!");
        return "profile";
    }

    // Handle profile image upload
    @PostMapping("/uploadProfileImage")
    public String uploadProfileImage(@RequestParam("image") MultipartFile file, HttpSession session, Model model) {
        String username = (String) session.getAttribute("activeUser");
        System.out.println("Upload - Active User: " + username);
        if (username == null) {
            model.addAttribute("error", "No active session. Please log in again.");
            return "profile";
        }

        UserClass user = uRepo.findByUsername(username);
        System.out.println("Upload - User found before save: " + (user != null));
        if (user == null) {
            model.addAttribute("error", "User not found.");
            model.addAttribute("purchases", purchaseRepo.findByUserId(user.getId()));
            return "profile";
        }

        try {
            // Validate file size (5MB limit)
            long fileSizeInBytes = file.getSize();
            System.out.println("Upload - File size: " + fileSizeInBytes + " bytes");
            if (fileSizeInBytes > 5 * 1024 * 1024) {
                model.addAttribute("error", "File size exceeds 5MB limit.");
                model.addAttribute("user", user);
                model.addAttribute("purchases", purchaseRepo.findByUserId(user.getId()));
                return "profile";
            }

            // Validate file type
            String contentType = file.getContentType();
            System.out.println("Upload - File type: " + contentType);
            if (contentType == null || !contentType.startsWith("image/")) {
                model.addAttribute("error", "Only image files are allowed.");
                model.addAttribute("user", user);
                model.addAttribute("purchases", purchaseRepo.findByUserId(user.getId()));
                return "profile";
            }

            // Convert to Base64 and update user
            byte[] imageBytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            System.out.println("Upload - Base64 length: " + base64Image.length());
            user.setProfileImage(base64Image);
            UserClass savedUser = uRepo.save(user);
            System.out.println("Upload - User saved successfully, ID: " + savedUser.getId());
            model.addAttribute("user", savedUser);
            model.addAttribute("purchases", purchaseRepo.findByUserId(savedUser.getId()));
            model.addAttribute("success", "Image uploaded successfully!");
            return "profile";
        } catch (IOException e) {
            System.out.println("Upload - IOException: " + e.getMessage());
            model.addAttribute("error", "Failed to upload image: " + e.getMessage());
            model.addAttribute("user", user);
            model.addAttribute("purchases", purchaseRepo.findByUserId(user.getId()));
            return "profile";
        } catch (DataIntegrityViolationException e) {
            System.out.println("Upload - DataIntegrityViolationException: " + e.getMessage());
            model.addAttribute("error", "Database error: Image data too large or schema issue. Please try a smaller image.");
            model.addAttribute("user", user);
            model.addAttribute("purchases", purchaseRepo.findByUserId(user.getId()));
            return "profile";
        }
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}