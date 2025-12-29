package com.example.demo.controller;

import com.example.demo.entity.Crop;
import com.example.demo.service.CatalogService;
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
        Crop crop = new Crop(
                null,
                name,
                suitablePHMin,
                suitablePHMax,
                requiredWater,
                season
        );

        return ResponseEntity.ok(catalogService.saveCrop(crop));
    }

    @GetMapping("/crops")
    public ResponseEntity<List<Crop>> getAllCrops() {
        return ResponseEntity.ok(catalogService.getAllCrops());
    }
}
