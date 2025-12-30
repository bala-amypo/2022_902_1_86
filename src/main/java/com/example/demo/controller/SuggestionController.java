package com.example.demo.controller;

import com.example.demo.entity.Suggestion;
import com.example.demo.service.SuggestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/suggestions")
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @PostMapping("/{farmId}")
    public ResponseEntity<Suggestion> generate(@PathVariable Long farmId) {
        return ResponseEntity.ok(suggestionService.generateSuggestion(farmId));
    }

    @GetMapping("/{suggestionId}")
    public ResponseEntity<Suggestion> getSuggestion(@PathVariable Long suggestionId) {
        return ResponseEntity.ok(suggestionService.getSuggestion(suggestionId));
    }

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<List<Suggestion>> getByFarm(@PathVariable Long farmId) {
        return ResponseEntity.ok(suggestionService.getSuggestionsByFarm(farmId));
    }
}