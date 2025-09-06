package com.trinetra.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.trinetra.model.Game;
import com.trinetra.model.UserClass;
import com.trinetra.repository.GameRepository;
import com.trinetra.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Controller
public class AddGameController {

    @Autowired
    private GameRepository gameRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JavaMailSender mailSender;

    // Show add game page
    @GetMapping("/addgame")
    public String addGamePage(Model model) {
        List<Game> games = gameRepo.findAll();
        model.addAttribute("games", games);
        return "Games";
    }

    // Handle game form submission with image
    @PostMapping("/addGame")
    public String addGamePost(@ModelAttribute Game game,
                              @RequestParam("imageFile") MultipartFile imageFile) {
        try {
            if (!imageFile.isEmpty()) {
                byte[] imageBytes = imageFile.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                game.setImage(base64Image);
            }
            gameRepo.save(game);
            // Send notification to all users
//            sendGameNotification(game);
            return "redirect:/addgame";
        } catch (IOException e) {
            e.printStackTrace();
            return "Games";
        }
    }

    // Fetch all games for the table
    @GetMapping("/api/games")
    @ResponseBody
    public List<Game> getAllGames() {
        return gameRepo.findAll();
    }

    // Update game image URLs, game link, and YouTube link
    @PostMapping("/api/update-images/{id}")
    @ResponseBody
    public String updateGameImages(@PathVariable int id, @RequestBody Map<String, String> imageData) {
        Game game = gameRepo.findById(id).orElse(null);
        if (game == null) {
            return "Error: Game not found";
        }
        try {
            String gameImage1 = imageData.get("gameImage1");
            String gameImage2 = imageData.get("gameImage2");
            String gameImage3 = imageData.get("gameImage3");
            String gameImage4 = imageData.get("gameImage4");
            String link = imageData.get("link");
            String youtubeLink = imageData.get("youtubeLink");

            // Validate image URLs
            for (String url : List.of(gameImage1, gameImage2, gameImage3, gameImage4)) {
                if (url != null && !url.isEmpty() && !url.matches("^/.*\\.(jpg|png)$")) {
                    return "Error: Invalid image URL format. Image URLs must start with '/' and end with '.jpg' or '.png'";
                }
            }

            // Validate game link
            if (link != null && !link.isEmpty() && !link.matches("^/.*$")) {
                return "Error: Invalid game link format. Game link must start with '/'";
            }

            // Validate YouTube link
            if (youtubeLink != null && !youtubeLink.isEmpty() && 
                !youtubeLink.matches("^(https://www\\.youtube\\.com/watch\\?v=|https://youtu\\.be/).*$")) {
                return "Error: Invalid YouTube link format. Must start with 'https://www.youtube.com/watch?v=' or 'https://youtu.be/'";
            }

            game.setGameImage1(gameImage1);
            game.setGameImage2(gameImage2);
            game.setGameImage3(gameImage3);
            game.setGameImage4(gameImage4);
            game.setLink(link);
            game.setYoutubeLink(youtubeLink);
            gameRepo.save(game);
            return "URLs updated successfully";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error updating URLs";
        }
    }

    // Send notification for a specific game
    @PostMapping("/api/send-notification/{id}")
    @ResponseBody
    public String sendNotification(@PathVariable int id) {
        Game game = gameRepo.findById(id).orElse(null);
        if (game == null) {
            return "Error: Game not found";
        }
        try {
            sendGameNotification(game);
            return "Notifications sent successfully";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error sending notifications";
        }
    }

    // Helper method to send email notifications
    private void sendGameNotification(Game game) throws MessagingException {
        List<UserClass> users = userRepo.findAll();
        for (UserClass user : users) {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("New Game Available: " + game.getGamename());
            String htmlContent = "<div style='font-family: Poppins, sans-serif; color: #1a202c; background: #fff7ed; padding: 20px;'>"
                    + "<h2 style='color: #f97316;'>New Game Alert!</h2>"
                    + "<p>A new game is now available at Trinetra Game Store!</p>"
                    + "<h3>" + game.getGamename() + "</h3>"
                    + "<p><strong>Price:</strong> Rs " + String.format("%.2f", game.getGameprice()) + "</p>"
                    + "<p><strong>Category:</strong> " + (game.getCategory() != null ? game.getCategory() : "-") + "</p>"
                    + "<p><strong>Genre:</strong> " + (game.getGamegenre() != null ? game.getGamegenre() : "-") + "</p>"
                    + "<p><strong>Platform:</strong> " + (game.getPlatform() != null ? game.getPlatform() : "-") + "</p>"
                    + (game.getYoutubeLink() != null && !game.getYoutubeLink().isEmpty() ? 
                       "<p><strong>Watch Trailer:</strong> <a href='" + game.getYoutubeLink() + "' style='color: #f97316; text-decoration: none;'>View on YouTube</a></p>" : "")
                    + (game.getImage() != null ? "<img src='data:image/jpeg;base64," + game.getImage() + "' style='width: 200px; height: auto; border-radius: 8px; margin-top: 10px;' alt='" + game.getGamename() + "'/>" : "")
                    + "<p style='margin-top: 20px;'><a href='http://localhost:8083' style='background: #f97316; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;'>Check it out!</a></p>"
                    + "</div>";
            helper.setText(htmlContent, true);
            mailSender.send(message);
        }
    }
}