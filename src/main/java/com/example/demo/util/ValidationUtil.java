package com.example.demo.util;
public class ValidationUtil {
    public static boolean validSeason(String season) {
        if (season == null) return false;
        return season.equalsIgnoreCase("Kharif") || 
               season.equalsIgnoreCase("Rabi") || 
               season.equalsIgnoreCase("Summer");
    }
}