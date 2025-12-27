package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @NotBlank
    @Column(length = 100)
    private String name;

    @NotNull
    private Double suitablePHMin;

    @NotNull
    private Double suitablePHMax;

    @NotNull
    private Double requiredWater;

    @NotBlank
    private String season;
}