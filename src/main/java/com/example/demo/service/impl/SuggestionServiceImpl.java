package com.example.demo.service.impl;

import com.example.demo.entity.Crop;
import com.example.demo.service.CatalogService;
import com.example.demo.service.SuggestionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    private final CatalogService catalogService;

    public SuggestionServiceImpl(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @Override
    public List<String> suggestCropsAndFertilizers(Double soilPH, Double waterAvailable, String season) {

        List<Crop> crops = catalogService.findSuitableCrops(soilPH, waterAvailable, season);

        List<String> cropNames = crops.stream()
                .map(Crop::getName)
                .collect(Collectors.toList());

        return catalogService.findFertilizersForCrops(cropNames);
    }
}
