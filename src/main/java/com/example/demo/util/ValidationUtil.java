package com.example.demo.util;

import java.util.Set;

public class ValidationUtil {
    private static final Set<String> VALID_SEASONS = Set.of("Kharif", "Rabi", "Summer");

    public static boolean validSeason(String season) {
        return VALID_SEASONS.contains(season);
    }
}
