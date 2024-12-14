package com.github.dkfukunaga.diceroller;

public class InvalidDiceException extends IllegalArgumentException {
    public InvalidDiceException(String message) {
        super(message);
    }
}
