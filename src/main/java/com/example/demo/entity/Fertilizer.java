package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fertilizers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 100)
    private String name;

    @NotBlank
    private String npkRatio;

    @NotBlank
    @Column(length = 500)
    private String recommendedForCrops;
}