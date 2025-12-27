package com.example.demo.dto;

public class FarmRequest {

    private String name;
    private String location;
    private Double area;

    public FarmRequest() {
    }

    public FarmRequest(String name, String location, Double area) {
        this.name = name;
        this.location = location;
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Double getArea() {
        return area;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setArea(Double area) {
        this.area = area;
    }
}
