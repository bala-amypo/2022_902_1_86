package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CropRequest {
    private String name;
    private Double suitablePHMin;
    private Double suitablePHMax;
    private Double requiredWater;
    private String season;
}