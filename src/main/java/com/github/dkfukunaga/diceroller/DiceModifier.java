package com.github.dkfukunaga.diceroller;

import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class DiceModifier extends CheckModifier {
    private final String name;
    private final RollType rollType;
    private final Supplier<Integer> dieRoll;

    private static final Random RAND = new Random();

    public DiceModifier(Check baseCheck, String name, int dieSize) {
        super(baseCheck);
        this.name = name;
        this.rollType = RollType.REGULAR;
        this.dieRoll = () -> RAND.nextInt(dieSize) + 1;
    }

    public DiceModifier(Check baseCheck, String name, int dieSize, RollType rollType) {
        super(baseCheck);
        this.name = name;
        this.rollType = rollType;
        this.dieRoll = () -> RAND.nextInt(dieSize) + 1;
    }

    @Override
    public String getName() {
        return baseCheck.getName() + ", " + name;
    }

    @Override
    public CheckResult getResult() {
        TextFlow newText = new TextFlow();
        newText.getChildren().addAll(baseCheck.getResult().text().getChildren());

        return switch (rollType) {
            case REGULAR -> handleRegular(newText);
            case ADVANTAGE -> handleAdvantage(newText);
            case DISADVANTAGE -> handleDisadvantage(newText);
            case REROLL_1 -> handleReroll(newText, 1);
            case REROLL_1_2 -> handleReroll(newText, 2);
            default -> throw new IllegalArgumentException("Unsupported roll type: " + rollType);
        };
    }

    private CheckResult handleRegular(TextFlow newText) {
        int roll = dieRoll.get();
        newText.getChildren().add(new Text(formatModifier(roll)));
        return new CheckResult(baseCheck.getResult().total() + roll, newText);
    }

    private CheckResult handleAdvantage(TextFlow newText) {
        int roll = dieRoll.get();
        int roll2 = dieRoll.get();
        addFormattedRolls(newText, roll, roll2, roll > roll2);
        return new CheckResult(baseCheck.getResult().total() + Math.max(roll, roll2), newText);
    }

    private CheckResult handleDisadvantage(TextFlow newText) {
        int roll = dieRoll.get();
        int roll2 = dieRoll.get();
        addFormattedRolls(newText, roll, roll2, roll < roll2);
        return new CheckResult(baseCheck.getResult().total() + Math.min(roll, roll2), newText);
    }

    private CheckResult handleReroll(TextFlow newText, int threshold) {
        int roll = dieRoll.get();
        if (roll <= threshold) {
            int roll2 = dieRoll.get();
            addFormattedRolls(newText, roll, roll2, false);
            roll = roll2;
        } else {
            newText.getChildren().add(new Text(formatModifier(roll)));
        }
        return new CheckResult(baseCheck.getResult().total() + roll, newText);
    }

    private void addFormattedRolls(TextFlow newText, int roll1, int roll2, boolean firstBold) {
        newText.getChildren().add(new Text("+("));
        if (firstBold) {
            Text boldText = new Text(Integer.toString(roll1));
            boldText.setStyle("-fx-font-weight: bold");
            newText.getChildren().add(boldText);
            newText.getChildren().add(new Text(" " + roll2 + ")"));
        } else {
            newText.getChildren().add(new Text(roll1 + " "));
            Text boldText = new Text(Integer.toString(roll2));
            boldText.setStyle("-fx-font-weight: bold");
            newText.getChildren().add(boldText);
            newText.getChildren().add(new Text(")"));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiceModifier that)) return false;
        return Objects.equals(getName(), that.getName()) && Objects.equals(dieRoll, that.dieRoll) && rollType == that.rollType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), dieRoll, rollType);
    }

    @Override
    public String toString() {
        return "DiceModifier{" +
                "name='" + name + '\'' +
                ", dieRoll=" + dieRoll +
                ", rollType=" + rollType +
                '}';
    }
}


// ChatGPT suggested unit test:
//@Test
//public void testHandleReroll() {
//    DiceModifier modifier = new DiceModifier(baseCheck, "Test Reroll", 6);
//    when(modifier.dieRoll.get()).thenReturn(1, 4); // Simulate a roll of 1 and then 4
//    CheckResult result = modifier.getResult();
//    assertEquals(baseCheck.getResult().total() + 4, result.total());
//}