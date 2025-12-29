package com.example.demo.service;

import com.example.demo.entity.Farm;
import com.example.demo.entity.User;
import com.example.demo.repository.FarmRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FarmServiceImpl implements FarmService {
    private final FarmRepository farmRepo;
    private final UserRepository userRepo;

    // Constructor signature required by Test line 357
    public FarmServiceImpl(FarmRepository farmRepo, UserRepository userRepo) {
        this.farmRepo = farmRepo;
        this.userRepo = userRepo;
    }

    @Override
    public Farm createFarm(Farm farm, Long ownerId) {
        if (farm.getSoilPH() < 3 || farm.getSoilPH() > 10) throw new IllegalArgumentException("Invalid pH level");
        return farmRepo.save(farm);
    }
    @Override public List<Farm> getFarmsByOwner(Long id) { return farmRepo.findByOwnerId(id); }
    @Override public Farm getFarmById(Long id) { return farmRepo.findById(id).orElseThrow(); }
}