package com.github.dkfukunaga.diceroller;

import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

public class RollResult {
    private int total;
    private final TextFlow text;

    public RollResult(int total, TextFlow text) {
        this.total = total;
        this.text = text;
    }

    public RollResult(RollResult other) {
        this.total = other.getTotal();
        this.text = new TextFlow(other.getText());
    }

    public RollResult() {
        this.total = 0;
        this.text = new TextFlow();
    }

    public int getTotal() {
        return total;
    }

    public TextFlow getText() {
        return text;
    }

    public RollResult addToTotal(int addition) {
        total += addition;
        return this;
    }

    public RollResult addToText(@NotNull TextFlow addedText) {
        text.getChildren().addAll(addedText.getChildren());
        return this;
    }

    public RollResult addAll(int addition, @NotNull TextFlow addedText) {
        addToTotal(addition);
        addToText(addedText);
        return this;
    }

    public RollResult addAll(RollResult oldResult) {
        return addAll(oldResult.getTotal(), oldResult.getText());
    }
}
