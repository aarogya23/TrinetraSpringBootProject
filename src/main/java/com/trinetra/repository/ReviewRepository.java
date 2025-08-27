package com.trinetra.repository;

import com.trinetra.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByGameId(int gameId);
    List<Review> findAllByOrderByCreatedAtAsc();
}