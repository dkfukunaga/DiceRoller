module com.github.dkfukunaga.diceroller {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens com.github.dkfukunaga.diceroller to javafx.fxml;
    exports com.github.dkfukunaga.diceroller;
    exports com.github.dkfukunaga.diceroller.modifiers.abilityscores;
    opens com.github.dkfukunaga.diceroller.modifiers.abilityscores to javafx.fxml;
    exports com.github.dkfukunaga.diceroller.modifiers;
    opens com.github.dkfukunaga.diceroller.modifiers to javafx.fxml;
    exports com.github.dkfukunaga.diceroller.modifiers.spells;
    opens com.github.dkfukunaga.diceroller.modifiers.spells to javafx.fxml;
}