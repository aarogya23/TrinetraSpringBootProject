package com.trinetra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.trinetra.model.ProfileClass;
import com.trinetra.repository.ProfileRepository;

@Controller
public class ProfilePageController {
	
	@Autowired  // Add this annotation for dependency injection
	private ProfileRepository pRepo;
	
	@GetMapping("/profilepage")
	public String profilePage() {
		return "profilebro"; // JSP or HTML view name
	}
	 
	@PostMapping("/profile")
	public String profilePostMethod(@ModelAttribute ProfileClass pc) {
		try {
			pRepo.save(pc);
			return "Frontpage"; // Use redirect to prevent form resubmission
		} catch (Exception e) {
			// Handle any database errors
			e.printStackTrace();
			return "redirect:/profilepage?error=true";
		}
	}
}