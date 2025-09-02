package com.trinetra.controller;

import com.trinetra.model.Purchase;
import com.trinetra.model.UserClass;
import com.trinetra.repository.PurchaseRepository;
import com.trinetra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/api/purchases")
public class PurchaseController {

    @Autowired
    private PurchaseRepository prepo;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/dashboard")
    public String showDashboard() {
        return "index"; // Maps to index.html
    }

    @GetMapping("/library")
    public String showLibrary(@RequestParam(value = "view", defaultValue = "grid") String view, Model model) {
        Long userId = 1L; // TODO: Replace with actual user ID from authentication
        List<Purchase> purchases = prepo.findByUserId(userId);
        model.addAttribute("purchases", purchases);
        model.addAttribute("view", view);
        return "library";
    }

    @GetMapping("/admin")
    @ResponseBody
    public List<Purchase> getAdminPurchases() {
        return prepo.findAll();
    }

    @GetMapping("/user/{userId}")
    @ResponseBody
    public List<Purchase> getPurchaseByUserId(@PathVariable Long userId) {
        return prepo.findByUserId(userId);
    }

    @GetMapping("/users")
    @ResponseBody
    public List<UserClass> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/adminpurchase")
    public String adminPurchase(Model model) {
        model.addAttribute("purchases", prepo.findAll());
        return "admin-purchase"; // Maps to admin-purchase.html
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public void deletePurchase(@PathVariable Long id) {
        prepo.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public Purchase updatePurchase(@PathVariable Long id, @RequestBody Purchase updatedPurchase) {
        Purchase purchase = prepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
        purchase.setGameName(updatedPurchase.getGameName());
        purchase.setGamePrice(updatedPurchase.getGamePrice());
        purchase.setPaymentMethod(updatedPurchase.getPaymentMethod());
        purchase.setPaymentDetails(updatedPurchase.getPaymentDetails());
        purchase.setPurchaseDate(updatedPurchase.getPurchaseDate());
        purchase.setImage(updatedPurchase.getImage());
        purchase.setStatus(updatedPurchase.getStatus());
        purchase.setSecretCode(updatedPurchase.getSecretCode());
        purchase.setLastPlayed(updatedPurchase.getLastPlayed());
        purchase.setPlayTime(updatedPurchase.getPlayTime());
        return prepo.save(purchase);
    }

    @GetMapping("/play")
    public String playGame(@RequestParam("id") Long purchaseId) {
        Purchase purchase = prepo.findById(purchaseId)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
        purchase.setLastPlayed(LocalDateTime.now().toString());
        prepo.save(purchase);
        return "redirect:/api/purchases/library";
    }
}