package com.example.demo.controller;

import com.example.demo.dto.CropRequest;
import com.example.demo.dto.FertilizerRequest;
import com.example.demo.entity.Crop;
import com.example.demo.entity.Fertilizer;
import com.example.demo.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    public ResponseEntity<Crop> addCrop(@RequestBody CropRequest request, Authentication authentication) {
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).build();
        }
        
        Crop crop = Crop.builder()
                .name(request.getName())
                .suitablePHMin(request.getSuitablePHMin())
                .suitablePHMax(request.getSuitablePHMax())
                .requiredWater(request.getRequiredWater())
                .season(request.getSeason())
                .build();
        
        Crop savedCrop = catalogService.addCrop(crop);
        return ResponseEntity.ok(savedCrop);
    }

    @PostMapping("/fertilizers")
    public ResponseEntity<Fertilizer> addFertilizer(@RequestBody FertilizerRequest request, Authentication authentication) {
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).build();
        }
        
        Fertilizer fertilizer = Fertilizer.builder()
                .name(request.getName())
                .npkRatio(request.getNpkRatio())
                .recommendedForCrops(request.getRecommendedForCrops())
                .build();
        
        Fertilizer savedFertilizer = catalogService.addFertilizer(fertilizer);
        return ResponseEntity.ok(savedFertilizer);
    }

    @GetMapping("/crops/suitable")
    public ResponseEntity<List<Crop>> findCrops(
            @RequestParam Double ph,
            @RequestParam Double waterLevel,
            @RequestParam String season) {
        List<Crop> crops = catalogService.findSuitableCrops(ph, waterLevel, season);
        return ResponseEntity.ok(crops);
    }

    @GetMapping("/fertilizers/by-crop")
    public ResponseEntity<List<Fertilizer>> findFerts(@RequestParam String cropName) {
        List<Fertilizer> fertilizers = catalogService.findFertilizersForCrops(List.of(cropName));
        return ResponseEntity.ok(fertilizers);
    }
}