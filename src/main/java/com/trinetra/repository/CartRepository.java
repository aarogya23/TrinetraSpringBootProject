package com.trinetra.repository;

import com.trinetra.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    Cart findByUserIdAndGameId(Long userId, int gameId);
    void deleteByUserId(Long userId);
}