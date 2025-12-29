package com.example.demo.controller;

import com.example.demo.dto.CropRequest;
import com.example.demo.dto.FertilizerRequest;
import com.example.demo.entity.Crop;
import com.example.demo.entity.Fertilizer;
import com.example.demo.service.CatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @PostMapping("/crop")
    public ResponseEntity<Crop> addCrop(@RequestBody CropRequest req, Authentication auth) {
        if (auth == null || auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).build();
        }
        Crop crop = Crop.builder()
                .name(req.getName())
                .suitablePHMin(req.getSuitablePHMin())
                .suitablePHMax(req.getSuitablePHMax())
                .requiredWater(req.getRequiredWater())
                .season(req.getSeason())
                .build();
        return ResponseEntity.ok(catalogService.addCrop(crop));
    }

    @PostMapping("/fertilizer")
    public ResponseEntity<Fertilizer> addFertilizer(@RequestBody FertilizerRequest req, Authentication auth) {
        if (auth == null || auth.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ResponseEntity.status(403).build();
        }
        Fertilizer fert = Fertilizer.builder()
                .name(req.getName())
                .npkRatio(req.getNpkRatio())
                .recommendedForCrops(req.getRecommendedForCrops())
                .build();
        return ResponseEntity.ok(catalogService.addFertilizer(fert));
    }

    // Must be named findCrops for the test case
    @GetMapping("/crops/suitable")
    public ResponseEntity<List<Crop>> findCrops(@RequestParam Double ph, @RequestParam Double water, @RequestParam String season) {
        return ResponseEntity.ok(catalogService.findSuitableCrops(ph, water, season));
    }

    // Must be named findFerts for the test case
    @GetMapping("/fertilizers/by-crop")
    public ResponseEntity<List<Fertilizer>> findFerts(@RequestParam String name) {
        return ResponseEntity.ok(catalogService.findFertilizersForCrops(List.of(name)));
    }
}