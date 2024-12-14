package com.github.dkfukunaga.diceroller;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class RollCheck implements Check {
    private final int dieSize;
    private final int dieNum;
    private final RollType rollType;
    private final Supplier<Integer> dieRoll;

    private static final Random RAND = new Random();

    public RollCheck(int dieSize, int dieNum, RollType rollType) {
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

    public RollCheck(int dieSize, int dieNum) {
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
    public CheckResult getResult() {

        int roll = dieRoll.get();
        return switch (rollType) {
            case REGULAR -> {
                yield new CheckResult(roll, formatRoll(roll));
            }
            case ADVANTAGE, DISADVANTAGE -> {
                int roll2 = dieRoll.get();
                CheckResult result = null;
                if (rollType == RollType.ADVANTAGE) {
                    result = new CheckResult(Math.max(roll, roll2), formatRoll(roll, roll2, roll > roll2));
                } else { // rollType == RollType.DISADVANTAGE
                    result = new CheckResult(Math.min(roll, roll2), formatRoll(roll, roll2, roll < roll2));
                }
                yield result;
            }
            case REROLL_1, REROLL_1_2 -> {
                if (roll <= (rollType == RollType.REROLL_1 ? 1 : 2)) {
                    int roll2 = dieRoll.get();
                    yield new CheckResult(roll2, formatRoll(roll, roll2, false));
                }
                yield new CheckResult(roll, formatRoll(roll));
            }
            default -> throw new InvalidDiceException("Unsupported roll type: " + rollType);
        };
    }

    private TextFlow formatRoll(int roll) {
        return new TextFlow(new Text(Integer.toString(roll)));
    }

    private TextFlow formatRoll(int roll1, int roll2, boolean firstBold) {
        TextFlow text = new TextFlow(new Text("("));

        if (firstBold) {
            Text boldText = new Text(Integer.toString(roll1));
            boldText.setStyle("-fx-font-weight: bold");
            text.getChildren().add(boldText);
            text.getChildren().add(new Text(" " + roll2 + ")"));
        } else {
            text.getChildren().add(new Text(roll1 + " "));
            Text boldText = new Text(Integer.toString(roll2));
            boldText.setStyle("-fx-font-weight: bold");
            text.getChildren().add(boldText);
            text.getChildren().add(new Text(")"));
        }

        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RollCheck rollCheck)) return false;
        return getDieSize() == rollCheck.getDieSize() && getDieNum() == rollCheck.getDieNum() &&
                getRollType() == rollCheck.getRollType() && Objects.equals(dieRoll, rollCheck.dieRoll);
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
