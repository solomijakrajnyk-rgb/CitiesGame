package com.example.cities;

import com.example.cities.game.CityGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CityGameTest {

    @Test
    void shouldAcceptFirstCity() {
        CityGame game = new CityGame();

        assertNull(game.validatePlayerMove("Луцьк"));
    }

    @Test
    void shouldRejectUnknownCity() {
        CityGame game = new CityGame();

        assertEquals(
                "Такого міста немає в грі або воно вже використовувалося.",
                game.validatePlayerMove("Варшава")
        );
    }

    @Test
    void shouldRejectCityWithWrongFirstLetter() {
        CityGame game = new CityGame();

        game.makeComputerMove("Луцьк");

        assertEquals(
                "Місто має починатися з літери \"в\".",
                game.validatePlayerMove("Одеса")
        );
    }

    @Test
    void shouldRejectRepeatedCity() {
        CityGame game = new CityGame();

        game.makeComputerMove("Луцьк");

        assertEquals(
                "Такого міста немає в грі або воно вже використовувалося.",
                game.validatePlayerMove("Луцьк")
        );
    }

    @Test
    void shouldUpdateScoreAfterMove() {
        CityGame game = new CityGame();

        game.makeComputerMove("Луцьк");

        assertEquals(1, game.getPlayerScore());
        assertEquals(1, game.getComputerScore());
    }
}

