package com.example.cities.game;

public class MoveResult {

    private final MoveStatus status;
    private final String computerCity;
    private final String message;

    public MoveResult(
            MoveStatus status,
            String computerCity,
            String message
    ) {
        this.status = status;
        this.computerCity = computerCity;
        this.message = message;
    }

    public MoveStatus getStatus() {
        return status;
    }

    public String getComputerCity() {
        return computerCity;
    }

    public String getMessage() {
        return message;
    }
}
