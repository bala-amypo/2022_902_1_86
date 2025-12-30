package com.example.demo.controller;

import com.example.demo.dto.FarmRequest;
import com.example.demo.entity.Farm;
import com.example.demo.service.FarmService;
import com.example.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/farms")
public class FarmController {
    private final FarmService farmService;
    private final UserService userService;

    public FarmController(FarmService farmService, UserService userService) {
        this.farmService = farmService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Farm> createFarm(@RequestBody FarmRequest req, Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        Farm farm = Farm.builder().name(req.getName()).soilPH(req.getSoilPH())
                      .waterLevel(req.getWaterLevel()).season(req.getSeason()).build();
        return ResponseEntity.ok(farmService.createFarm(farm, userId));
    }

    @GetMapping
    public ResponseEntity<List<Farm>> listFarms(Authentication auth) {
        Long userId = (Long) auth.getPrincipal();
        return ResponseEntity.ok(farmService.getFarmsByOwner(userId));
    }
}