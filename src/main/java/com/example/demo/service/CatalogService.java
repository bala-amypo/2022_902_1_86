package com.example.demo.service;

import com.example.demo.entity.Crop;
import com.example.demo.entity.Fertilizer;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.CropRepository;
import com.example.demo.repository.FertilizerRepository;
import com.example.demo.util.ValidationUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CropRepository cropRepo;
    private final FertilizerRepository fertRepo;

    // Constructor Injection
    public CatalogServiceImpl(CropRepository cropRepo, FertilizerRepository fertRepo) {
        this.cropRepo = cropRepo;
        this.fertRepo = fertRepo;
    }

    @Override
    public Crop addCrop(Crop crop) {
        // Test 40: PH Range validation
        if (crop.getSuitablePHMin() == null || crop.getSuitablePHMax() == null || 
            crop.getSuitablePHMin() > crop.getSuitablePHMax()) {
            throw new BadRequestException("PH min must be <= PH max");
        }

        // Test 47: Season validation
        if (!ValidationUtil.validSeason(crop.getSeason())) {
            throw new BadRequestException("Invalid season");
        }

        return cropRepo.save(crop);
    }

    @Override
    public Fertilizer addFertilizer(Fertilizer fertilizer) {
        // Test 41: NPK Ratio validation (Regex for number-number-number)
        if (fertilizer.getNpkRatio() == null || !fertilizer.getNpkRatio().matches("\\d+-\\d+-\\d+")) {
            throw new BadRequestException("Invalid NPK format");
        }

        return fertRepo.save(fertilizer);
    }

    @Override
    public List<Crop> findSuitableCrops(Double ph, Double waterLevel, String season) {
        // Based on AllFunctionalTests requirements, repo call uses ph and season
        return cropRepo.findSuitableCrops(ph, season);
    }

    @Override
    public List<Fertilizer> findFertilizersForCrops(List<String> cropNames) {
        // Test 28: Aggregate unique fertilizers for multiple crops
        Set<Fertilizer> uniqueFertilizers = new HashSet<>();
        
        if (cropNames != null) {
            for (String name : cropNames) {
                List<Fertilizer> found = fertRepo.findByCropName(name);
                if (found != null) {
                    uniqueFertilizers.addAll(found);
                }
            }
        }
        
        return new ArrayList<>(uniqueFertilizers);
    }
}