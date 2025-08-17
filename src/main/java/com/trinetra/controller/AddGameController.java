package com.trinetra.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.trinetra.model.Game;
import com.trinetra.repository.GameRepository;

@Controller
public class AddGameController {

	@Autowired
	private GameRepository gameRepo;

	// Show add game page
	@GetMapping("/addgamebro")
	public String addGamePage() {
		return "Games"; // JSP or HTML view name
	}

	// Handle game form submission with image
	@PostMapping("/addGame")
	public String addGamePost(@ModelAttribute Game game,
			@RequestParam("imageFile") MultipartFile imageFile) {
				try {
						// Convert image to Base64 string if file is uploaded
						if (!imageFile.isEmpty()) {
						byte[] imageBytes = imageFile.getBytes();
						String base64Image = Base64.getEncoder().encodeToString(imageBytes);
						game.setImage(base64Image);
						}

						gameRepo.save(game);
						return "Games"; // Redirect to games list page
						
						} catch (IOException e) {
						e.printStackTrace();
						return "Games"; // Return to form if error
						}
				
					}
						
					}