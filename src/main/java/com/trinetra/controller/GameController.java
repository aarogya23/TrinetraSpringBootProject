package com.trinetra.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.trinetra.model.Cart;
import com.trinetra.model.Game;
import com.trinetra.model.Purchase;
import com.trinetra.model.Review;
import com.trinetra.repository.CartRepository;
import com.trinetra.repository.GameRepository;
import com.trinetra.repository.PurchaseRepository;
import com.trinetra.repository.ReviewRepository;

@Controller
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    // Show game details and reviews
    @GetMapping("/game/{id}")
    public String showGameDetails(@PathVariable("id") int id, Model model) {
        Game game = gameRepository.findById(id).orElse(null);
        if (game == null) {
            return "redirect:/index.html";
        }
        List<Review> reviews = reviewRepository.findByGameId(id);
        Long userId = 1L; // TODO: Replace with actual user ID from authentication
        List<Cart> cartItems = cartRepository.findByUserId(userId);
        int cartItemCount = cartItems.stream().mapToInt(Cart::getQuantity).sum();
        model.addAttribute("game", game);
        model.addAttribute("reviews", reviews);
        model.addAttribute("newReview", new Review());
        model.addAttribute("cartItemCount", cartItemCount);
        return "eafc";
    }

    // Buy now
    @PostMapping("/buyNow")
    public String buyNow(@RequestParam("gameId") int gameId, Model model, RedirectAttributes redirectAttributes) {
        Long userId = 1L; // TODO: Replace with actual user ID from authentication
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            redirectAttributes.addFlashAttribute("error", "Game not found!");
            return "redirect:/index.html";
        }
        model.addAttribute("game", game);
        // Optionally add to cart for checkout
        Cart existingCartItem = cartRepository.findByUserIdAndGameId(userId, gameId);
        if (existingCartItem == null) {
            Cart cartItem = new Cart(userId, gameId, 1);
            cartRepository.save(cartItem);
        }
        return "checkout";
    }

    // Confirm purchase
    @PostMapping("/confirmPurchase")
    public String confirmPurchase(
            @RequestParam("gameId") int gameId,
            @RequestParam("paymentMethod") String paymentMethod,
            @RequestParam(value = "cardNumber", required = false) String cardNumber,
            @RequestParam(value = "cardholderName", required = false) String cardholderName,
            @RequestParam(value = "expiryDate", required = false) String expiryDate,
            @RequestParam(value = "cvv", required = false) String cvv,
            @RequestParam(value = "esewaId", required = false) String esewaId,
            @RequestParam(value = "khaltiMobile", required = false) String khaltiMobile,
            @RequestParam(value = "imepayMobile", required = false) String imepayMobile,
            @RequestParam(value = "bankName", required = false) String bankName,
            @RequestParam(value = "email") String email,
            Model model,
            RedirectAttributes redirectAttributes) {

        Long userId = 1L; // TODO: Replace with actual user ID from authentication
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            redirectAttributes.addFlashAttribute("error", "Game not found!");
            return "redirect:/index.html";
        }

        // Validate email
        if (email == null || email.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Email is required!");
            return "redirect:/cart";
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            redirectAttributes.addFlashAttribute("error", "Invalid email format!");
            return "redirect:/cart";
        }

        // Construct payment details (excluding email, as it's stored separately)
        String paymentDetails;
        switch (paymentMethod) {
            case "card":
                if (cardNumber == null || cardholderName == null || expiryDate == null || cvv == null) {
                    redirectAttributes.addFlashAttribute("error", "All card details are required!");
                    return "redirect:/cart";
                }
                paymentDetails = "Card ending in " + (cardNumber.length() >= 4 
                    ? cardNumber.substring(cardNumber.length() - 4) : "N/A");
                break;
            case "esewa":
                if (esewaId == null || esewaId.trim().isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "eSewa ID is required!");
                    return "redirect:/cart";
                }
                paymentDetails = "eSewa ID: " + esewaId;
                break;
            case "khalti":
                if (khaltiMobile == null || khaltiMobile.trim().isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Khalti mobile number is required!");
                    return "redirect:/cart";
                }
                paymentDetails = "Khalti Mobile: " + khaltiMobile;
                break;
            case "imepay":
                if (imepayMobile == null || imepayMobile.trim().isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "IME Pay mobile number is required!");
                    return "redirect:/cart";
                }
                paymentDetails = "IME Pay Mobile: " + imepayMobile;
                break;
            case "bank":
                if (bankName == null || bankName.trim().isEmpty()) {
                    redirectAttributes.addFlashAttribute("error", "Bank name is required!");
                    return "redirect:/cart";
                }
                paymentDetails = "Bank: " + bankName;
                break;
            default:
                redirectAttributes.addFlashAttribute("error", "Invalid payment method!");
                return "redirect:/cart";
        }

        String image = game.getImage() != null ? game.getImage() : "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAACklEQVR4nGMAAQAABQABDQottAAAAABJRU5ErkJggg==";

        // Create purchase with email as a separate field
        Purchase purchase = new Purchase(
                userId,
                gameId,
                game.getGamename(),
                game.getGameprice(),
                paymentMethod,
                paymentDetails,
                LocalDateTime.now(),
                image,
                "Ready to Play",
                UUID.randomUUID().toString().substring(0, 8).toUpperCase(),
                null,
                "0 hours",
                email // Store email in the Purchase model
        );
        purchaseRepository.save(purchase);

        // Remove item from cart
        Cart cartItem = cartRepository.findByUserIdAndGameId(userId, gameId);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
        }

        // Add attributes for the library page
        model.addAttribute("purchase", purchase);
        model.addAttribute("game", game);
        model.addAttribute("message", "Purchase successful! A confirmation has been sent to " + email + ".");

        return "library";
    }

    // Show all reviews (standalone review page)
    @GetMapping("/review")
    public String showAllReviews(Model model) {
        List<Review> reviews = reviewRepository.findAllByOrderByCreatedAtAsc();
        model.addAttribute("reviews", reviews);
        model.addAttribute("newReview", new Review());
        return "review";
    }

    // Add a general review
    @PostMapping("/addbro")
    public String addGeneralReview(@ModelAttribute Review review, RedirectAttributes redirectAttributes) {
        review.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        redirectAttributes.addFlashAttribute("message", "Review submitted successfully!");
        return "redirect:/review";
    }

    // Add a review for a specific game
    @PostMapping("/addReview")
    public String addReview(@ModelAttribute Review review, 
                           @RequestParam("gameId") int gameId, 
                           RedirectAttributes redirectAttributes) {
        review.setGameId(gameId);
        review.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        redirectAttributes.addFlashAttribute("message", "Review submitted successfully!");
        return "redirect:/game/" + gameId;
    }
}