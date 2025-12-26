package com.example.demo.dto;

import lombok.Data;

@Data
public class FarmRequest {
    private Double soilPH;
    private Double waterLevel;
    private String season;
}