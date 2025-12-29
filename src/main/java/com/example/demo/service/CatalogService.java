package com.example.demo.service;

import com.example.demo.entity.Crop;
import java.util.List;

public interface CatalogService {

    Crop saveCrop(Crop crop);

    List<Crop> getAllCrops();

    // 🔹 REQUIRED for SuggestionServiceImpl
    List<Crop> findSuitableCrops(Double soilPH, Double waterAvailable, String season);

    List<String> findFertilizersForCrops(List<String> cropNames);
}
