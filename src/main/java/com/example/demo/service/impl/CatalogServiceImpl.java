package com.example.demo.service.impl;

import com.example.demo.entity.Crop;
import com.example.demo.entity.Fertilizer;
import com.example.demo.repository.CropRepository;
import com.example.demo.repository.FertilizerRepository;
import com.example.demo.service.CatalogService;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CropRepository cropRepository;
    private final FertilizerRepository fertilizerRepository;

    public CatalogServiceImpl(CropRepository cropRepository,
                              FertilizerRepository fertilizerRepository) {
        this.cropRepository = cropRepository;
        this.fertilizerRepository = fertilizerRepository;
    }

    @Override
    public List<Fertilizer> findFertilizersForCrops(List<String> cropNames) {

        List<Fertilizer> result = new ArrayList<>();

        for (String cropName : cropNames) {
            Crop crop = cropRepository.findByName(cropName);

            if (crop != null) {
                List<Fertilizer> fertilizers =
                        fertilizerRepository.findByCrop(crop);

                result.addAll(fertilizers);
            }
        }

        return result;
    }
}
