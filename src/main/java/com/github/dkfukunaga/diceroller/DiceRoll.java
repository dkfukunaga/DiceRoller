package com.github.dkfukunaga.diceroller;

import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class DiceRoll implements DiceBase {
    private final int dieSize;
    private final int dieNum;
    private final RollType rollType;
    private final Supplier<Integer> dieRoll;

    private static final Random RAND = new Random();

    public DiceRoll(int dieSize, int dieNum, RollType rollType) {
        if (dieNum < 1) {
            throw new InvalidDiceException("Must roll at least 1 die");
        }
        if (dieSize < 2) {
            throw new InvalidDiceException("Dice must have at least 2 faces");
        }

        this.dieSize = dieSize;
        this.dieNum = dieNum;
        this.rollType = rollType;
        this.dieRoll = () -> RAND.nextInt(dieSize) + 1;
    }

    public DiceRoll(int dieSize, int dieNum) {
        this(dieSize, dieNum, RollType.REGULAR);
    }

    public int getDieSize() {
        return dieSize;
    }

    public int getDieNum() {
        return dieNum;
    }

    public RollType getRollType() {
        return rollType;
    }

    @Override
    public String getName() {
        return (dieNum > 0 ? dieNum + "d" : "d") + dieSize;
    }

    @Override
    public RollResult getResult() {
        return DiceUtils.diceRollResult(rollType, dieRoll);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiceRoll diceRoll)) return false;
        return getDieSize() == diceRoll.getDieSize() && getDieNum() == diceRoll.getDieNum() &&
                getRollType() == diceRoll.getRollType() && Objects.equals(dieRoll, diceRoll.dieRoll);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDieSize(), getDieNum(), getRollType(), dieRoll);
    }

    @Override
    public String toString() {
        return "RollCheck{" +
                "dieSize=" + dieSize +
                ", dieNum=" + dieNum +
                ", rollType=" + rollType +
                ", dieRoll=" + dieRoll +
                '}';
    }
}
