package com.example.cities.game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CityGame {

    private final CityRepository cityRepository;
    private final Set<String> usedCities;
    private final List<String> availableCities;

    private String lastCity;
    private int playerScore;
    private int computerScore;

    public CityGame() {
        cityRepository = new CityRepository();
        availableCities = cityRepository.getCities();
        usedCities = new HashSet<>();
    }

    public String validatePlayerMove(String city) {
        if (city == null || city.isBlank()) {
            return "Введіть назву міста.";
        }

        String normalizedCity = city.trim();

        if (!isCityAvailable(normalizedCity)) {
            return "Такого міста немає в грі або воно вже використовувалося.";
        }

        if (!isCorrectFirstCity(normalizedCity)) {
            return "Місто має починатися з літери \"" + getRequiredLetter() + "\".";
        }

        return null;
    }

    public boolean isCityAvailable(String city) {
        return availableCities.stream()
                .anyMatch(availableCity ->
                        availableCity.equalsIgnoreCase(city)
                                && !usedCities.contains(availableCity));
    }

    public boolean isCorrectFirstCity(String city) {
        return lastCity == null || startsWithRequiredLetter(city);
    }

    public String makeComputerMove(String playerCity) {
        String normalizedPlayerCity = findCity(playerCity);

        markCityAsUsed(normalizedPlayerCity);
        lastCity = normalizedPlayerCity;
        playerScore++;

        String requiredLetter = getLastLetter(normalizedPlayerCity);

        for (String city : availableCities) {
            if (!usedCities.contains(city) && startsWith(city, requiredLetter)) {
                markCityAsUsed(city);
                lastCity = city;
                computerScore++;
                return city;
            }
        }

        return null;
    }

    public boolean hasAvailableResponse() {
        if (lastCity == null) {
            return true;
        }

        String requiredLetter = getLastLetter(lastCity);

        for (String city : availableCities) {
            if (!usedCities.contains(city) && startsWith(city, requiredLetter)) {
                return true;
            }
        }

        return false;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getComputerScore() {
        return computerScore;
    }

    public String getRequiredLetter() {
        if (lastCity == null) {
            return "";
        }

        return getLastLetter(lastCity);
    }

    private boolean startsWithRequiredLetter(String city) {
        return startsWith(city, getRequiredLetter());
    }

    private boolean startsWith(String city, String letter) {
        return city.toLowerCase().startsWith(letter.toLowerCase());
    }

    private String getLastLetter(String city) {
        String normalizedCity = city.trim().toLowerCase();

        int index = normalizedCity.length() - 1;

        while (index > 0 && isSpecialEndingLetter(normalizedCity.charAt(index))) {
            index--;
        }

        return String.valueOf(normalizedCity.charAt(index));
    }

    private boolean isSpecialEndingLetter(char letter) {
        return letter == 'ь' || letter == 'ъ';
    }

    private void markCityAsUsed(String city) {
        usedCities.add(city);
    }

    private String findCity(String city) {
        return availableCities.stream()
                .filter(availableCity -> availableCity.equalsIgnoreCase(city))
                .findFirst()
                .orElseThrow();
    }
}
