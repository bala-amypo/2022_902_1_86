catalog
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

farm
package com.example.demo.service;

import com.example.demo.entity.Farm;
import java.util.List;

public interface FarmService {
    Farm createFarm(Farm farm, Long ownerId);
    List<Farm> getFarmsByOwner(Long ownerId);
    Farm getFarmById(Long farmId);
}
fertilizer
package com.example.demo.service;

import com.example.demo.entity.Fertilizer;

import java.util.List;

public interface FertilizerService {

    Fertilizer addFertilizer(Fertilizer fertilizer);

    List<Fertilizer> getAllFertilizers();

    Fertilizer getFertilizerById(Long id);
}
suggestion
package com.example.demo.service;

import com.example.demo.entity.Suggestion;
import java.util.List;

public interface SuggestionService {
    Suggestion generateSuggestion(Long farmId);
    Suggestion getSuggestion(Long suggestionId);
    List<Suggestion> getSuggestionsByFarm(Long farmId);
}
user
package com.example.demo.service;
import com.example.demo.entity.User;
import java.util.List;

public interface UserService {
    User register(User user);
    User findByEmail(String email);
    User findById(Long id);
    // Added to fix UserController errors
    User create(User user);
    User getById(Long id);
    List<User> getAll();
    User update(Long id, User user);
    void delete(Long id);
}