package com.example.demo.controller;

import com.example.demo.entity.Suggestion;
import com.example.demo.service.SuggestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @PostMapping("/generate/{farmId}")
    public ResponseEntity<Suggestion> generate(@PathVariable Long farmId) {
        Suggestion suggestion = suggestionService.generateSuggestion(farmId);
        return ResponseEntity.ok(suggestion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Suggestion> getSuggestion(@PathVariable Long id) {
        Suggestion suggestion = suggestionService.getSuggestion(id);
        return ResponseEntity.ok(suggestion);
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<Suggestion>> getSuggestionsByFarm(@PathVariable Long farmId) {
        List<Suggestion> suggestions = suggestionService.getSuggestionsByFarm(farmId);
        return ResponseEntity.ok(suggestions);
    }
}