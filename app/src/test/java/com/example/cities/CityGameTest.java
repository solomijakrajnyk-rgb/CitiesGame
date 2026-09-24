package com.example.cities;

import com.example.cities.game.CityGame;
import com.example.cities.game.CityRepository;
import com.example.cities.game.GameStatus;
import com.example.cities.game.MoveResult;
import com.example.cities.game.MoveStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CityGameTest {

    @Test
    void shouldAcceptFirstCity() {
        CityGame game = new CityGame(new CityRepository());

        MoveResult result = game.processPlayerMove("Луцьк");

        assertEquals(MoveStatus.VALID, result.getStatus());
        assertEquals("Київ", result.getComputerCity());
        assertEquals(1, game.getPlayerScore());
        assertEquals(1, game.getComputerScore());
    }

    @Test
    void shouldRejectUnknownCity() {
        CityGame game = new CityGame(new CityRepository());

        MoveResult result = game.processPlayerMove("Варшава");

        assertEquals(MoveStatus.INVALID_CITY, result.getStatus());
        assertEquals(
                "Такого міста немає в грі.",
                result.getMessage()
        );
        assertEquals(0, game.getPlayerScore());
        assertEquals(0, game.getComputerScore());
    }

    @Test
    void shouldRejectCityWithWrongFirstLetter() {
        CityGame game = new CityGame(new CityRepository());

        game.processPlayerMove("Луцьк");

        MoveResult result = game.processPlayerMove("Одеса");

        assertEquals(MoveStatus.WRONG_LETTER, result.getStatus());
        assertEquals(
                "Місто має починатися з літери \"в\".",
                result.getMessage()
        );
        assertEquals(1, game.getPlayerScore());
        assertEquals(1, game.getComputerScore());
    }

    @Test
    void shouldRejectRepeatedCity() {
        CityGame game = new CityGame(new CityRepository());

        game.processPlayerMove("Луцьк");

        MoveResult result = game.processPlayerMove("Луцьк");

        assertEquals(MoveStatus.ALREADY_USED, result.getStatus());
        assertEquals(
                "Це місто вже використовувалося.",
                result.getMessage()
        );
        assertEquals(1, game.getPlayerScore());
        assertEquals(1, game.getComputerScore());
    }

    @Test
    void shouldUpdateScoreAfterValidMove() {
        CityGame game = new CityGame(new CityRepository());

        MoveResult result = game.processPlayerMove("Луцьк");

        assertEquals(MoveStatus.VALID, result.getStatus());
        assertEquals(1, game.getPlayerScore());
        assertEquals(1, game.getComputerScore());
    }

    @Test
    void shouldHandleSurrenderInGameLogic() {
        CityGame game = new CityGame(new CityRepository());

        MoveResult result = game.processPlayerMove("здаюсь");

        assertEquals(MoveStatus.COMPUTER_WON, result.getStatus());
        assertEquals(
                "Ви здалися.\nПереміг комп'ютер!",
                result.getMessage()
        );
        assertEquals(GameStatus.COMPUTER_WON, game.getGameStatus());
    }

    @Test
    void shouldNotAllowMoveAfterGameIsOver() {
        CityGame game = new CityGame(new CityRepository());

        game.processPlayerMove("здаюсь");

        MoveResult result = game.processPlayerMove("Луцьк");

        assertEquals(MoveStatus.GAME_OVER, result.getStatus());
        assertEquals(
                "Гра вже завершена.",
                result.getMessage()
        );
    }

    @Test
    void shouldAcceptCityWithDifferentLetterCase() {
        CityGame game = new CityGame(new CityRepository());

        MoveResult result = game.processPlayerMove("ЛУЦЬК");

        assertEquals(MoveStatus.VALID, result.getStatus());
        assertEquals("Київ", result.getComputerCity());
    }

    @Test
    void shouldAcceptCityWithExtraSpaces() {
        CityGame game = new CityGame(new CityRepository());

        MoveResult result = game.processPlayerMove("  Луцьк  ");

        assertEquals(MoveStatus.VALID, result.getStatus());
        assertEquals("Київ", result.getComputerCity());
    }
}
