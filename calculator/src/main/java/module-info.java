module calculator.calculator {
    requires javafx.controls;
    requires javafx.fxml;

    opens calculator.calculator to javafx.fxml;
    exports calculator.calculator;
}
