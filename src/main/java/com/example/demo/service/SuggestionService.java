package com.example.demo.service;

import java.util.List;

public interface SuggestionService {

    List<String> suggestCropsAndFertilizers(Double soilPH, Double waterAvailable, String season);
}
