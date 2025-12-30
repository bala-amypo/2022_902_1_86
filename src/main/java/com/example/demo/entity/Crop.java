package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer requiredWater;

    // ✅ Getter & Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // ✅ Getter & Setter for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ✅ Getter & Setter for requiredWater
    public Integer getRequiredWater() {
        return requiredWater;
    }

    public void setRequiredWater(Integer requiredWater) {
        this.requiredWater = requiredWater;
    }
}
