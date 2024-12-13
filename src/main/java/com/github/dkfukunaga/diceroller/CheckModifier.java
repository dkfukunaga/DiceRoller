package com.github.dkfukunaga.diceroller;

public abstract class CheckModifier implements Check {
    protected Check     baseCheck;

    public CheckModifier(Check baseCheck) {
        this.baseCheck = baseCheck;
    }

    @Override
    public String getName() {
        return baseCheck.getName();
    }

    @Override
    public CheckResult getResult() {
        return baseCheck.getResult();
    }

    static protected String formatModifier(int modifier) {
        return (modifier > 0 ? " +" + modifier : (modifier < 0 ? " " + modifier : ""));
    }
}
