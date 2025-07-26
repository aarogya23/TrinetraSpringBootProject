package com.trinetra.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trinetra.model.UserClass;
import com.trinetra.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        System.out.println("UserController: /users endpoint called");
        List<UserClass> userList = userRepository.findAll();
        System.out.println("Found " + userList.size() + " users");

        // Debug: print each user
        for(UserClass user : userList) {
            System.out.println("User: " + user.getUsername() + 
                             " - " + user.getEmail() + 
                             " - Password: " + user.getPassword() + 
                             " - Country: " + user.getCountry());
        }

        model.addAttribute("userList", userList);
        return "UserList"; // This will resolve to UserList.jsp
    }

    // API endpoint to fetch users as JSON for HTML page
    @GetMapping("/api/users")
    @ResponseBody
    public List<UserClass> getAllUsersApi() {
        System.out.println("UserController: /api/users API endpoint called");
        List<UserClass> userList = userRepository.findAll();
        System.out.println("API: Found " + userList.size() + " users");
        
        // Debug: print user data
        for(UserClass user : userList) {
            System.out.println("API User: " + user.getUsername() + 
                             " - " + user.getEmail() + 
                             " - Password: " + user.getPassword() + 
                             " - Country: " + user.getCountry());
        }
        
        return userList; // This will automatically convert to JSON
    }

    @GetMapping("/users/count")
    public String showUserCount(Model model) {
        long totalUsers = userRepository.count();
        model.addAttribute("totalUsers", totalUsers);
        return "UserCount"; // Optional: separate page for user count
    }

    // API endpoint to get user count as JSON
    @GetMapping("/api/users/count")
    @ResponseBody
    public long getUserCountApi() {
        System.out.println("UserController: /api/users/count API endpoint called");
        long totalUsers = userRepository.count();
        System.out.println("API: Total users count: " + totalUsers);
        return totalUsers;
    }
}