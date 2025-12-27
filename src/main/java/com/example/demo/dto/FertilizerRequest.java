package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FertilizerRequest {
    private String name;
    private String npkRatio;
    private String recommendedForCrops;
}