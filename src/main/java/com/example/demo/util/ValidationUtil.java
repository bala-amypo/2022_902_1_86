package com.example.demo.util;

public class ValidationUtil {

    public static boolean validSeason(String season) {
        return season.equalsIgnoreCase("Kharif")
            || season.equalsIgnoreCase("Rabi")
            || season.equalsIgnoreCase("Summer");
    }
}

