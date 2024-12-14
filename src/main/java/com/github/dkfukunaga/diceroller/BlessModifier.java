package com.github.dkfukunaga.diceroller;

public class BlessModifier extends RolledModifier {

    public BlessModifier(DiceBase diceRoll) {
        super(diceRoll, 4);
    }

    public BlessModifier(DiceBase baseDiceBase, RollType rollType) {
        super(baseDiceBase, 4, rollType);
    }

    @Override
    public String getName() {
        return diceRoll.getName() + ", Bless";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlessModifier that)) return false;
        return getDiceSize() == that.getDiceSize() && getRollType() == that.getRollType();
    }

    @Override
    public String toString() {
        return "BlessModifier{" +
                "rollType=" + getRollType() +
                ", diceSize=" + getDiceSize() +
                ", diceRoll=" + diceRoll +
                '}';
    }
}


/*
 ChatGPT suggested unit test:
@Test
public void testHandleReroll() {
    DiceModifier modifier = new DiceModifier(baseCheck, "Test Reroll", 6);
    when(modifier.dieRoll.get()).thenReturn(1, 4); // Simulate a roll of 1 and then 4
    CheckResult result = modifier.getResult();
    assertEquals(baseCheck.getResult().total() + 4, result.total());
}
*/
