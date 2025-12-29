package com.example.demo.service;

import com.example.demo.entity.Crop;
import java.util.List;

public interface CatalogService {

    Crop saveCrop(Crop crop);

    List<Crop> getAllCrops();
}
