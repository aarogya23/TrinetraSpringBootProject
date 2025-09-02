package com.trinetra.service;

import com.trinetra.model.Cart;
import com.trinetra.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    // Add game to cart
    public Cart addToCart(Long userId, int gameId, int quantity) {
        // Check if the game is already in the user's cart
        Cart existingCart = cartRepository.findByUserIdAndGameId(userId, gameId);

        if (existingCart != null) {
            // If already exists, just update the quantity
            existingCart.setQuantity(existingCart.getQuantity() + quantity);
            return cartRepository.save(existingCart);
        } else {
            // Else create a new cart entry
            Cart newCart = new Cart(userId, gameId, quantity);
            return cartRepository.save(newCart);
        }
    }

    // Get all cart items for a user
    public List<Cart> getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    // Remove a game from cart
    public void removeFromCart(Long userId, int gameId) {
        Cart existingCart = cartRepository.findByUserIdAndGameId(userId, gameId);
        if (existingCart != null) {
            cartRepository.delete(existingCart);
        }
    }

    // Clear cart
    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}
