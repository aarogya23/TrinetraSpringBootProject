package com.trinetra.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.trinetra.model.UserClass;
import com.trinetra.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Value("${image.storage.path:/var/images/}")
    private String imageStoragePath;

    @GetMapping("/users")
    public String showAllUsers(Model model) {
        System.out.println("UserController: /users endpoint called");
        List<UserClass> userList = userRepository.findAll();
        System.out.println("Found " + userList.size() + " users");

        for (UserClass user : userList) {
            System.out.println("User: " + user.getUsername() + 
                             " - Email: " + user.getEmail() + 
                             " - Country: " + user.getCountry() +
                             " - Profile Image: " + (user.getProfileImage() != null ? "[Image Present]" : "[No Image]"));
        }

        model.addAttribute("userList", userList);
        return "UserList";
    }

    @GetMapping("/api/users")
    @ResponseBody
    public List<UserClass> getAllUsersApi() {
        System.out.println("UserController: /api/users API endpoint called");
        List<UserClass> userList = userRepository.findAll();
        System.out.println("API: Found " + userList.size() + " users");

        // Set password to null to exclude it from JSON response
        for (UserClass user : userList) {
            user.setPassword(null);
            System.out.println("API User: " + user.getUsername() + 
                             " - Email: " + user.getEmail() + 
                             " - Country: " + user.getCountry() +
                             " - Profile Image: " + (user.getProfileImage() != null ? "[Image Present]" : "[No Image]"));
        }

        return userList;
    }

    @GetMapping(value = "/api/user/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getUserImage(@PathVariable long id) throws IOException {
        System.out.println("UserController: /api/user/image/" + id + " endpoint called");
        UserClass user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        
        String imagePath = user.getProfileImage();
        if (imagePath == null || imagePath.isEmpty()) {
            // Return a default image
            return Files.readAllBytes(Paths.get(imageStoragePath + "default.jpg"));
        }

        try {
            return Files.readAllBytes(Paths.get(imageStoragePath + imagePath));
        } catch (IOException e) {
            System.out.println("Error reading image file for user id: " + id + " - " + e.getMessage());
            return Files.readAllBytes(Paths.get(imageStoragePath + "default.jpg"));
        }
    }

    @GetMapping("/users/count")
    public String showUserCount(Model model) {
        long totalUsers = userRepository.count();
        model.addAttribute("totalUsers", totalUsers);
        return "UserCount";
    }

    @GetMapping("/api/users/count")
    @ResponseBody
    public long getUserCountApi() {
        System.out.println("UserController: /api/users/count API endpoint called");
        long totalUsers = userRepository.count();
        System.out.println("API: Total users count: " + totalUsers);
        return totalUsers;
    }
}