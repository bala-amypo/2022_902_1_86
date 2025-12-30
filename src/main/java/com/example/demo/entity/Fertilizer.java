package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fertilizers")
public class Fertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String npkRatio;
    private String recommendedForCrops;

    public Fertilizer() {}

    public Fertilizer(Long id, String name, String npkRatio, String recommendedForCrops) {
        this.id = id;
        this.name = name;
        this.npkRatio = npkRatio;
        this.recommendedForCrops = recommendedForCrops;
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getNpkRatio() { return npkRatio; }
    public String getRecommendedForCrops() { return recommendedForCrops; }

    // Manual Builder for Test Cases
    public static FertilizerBuilder builder() {
        return new FertilizerBuilder();
    }

    public static class FertilizerBuilder {
        private Long id;
        private String name;
        private String npkRatio;
        private String recommendedForCrops;

        public FertilizerBuilder id(Long id) { this.id = id; return this; }
        public FertilizerBuilder name(String name) { this.name = name; return this; }
        public FertilizerBuilder npkRatio(String npkRatio) { this.npkRatio = npkRatio; return this; }
        public FertilizerBuilder recommendedForCrops(String recommendedForCrops) { this.recommendedForCrops = recommendedForCrops; return this; }
        
        public Fertilizer build() {
            return new Fertilizer(id, name, npkRatio, recommendedForCrops);
        }
    }
}