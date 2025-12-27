package com.example.demo.service;

import com.example.demo.entity.Crop;
import com.example.demo.entity.Farm;
import com.example.demo.entity.Fertilizer;
import com.example.demo.entity.Suggestion;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SuggestionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionServiceImpl implements SuggestionService {
    
    private final FarmService farmService;
    private final CatalogService catalogService;
    private final SuggestionRepository suggestionRepository;
    
    public SuggestionServiceImpl(FarmService farmService, CatalogService catalogService, 
                                SuggestionRepository suggestionRepository) {
        this.farmService = farmService;
        this.catalogService = catalogService;
        this.suggestionRepository = suggestionRepository;
    }
    
    @Override
    public Suggestion generateSuggestion(Long farmId) {
        Farm farm = farmService.getFarmById(farmId);
        
        List<Crop> suitableCrops = catalogService.findSuitableCrops(
            farm.getSoilPH(), farm.getWaterLevel(), farm.getSeason());
        
        List<String> cropNames = suitableCrops.stream()
                .map(Crop::getName)
                .collect(Collectors.toList());
        
        List<Fertilizer> suitableFertilizers = catalogService.findFertilizersForCrops(cropNames);
        
        String suggestedCrops = cropNames.isEmpty() ? "No suitable crops" : String.join(",", cropNames);
        String suggestedFertilizers = suitableFertilizers.isEmpty() ? "No suitable fertilizers" : 
                suitableFertilizers.stream().map(Fertilizer::getName).collect(Collectors.joining(","));
        
        Suggestion suggestion = Suggestion.builder()
                .farm(farm)
                .suggestedCrops(suggestedCrops)
                .suggestedFertilizers(suggestedFertilizers)
                .build();
        
        return suggestionRepository.save(suggestion);
    }
    
    @Override
    public Suggestion getSuggestion(Long id) {
        return suggestionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Suggestion not found"));
    }
}
