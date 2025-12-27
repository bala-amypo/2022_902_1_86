package com.example.demo.util;

import java.util.Arrays;
import java.util.List;

public class ValidationUtil {
    private static final List<String> VALID_SEASONS = Arrays.asList("Kharif", "Rabi", "Zaid");
    
    public static boolean validSeason(String season) {
        return VALID_SEASONS.contains(season);
    }
}