package com.github.dkfukunaga.diceroller.modifiers.abilityscores;

import com.github.dkfukunaga.diceroller.DiceBase;
import com.github.dkfukunaga.diceroller.DiceModifier;
import com.github.dkfukunaga.diceroller.RollResult;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Objects;

public class CharismaModifier extends DiceModifier {
    private final int modifier;

    public CharismaModifier(DiceBase diceRoll, int modifier) {
        super(diceRoll);
        this.modifier = modifier;
    }

    @Override
    public String getName() {
        return diceRoll.getName() + ", Charisma";
    }

    @Override
    public RollResult getResult() {
        return diceRoll.getResult().addAll(modifier, new TextFlow(new Text(" " + formatModifier(modifier))));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharismaModifier that)) return false;
        return modifier == that.modifier;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(modifier);
    }

    @Override
    public String toString() {
        return "CharismaModifier{" +
                "modifier=" + modifier +
                ", diceRoll=" + diceRoll +
                '}';
    }
}
