package com.example.demo.service;

import com.example.demo.entity.Fertilizer;

import java.util.List;

public interface FertilizerService {

    Fertilizer addFertilizer(Fertilizer fertilizer);

    List<Fertilizer> getAllFertilizers();

    Fertilizer getFertilizerById(Long id);
}