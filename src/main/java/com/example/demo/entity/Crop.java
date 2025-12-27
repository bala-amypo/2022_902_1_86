package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "crops")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Double suitablePHMin;
    
    @Column(nullable = false)
    private Double suitablePHMax;
    
    @Column(nullable = false)
    private Double requiredWater;
    
    @Column(nullable = false)
    private String season;
}
