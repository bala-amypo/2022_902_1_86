package com.example.demo.dto;

public class CropRequest {
    private String name;
    private Double suitablePHMin;
    private Double suitablePHMax;
    private Double requiredWater;
    private String season;

    public CropRequest() {}

    // Add this constructor to satisfy Test line 450
    public CropRequest(String name, Double suitablePHMin, Double suitablePHMax, Double requiredWater, String season) {
        this.name = name;
        this.suitablePHMin = suitablePHMin;
        this.suitablePHMax = suitablePHMax;
        this.requiredWater = requiredWater;
        this.season = season;
    }

    public String getName() { return name; }
    public Double getSuitablePHMin() { return suitablePHMin; }
    public Double getSuitablePHMax() { return suitablePHMax; }
    public Double getRequiredWater() { return requiredWater; }
    public String getSeason() { return season; }
}