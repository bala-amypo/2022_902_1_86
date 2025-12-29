package com.example.demo.service.impl;

import com.example.demo.entity.Crop;
import com.example.demo.repository.CropRepository;
import com.example.demo.service.CatalogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CropRepository cropRepository;

    public CatalogServiceImpl(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    @Override
    public Crop saveCrop(Crop crop) {
        return cropRepository.save(crop);
    }

    @Override
    public List<Crop> getAllCrops() {
        return cropRepository.findAll();
    }

    @Override
    public List<Crop> findSuitableCrops(Double soilPH, Double waterAvailable, String season) {
        return cropRepository.findAll()
                .stream()
                .filter(crop ->
                        soilPH >= crop.getSuitablePHMin()
                                && soilPH <= crop.getSuitablePHMax()
                                && crop.getSeason().equalsIgnoreCase(season)
                                && waterAvailable >= crop.getRequiredWater()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findFertilizersForCrops(List<String> cropNames) {
        // SIMPLE placeholder logic (can be improved later)
        return cropNames.stream()
                .map(crop -> crop + " Fertilizer")
                .collect(Collectors.toList());
    }
}
