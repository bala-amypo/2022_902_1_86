package com.example.demo.service.impl;

import com.example.demo.entity.Crop;
import com.example.demo.repository.CropRepository;
import com.example.demo.service.CatalogService;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
