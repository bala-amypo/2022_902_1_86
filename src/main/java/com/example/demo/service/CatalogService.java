package com.example.demo.service;

import com.example.demo.entity.Crop;
import com.example.demo.entity.Fertilizer;
import java.util.List;

public interface CatalogService {
    Crop addCrop(Crop crop);
    Fertilizer addFertilizer(Fertilizer fertilizer);
    List<Crop> findSuitableCrops(Double ph, Double waterLevel, String season);
    List<Fertilizer> findFertilizersForCrops(List<String> cropNames);
}