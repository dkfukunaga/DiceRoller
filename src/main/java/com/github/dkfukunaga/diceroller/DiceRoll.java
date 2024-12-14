package com.github.dkfukunaga.diceroller;

import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class DiceRoll implements DiceBase {
    private final int diceSize;
    private final int diceNum;
    private final RollType rollType;

    public DiceRoll(int diceSize, int diceNum, RollType rollType) {
        if (diceNum < 1) {
            throw new InvalidDiceException("Must roll at least 1 die");
        }
        if (diceSize < 2) {
            throw new InvalidDiceException("Dice must have at least 2 faces");
        }

        this.diceSize = diceSize;
        this.diceNum = diceNum;
        this.rollType = rollType;
    }

    public DiceRoll(int diceSize, int diceNum) {
        this(diceSize, diceNum, RollType.REGULAR);
    }

    public int getDiceSize() {
        return diceSize;
    }

    public int getDiceNum() {
        return diceNum;
    }

    public RollType getRollType() {
        return rollType;
    }

    @Override
    public String getName() {
        return (diceNum > 0 ? diceNum + "d" : "d") + diceSize;
    }

    @Override
    public RollResult getResult() {
        return DiceUtils.diceRollResult(rollType, diceSize);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiceRoll diceRoll)) return false;
        return getDiceSize() == diceRoll.getDiceSize() && getDiceNum() == diceRoll.getDiceNum() && getRollType() == diceRoll.getRollType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDiceSize(), getDiceNum(), getRollType());
    }

    @Override
    public String toString() {
        return "DiceRoll{" +
                "diceSize=" + diceSize +
                ", diceNum=" + diceNum +
                ", rollType=" + rollType +
                '}';
    }
}
