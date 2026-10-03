package com.example.cities;

import com.example.cities.game.CityGame;
import com.example.cities.game.CityNameNormalizer;
import com.example.cities.game.CityRepository;
import com.example.cities.game.GameStatus;
import com.example.cities.game.MoveResult;
import com.example.cities.game.MoveStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CityGameTest {

    private CityGame createGame() {
        CityNameNormalizer cityNameNormalizer =
                new CityNameNormalizer();
        CityRepository cityRepository =
                new CityRepository(cityNameNormalizer);

        return new CityGame(
                cityRepository,
                cityNameNormalizer
        );
    }

    @Test
    void shouldAcceptFirstCity() {
        CityGame game = createGame();

        MoveResult result = game.processPlayerMove("Луцьк");

        assertEquals(MoveStatus.VALID, result.status());
        assertEquals("Київ", result.computerCity());
        assertEquals(1, game.getPlayerScore());
        assertEquals(1, game.getComputerScore());
    }

    @Test
    void shouldRejectUnknownCity() {
        CityGame game = createGame();

        MoveResult result = game.processPlayerMove("Варшава");

        assertEquals(MoveStatus.INVALID_CITY, result.status());
        assertEquals(
                "Такого міста немає в грі.",
                result.message()
        );
        assertEquals(0, game.getPlayerScore());
        assertEquals(0, game.getComputerScore());
    }

    @Test
    void shouldRejectCityWithWrongFirstLetter() {
        CityGame game = createGame();

        game.processPlayerMove("Луцьк");

        MoveResult result = game.processPlayerMove("Одеса");

        assertEquals(MoveStatus.WRONG_LETTER, result.status());
        assertEquals(
                "Місто має починатися з літери \"в\".",
                result.message()
        );
        assertEquals(1, game.getPlayerScore());
        assertEquals(1, game.getComputerScore());
    }

    @Test
    void shouldRejectRepeatedCity() {
        CityGame game = createGame();

        game.processPlayerMove("Луцьк");

        MoveResult result = game.processPlayerMove("Луцьк");

        assertEquals(MoveStatus.ALREADY_USED, result.status());
        assertEquals(
                "Це місто вже використовувалося.",
                result.message()
        );
        assertEquals(1, game.getPlayerScore());
        assertEquals(1, game.getComputerScore());
    }

    @Test
    void shouldUpdateScoreAfterValidMove() {
        CityGame game = createGame();

        MoveResult result = game.processPlayerMove("Луцьк");

        assertEquals(MoveStatus.VALID, result.status());
        assertEquals(1, game.getPlayerScore());
        assertEquals(1, game.getComputerScore());
    }

    @Test
    void shouldHandleSurrenderInGameLogic() {
        CityGame game = createGame();

        MoveResult result = game.processPlayerMove("здаюсь");

        assertEquals(MoveStatus.COMPUTER_WON, result.status());
        assertEquals(
                "Ви здалися.\nПереміг комп'ютер!",
                result.message()
        );
        assertEquals(
                GameStatus.COMPUTER_WON,
                game.getGameStatus()
        );
    }

    @Test
    void shouldNotAllowMoveAfterGameIsOver() {
        CityGame game = createGame();

        game.processPlayerMove("здаюсь");

        MoveResult result = game.processPlayerMove("Луцьк");

        assertEquals(MoveStatus.GAME_OVER, result.status());
        assertEquals(
                "Гра вже завершена.",
                result.message()
        );
    }

    @Test
    void shouldAcceptCityWithDifferentLetterCase() {
        CityGame game = createGame();

        MoveResult result = game.processPlayerMove("ЛУЦЬК");

        assertEquals(MoveStatus.VALID, result.status());
        assertEquals("Київ", result.computerCity());
    }

    @Test
    void shouldAcceptCityWithExtraSpaces() {
        CityGame game = createGame();

        MoveResult result = game.processPlayerMove("  Луцьк  ");

        assertEquals(MoveStatus.VALID, result.status());
        assertEquals("Київ", result.computerCity());
    }
}
