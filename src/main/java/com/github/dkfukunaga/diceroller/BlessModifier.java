package com.github.dkfukunaga.diceroller;

import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class BlessModifier extends DiceModifier {
    private final RollType rollType;
    private final Supplier<Integer> dieRoll;

    private static final Random RAND = new Random();

    public BlessModifier(DiceBase diceRoll) {
        super(diceRoll);
        this.rollType = RollType.REGULAR;
        this.dieRoll = () -> RAND.nextInt(4) + 1;
    }

    public BlessModifier(DiceBase baseDiceBase, RollType rollType) {
        super(baseDiceBase);
        this.rollType = rollType;
        this.dieRoll = () -> RAND.nextInt(4) + 1;
    }

    @Override
    public String getName() {
        return diceRoll.getName() + ", Bless";
    }

    @Override
    public RollResult getResult() {
        return DiceUtils.diceRollResult(rollType, dieRoll, diceRoll.getResult());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlessModifier that)) return false;
        return rollType == that.rollType && Objects.equals(dieRoll, that.dieRoll);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rollType, dieRoll);
    }

    @Override
    public String toString() {
        return "BlessModifier{" +
                "rollType=" + rollType +
                ", dieRoll=" + dieRoll +
                ", diceRoll=" + diceRoll +
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