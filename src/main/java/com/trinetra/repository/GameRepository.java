package com.trinetra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trinetra.model.Game;

public interface GameRepository extends JpaRepository<Game, Integer>{

	 List<Game> findByCategory(String category);

	 
	 
	 // now making a list of game by name to make smooth functionality
	 
	 
		// Search by name containing a string (case-insensitive)
		 List<Game> findByGamenameContainingIgnoreCase(String gamename);
		
		
	
}
