package com.example.cities.game;

import java.util.Locale;

public class CityNameNormalizer {

    public String normalize(String city) {
        return city
                .trim()
                .replaceAll("\\s+", " ")
                .replaceAll("[’ʼ`ʻʹ]", "'")
                .replaceAll("[‐-‒–—―]", "-")
                .replaceAll("\\s*-\\s*", "-")
                .toLowerCase(Locale.ROOT);
    }
}

