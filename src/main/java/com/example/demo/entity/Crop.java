package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "crops")
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double suitablePHMin;
    private Double suitablePHMax;
    private Double requiredWater;
    private String season;

    public Crop() {}
    public Crop(Long id, String name, Double suitablePHMin, Double suitablePHMax, Double requiredWater, String season) {
        this.id = id; this.name = name; this.suitablePHMin = suitablePHMin; 
        this.suitablePHMax = suitablePHMax; this.requiredWater = requiredWater; this.season = season;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Double getSuitablePHMin() { return suitablePHMin; }
    public Double getSuitablePHMax() { return suitablePHMax; }
    public String getSeason() { return season; }

    public static CropBuilder builder() { return new CropBuilder(); }
    public static class CropBuilder {
        private Long id; private String name; private Double suitablePHMin; private Double suitablePHMax; private Double requiredWater; private String season;
        public CropBuilder id(Long id) { this.id = id; return this; }
        public CropBuilder name(String name) { this.name = name; return this; }
        public CropBuilder suitablePHMin(Double min) { this.suitablePHMin = min; return this; }
        public CropBuilder suitablePHMax(Double max) { this.suitablePHMax = max; return this; }
        public CropBuilder requiredWater(Double water) { this.requiredWater = water; return this; }
        public CropBuilder season(String season) { this.season = season; return this; }
        public Crop build() { return new Crop(id, name, suitablePHMin, suitablePHMax, requiredWater, season); }
    }
}