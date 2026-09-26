package com.example.cities.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CityRepository {

    private static final String CITIES_FILE = "/cities.txt";

    private final List<String> cities;
    private final CityNameNormalizer cityNameNormalizer;

    public CityRepository(CityNameNormalizer cityNameNormalizer) {
        this.cityNameNormalizer = cityNameNormalizer;
        cities = Collections.unmodifiableList(loadCities());
    }

    public List<String> getCities() {
        return cities;
    }

    public String findCity(String city) {
        String normalizedCity = cityNameNormalizer.normalize(city);

        return cities.stream()
                .filter(availableCity ->
                        cityNameNormalizer.normalize(availableCity)
                                .equals(normalizedCity))
                .findFirst()
                .orElse(null);
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
                    String normalizedCity =
                            cityNameNormalizer.normalize(city);

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
