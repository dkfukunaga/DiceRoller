package com.github.dkfukunaga.diceroller;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Objects;

public class AbilityModifier extends CheckModifier {
    private final String name;
    private final int modifier;

    public AbilityModifier(Check baseCheck, String name, int modifier) {
        super(baseCheck);
        this.name = name;
        this.modifier = modifier;
    }

    @Override
    public String getName() {
        return baseCheck.getName() + ", " + name;
    }

    @Override
    public CheckResult getResult() {
        TextFlow newText = new TextFlow();
        newText.getChildren().addAll(baseCheck.getResult().text().getChildren());
        newText.getChildren().add(new Text(" " + formatModifier(modifier)));

        return new CheckResult(baseCheck.getResult().total() + modifier, newText);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbilityModifier that)) return false;
        return modifier == that.modifier && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), modifier);
    }

    @Override
    public String toString() {
        return "AbilityModifier{" +
                "name='" + name + '\'' +
                ", modifier=" + modifier +
                '}';
    }
}
