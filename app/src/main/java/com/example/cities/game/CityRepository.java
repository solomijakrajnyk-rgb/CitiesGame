package com.example.cities.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class CityRepository {

    private static final String CITIES_FILE = "/cities.txt";

    private final List<String> cities;

    public CityRepository() {
        cities = loadCities();
    }

    public List<String> getCities() {
        return new ArrayList<>(cities);
    }

    public String findCity(String city) {
        String normalizedCity = normalizeCityName(city);

        return cities.stream()
                .filter(availableCity ->
                        normalizeCityName(availableCity)
                                .equals(normalizedCity))
                .findFirst()
                .orElse(null);
    }

    public String normalizeCityName(String city) {
        return city
                .trim()
                .replaceAll("\\s+", " ")
                .replaceAll("[’ʼ`ʻʹ]", "'")
                .replaceAll("[‐-‒–—―]", "-")
                .replaceAll("\\s*-\\s*", "-")
                .toLowerCase(Locale.ROOT);
    }

    private List<String> loadCities() {
        List<String> loadedCities = new ArrayList<>();
        Set<String> normalizedCities = new HashSet<>();

        try (InputStream inputStream = getClass()
                .getResourceAsStream(CITIES_FILE)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Файл міст не знайдено: " + CITIES_FILE
                );
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            inputStream,
                            StandardCharsets.UTF_8))) {

                String line;

                while ((line = reader.readLine()) != null) {
                    String city = line.trim();
                    String normalizedCity = normalizeCityName(city);

                    if (!normalizedCity.isEmpty()
                            && normalizedCities.add(normalizedCity)) {
                        loadedCities.add(city);
                    }
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "Не вдалося завантажити міста.",
                    exception
            );
        }

        return loadedCities;
    }
}
