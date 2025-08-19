package com.trinetra.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.trinetra.model.Game;
import com.trinetra.repository.GameRepository;

@RestController
public class SearchController {
    
    @Autowired
    private GameRepository gameRepository;
    
    @GetMapping("/api/search")
    public ResponseEntity<List<Game>> searchGames(@RequestParam("q") String searchTerm) {
        List<Game> games = gameRepository.findByGamenameContainingIgnoreCase(searchTerm);
        return ResponseEntity.ok(games);
    }
}