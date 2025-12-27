package com.example.demo.service;

import com.example.demo.entity.Suggestion;

public interface SuggestionService {
    Suggestion generateSuggestion(Long farmId);
    Suggestion getSuggestion(Long id);
}
