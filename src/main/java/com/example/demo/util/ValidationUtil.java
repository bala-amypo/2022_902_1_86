package com.example.demo.util;

public class ValidationUtils {

    public static boolean validSeason(String season) {
        return season.equalsIgnoreCase("Kharif")
            || season.equalsIgnoreCase("Rabi")
            || season.equalsIgnoreCase("Summer");
    }
}

