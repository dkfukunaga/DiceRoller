package com.github.dkfukunaga.diceroller;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DiceUtils {
    private static final Random RAND = new Random();

    static public RollResult diceRollResult(RollType rollType, int diceSize) {

        int roll = roll(diceSize);
        return switch (rollType) {
            case REGULAR -> {
                yield new RollResult(roll, new TextFlow(new Text(Integer.toString(roll))));
            }
            case ADVANTAGE, DISADVANTAGE -> {
                int roll2 = roll(diceSize);
                RollResult result = null;
                if (rollType == RollType.ADVANTAGE) {
                    result = new RollResult(Math.max(roll, roll2), formatRoll(roll, roll2, roll > roll2));
                } else { // rollType == RollType.DISADVANTAGE
                    result = new RollResult(Math.min(roll, roll2), formatRoll(roll, roll2, roll < roll2));
                }
                yield result;
            }
            case REROLL_1, REROLL_1_2 -> {
                if (roll <= (rollType == RollType.REROLL_1 ? 1 : 2)) {
                    int roll2 = roll(diceSize);
                    yield new RollResult(roll2, formatRoll(roll, roll2, false));
                }
                yield new RollResult(roll, new TextFlow(new Text(Integer.toString(roll))));
            }
            default -> throw new InvalidDiceException("Unsupported roll type: " + rollType);
        };
    }

    static public RollResult diceRollResult(RollType rollType, int diceSize, @NotNull RollResult result) {
        return result.addAll(diceRollResult(rollType, diceSize));
    }

    static private int roll(int diceSize) {
        return RAND.nextInt(diceSize) + 1;
    }

    static private TextFlow formatRoll(int roll1, int roll2, boolean firstBold) {
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
}
