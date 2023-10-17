package homesafe.digital;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class BatteryIndicator extends HBox {
    private Rectangle batteryBody;
    private Rectangle batteryTerminal;
    private Label percentageLabel; // This label will display the percentage

    public BatteryIndicator() {
        batteryBody = new Rectangle(30, 10);
        batteryBody.setFill(Color.WHITE);
        batteryBody.setStroke(Color.BLACK);

        batteryTerminal = new Rectangle(5, 6);
        batteryTerminal.setFill(Color.BLACK);
        batteryTerminal.setStroke(Color.BLACK);

        percentageLabel = new Label("100%"); // Initialize the label with "100%"
        percentageLabel.setTextFill(Color.WHITE); // Set the text color
        percentageLabel.setAlignment(Pos.CENTER); // Center the text

        this.getChildren().addAll(batteryBody, batteryTerminal);
        this.setAlignment(Pos.CENTER);
        this.getChildren().add(percentageLabel);
        StackPane.setAlignment(percentageLabel, Pos.CENTER); // Align the label to the center of the batteryBody
    }
}
