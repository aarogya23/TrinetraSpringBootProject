package com.trinetra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trinetra.model.Game;

public interface GameRepository extends JpaRepository<Game, Integer>{

	 List<Game> findByCategory(String category);

	
	
}
