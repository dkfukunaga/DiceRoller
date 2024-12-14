package com.github.dkfukunaga.diceroller;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class DiceUtils {
    private static final Random RAND = new Random();

    static public RollResult diceRollResult(RollType rollType, int diceSize, ModifierType modType) {

        int roll = roll(diceSize);
        final String plusModifier = switch (modType) {
            case BASE -> "";
            case POS -> "+";
            case NEG -> "-";
        }  + Integer.toString(roll);
        return switch (rollType) {
            case REGULAR -> {
                yield new RollResult(roll, new TextFlow(new Text(plusModifier)));
            }
            case ADVANTAGE, DISADVANTAGE -> {
                int roll2 = roll(diceSize);
                RollResult result = null;
                if (rollType == RollType.ADVANTAGE) {
                    result = new RollResult(Math.max(roll, roll2), formatRoll(roll, roll2, roll > roll2, modType));
                } else { // rollType == RollType.DISADVANTAGE
                    result = new RollResult(Math.min(roll, roll2), formatRoll(roll, roll2, roll < roll2, modType));
                }
                yield result;
            }
            case REROLL_1, REROLL_1_2 -> {
                if (roll <= (rollType == RollType.REROLL_1 ? 1 : 2)) {
                    int roll2 = roll(diceSize);
                    yield new RollResult(roll2, formatRoll(roll, roll2, false, modType));
                }
                yield new RollResult(roll, new TextFlow(new Text(plusModifier)));
            }
            default -> throw new InvalidDiceException("Unsupported roll type: " + rollType);
        };
    }

    static public RollResult diceRollResult(RollType rollType, int diceSize, @NotNull RollResult result, ModifierType modType) {
        return result.addAll(diceRollResult(rollType, diceSize, modType));
    }

    static public RollResult diceRollResult(RollType rollType, int diceSize, @NotNull RollResult result) {
        return result.addAll(diceRollResult(rollType, diceSize, ModifierType.BASE));
    }

    static private int roll(int diceSize) {
        return RAND.nextInt(diceSize) + 1;
    }

    static private TextFlow formatRoll(int roll1, int roll2, boolean firstBold, ModifierType modType) {
        TextFlow text = new TextFlow(new Text(switch (modType) {
            case BASE -> "(";
            case POS -> "+(";
            case NEG -> "-(";
        }));

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
