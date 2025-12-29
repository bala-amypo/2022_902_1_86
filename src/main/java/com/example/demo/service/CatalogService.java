package com.example.demo.service;
import com.example.demo.entity.*;
import java.util.List;

public interface CatalogService {
    Crop addCrop(Crop crop);
    Fertilizer addFertilizer(Fertilizer fertilizer);
    List<Crop> findSuitableCrops(Double ph, Double water, String season);
    List<Fertilizer> findFertilizersForCrops(List<String> cropNames);
}
crop
package com.example.demo.service;

import com.example.demo.entity.Crop;

import java.util.List;

public interface CropService {

    Crop saveCrop(Crop crop);

    List<Crop> getAllCrops();

    Crop getCropById(Long id);

    void deleteCrop(Long id);
}



