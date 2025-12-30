package com.example.demo.service;

import java.util.List;
import com.example.demo.entity.Fertilizer;

public interface CatalogService {

    List<Fertilizer> findFertilizersForCrops(List<String> cropNames);

}
