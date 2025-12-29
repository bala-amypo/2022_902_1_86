package com.example.demo.controller;

import com.example.demo.entity.Crop;
import com.example.demo.service.CatalogService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping("/crops")
    public ResponseEntity<Crop> createCrop(
            @RequestParam String name,
            @RequestParam Double suitablePHMin,
            @RequestParam Double suitablePHMax,
            @RequestParam Double requiredWater,
            @RequestParam String season
    ) {

        // ✅ NO Lombok builder — using constructor
        Crop crop = new Crop(
                null,
                name,
                suitablePHMin,
                suitablePHMax,
                requiredWater,
                season
        );

        Crop savedCrop = catalogService.saveCrop(crop);
        return ResponseEntity.ok(savedCrop);
    }

    @GetMapping("/crops")
    public ResponseEntity<List<Crop>> getAllCrops() {
        return ResponseEntity.ok(catalogService.getAllCrops());
    }
}
