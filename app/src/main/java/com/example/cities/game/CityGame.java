package com.example.cities.game;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CityGame {

    private final CityRepository cityRepository;
    private final CityNameNormalizer cityNameNormalizer;
    private final Set<String> usedCities;

    private String lastCity;
    private int playerScore;
    private int computerScore;
    private GameStatus gameStatus;

    public CityGame(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
        cityNameNormalizer = new CityNameNormalizer();
        usedCities = new HashSet<>();
        gameStatus = GameStatus.IN_PROGRESS;
    }

    public MoveResult processPlayerMove(String city) {
        if (gameStatus != GameStatus.IN_PROGRESS) {
            return new MoveResult(
                    MoveStatus.GAME_OVER,
                    null,
                    "Гра вже завершена."
            );
        }

        if (city == null || city.isBlank()) {
            return new MoveResult(
                    MoveStatus.INVALID_CITY,
                    null,
                    "Введіть назву міста."
            );
        }

        String normalizedCity = cityNameNormalizer.normalize(city);

        if ("здаюсь".equals(normalizedCity)) {
            gameStatus = GameStatus.COMPUTER_WON;

            return new MoveResult(
                    MoveStatus.COMPUTER_WON,
                    null,
                    "Ви здалися.\nПереміг комп'ютер!"
            );
        }

        String cityFromRepository = cityRepository.findCity(normalizedCity);

        if (cityFromRepository == null) {
            return new MoveResult(
                    MoveStatus.INVALID_CITY,
                    null,
                    "Такого міста немає в грі."
            );
        }

        if (usedCities.contains(cityFromRepository)) {
            return new MoveResult(
                    MoveStatus.ALREADY_USED,
                    null,
                    "Це місто вже використовувалося."
            );
        }

        if (lastCity != null && !startsWithRequiredLetter(cityFromRepository)) {
            return new MoveResult(
                    MoveStatus.WRONG_LETTER,
                    null,
                    "Місто має починатися з літери \""
                            + getRequiredLetter()
                            + "\"."
            );
        }

        processPlayerCity(cityFromRepository);

        String computerCity = makeComputerMove();

        if (computerCity == null) {
            gameStatus = GameStatus.PLAYER_WON;

            return new MoveResult(
                    MoveStatus.PLAYER_WON,
                    null,
                    "У комп'ютера закінчилися міста.\n"
                            + "Ви перемогли!"
            );
        }

        return new MoveResult(
                MoveStatus.VALID,
                computerCity,
                null
        );
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getComputerScore() {
        return computerScore;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public String getRequiredLetter() {
        if (lastCity == null) {
            return "";
        }

        return getLastLetter(lastCity);
    }

    private void processPlayerCity(String city) {
        usedCities.add(city);
        lastCity = city;
        playerScore++;
    }

    private String makeComputerMove() {
        String requiredLetter = getRequiredLetter();
        List<String> cities = cityRepository.getCities();

        for (String city : cities) {
            if (!usedCities.contains(city)
                    && startsWith(city, requiredLetter)) {
                usedCities.add(city);
                lastCity = city;
                computerScore++;
                return city;
            }
        }

        return null;
    }

    private boolean startsWithRequiredLetter(String city) {
        return startsWith(city, getRequiredLetter());
    }

    private boolean startsWith(String city, String letter) {
        return cityNameNormalizer.normalize(city)
                .startsWith(cityNameNormalizer.normalize(letter));
    }

    private String getLastLetter(String city) {
        String normalizedCity = cityNameNormalizer.normalize(city);
        int index = normalizedCity.length() - 1;

        while (index > 0
                && isSpecialEndingLetter(normalizedCity.charAt(index))) {
            index--;
        }

        return String.valueOf(normalizedCity.charAt(index));
    }

    private boolean isSpecialEndingLetter(char letter) {
        return letter == 'ь' || letter == 'ъ';
    }
}
