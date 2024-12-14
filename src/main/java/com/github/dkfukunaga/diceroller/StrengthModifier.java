package com.github.dkfukunaga.diceroller;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Objects;

public class StrengthModifier extends DiceModifier {
    private final int modifier;

    public StrengthModifier(DiceBase diceRoll, int modifier) {
        super(diceRoll);
        this.modifier = modifier;
    }

    @Override
    public String getName() {
        return diceRoll.getName() + ", Strength";
    }

    @Override
    public RollResult getResult() {
        return diceRoll.getResult().addAll(modifier, new TextFlow(new Text(" " + formatModifier(modifier))));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StrengthModifier that)) return false;
        return modifier == that.modifier;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(modifier);
    }

    @Override
    public String toString() {
        return "StrengthModifier{" +
                "modifier=" + modifier +
                ", diceRoll=" + diceRoll +
                '}';
    }
}
