package homesafe.digital;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Key extends Button {
    private char value;
    private int rowIndex;
    private int colIndex;
    private Keypad keypad;

    public Key(char value, int rowIndex, int colIndex, Keypad keypad){
        this.value = value;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.keypad = keypad;
        setup(this);
        this.setOnAction(actionEvent -> keyPressed());
    }

    public void setup(Key key){
        key.setText(Character.toString(key.getValue()));
        key.setMaxHeight(100);
        key.setMaxWidth(100);
        key.setMinHeight(100);
        key.setMinWidth(100);
        key.setFont(Font.font("Verdana", FontWeight.BOLD,30));
        key.setStyle("-fx-background-radius: 80px; -fx-background-color: #000000; -fx-text-fill: #ffffff; -fx-border-radius: 80px; -fx-border-color: #ffffff; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 )");
        if(key.getValue() == '*' || key.getValue() == '#') {
            key.setStyle("-fx-background-radius: 80px; -fx-background-color: #000000; -fx-text-fill: #5FE000; -fx-border-radius: 80px; -fx-border-color: #5FE000");
        }
    }

    private void keyPressed(){
        this.keypad.pressKey(this);
    }

    public char getValue() {
        return value;
    }
}
