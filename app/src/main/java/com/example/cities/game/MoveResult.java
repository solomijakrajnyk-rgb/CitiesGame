package com.example.cities.game;

public record MoveResult(
        MoveStatus status,
        String computerCity,
        String message
) {
}