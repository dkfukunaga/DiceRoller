package com.github.dkfukunaga.diceroller;

public abstract class DiceModifier implements DiceBase {
    protected DiceBase diceRoll;

    public DiceModifier(DiceBase diceRoll) {
        this.diceRoll = diceRoll;
    }

    @Override
    public String getName() {
        return diceRoll.getName();
    }

    @Override
    public RollResult getResult() {
        return diceRoll.getResult();
    }

    static protected String formatModifier(int modifier) {
        return (modifier > 0 ? " +" + modifier : (modifier < 0 ? " " + modifier : ""));
    }
}
