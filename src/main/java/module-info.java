module taxcalculator.taxcalculator {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens taxcalculator.taxcalculator to javafx.fxml;
    exports taxcalculator.taxcalculator;
}