module com.github.dkfukunaga.diceroller {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.dkfukunaga.diceroller to javafx.fxml;
    exports com.github.dkfukunaga.diceroller;
}