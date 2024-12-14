package com.github.dkfukunaga.diceroller;

import java.util.Objects;

public abstract class RolledModifier extends DiceModifier {
    private final RollType rollType;
    private final int diceSize;

    public RolledModifier(DiceBase diceRoll, int diceSize) {
        super(diceRoll);
        this.diceSize = diceSize;
        this.rollType = RollType.REGULAR;
    }

    public RolledModifier(DiceBase baseDiceBase, int diceSize, RollType rollType) {
        super(baseDiceBase);
        this.diceSize = diceSize;
        this.rollType = rollType;
    }

    public RollType getRollType() {
        return rollType;
    }

    public int getDiceSize() {
        return diceSize;
    }

    @Override
    public RollResult getResult() {
        return DiceUtils.diceRollResult(getRollType(), getDiceSize());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRollType(), getDiceSize());
    }

    @Override
    public abstract String getName();

    @Override
    public abstract boolean equals(Object o);

    @Override
    public abstract String toString();
}