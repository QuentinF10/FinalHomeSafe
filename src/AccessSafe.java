package homesafe.digital;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class AccessSafe extends Scene {
    private Keypad keypad;
    private Notification display;
    private Controller controller;
    private Timer timer;
    private Label pin;
    private Label pwd;
    private Label homeSafe;
    private Door door;
    private Code code;
    private boolean lockout;
    private BatteryIndicator batteryIndicator;




    public AccessSafe(Parent parent, double v, double v1, Controller controller) throws IOException {
        super(parent, v, v1);
        this.timer = new Timer(this);
        this.controller = controller;
        this.code = new Code("0000",this);
        this.door = new Door(this);
        this.lockout = false;
        this.homeSafe = new Label("HomeSafe Team 7");
        this.batteryIndicator = new BatteryIndicator();



        VBox mainLayout = (VBox) parent;
        mainLayout.setAlignment(Pos.CENTER);

        HBox layout = new HBox(150);

        layout.setAlignment(Pos.CENTER);
        mainLayout.setStyle("-fx-background-color: linear-gradient(to left,#833400,#FF9F33)");
        //layout.setStyle("-fx-background-color: linear-gradient(to right top,#ffb900 0%,#ffb900 50%,#ff7730)");
        VBox UI = new VBox();
        setupUI(UI);

        VBox menu = new VBox(40);
        setupMenu(menu);
        setupHeading(homeSafe);
        layout.getChildren().addAll(menu, UI);
        mainLayout.getChildren().addAll(homeSafe,layout);
    }

    private void setupMenu(VBox menu) {
        ArrayList<Label> infoLabels = new ArrayList<>();
        ArrayList<Label> changeLabels = new ArrayList<>();

        menu.setAlignment(Pos.CENTER);

        Button openDoor = new Button("Open Door");
        openDoor.setStyle("-fx-background-color: #5FE000;-fx-background-radius: 80px; -fx-font-size: 20px; -fx-text-fill: #000000; -fx-border-radius: 80px; -fx-border-color: #000000");
        openDoor.setOnAction(actionEvent -> openDoor());

        Button mechanicalKey = new Button("Use key");
        mechanicalKey.setStyle("-fx-background-color: #5FE000;-fx-background-radius: 80px; -fx-font-size: 20px; -fx-text-fill: #000000; -fx-border-radius: 80px; -fx-border-color: #000000");
        mechanicalKey.setOnAction(actionEvent -> insertMechanicalKey());

        Button closeDoor = new Button("Close Door");
        closeDoor.setStyle("-fx-background-color: #E00400;-fx-background-radius: 80px; -fx-font-size: 20px; -fx-text-fill: #000000; -fx-border-radius: 80px; -fx-border-color: #000000");
        closeDoor.setOnAction(actionEvent -> closeDoor());

        HBox one = new HBox();
        one.setAlignment(Pos.CENTER);
        one.setMinHeight(50);
        one.setStyle("-fx-background-color: #434343; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #000000");
        this.pin = new Label("");
        pin.setStyle(" -fx-background-color: #000000; -fx-background-radius: 10px; -fx-font-size: 30px; -fx-text-fill: #FFFFFF");
        Label pinLabel = new Label(" Pin: ");
        one.getChildren().addAll(pinLabel,pin);

        HBox two = new HBox();
        two.setMinHeight(50);
        two.setAlignment(Pos.CENTER);
        two.setStyle("-fx-background-color: #434343; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #000000");
        this.pwd = new Label(" 0 0 0 0");
        pwd.setStyle(" -fx-background-color: #000000; -fx-background-radius: 10px; -fx-font-size: 30px; -fx-text-fill: #FFFFFF");
        Label pwdLabel = new Label(" Pwd: ");
        two.getChildren().addAll(pwdLabel,pwd);

        HBox three = new HBox();
        three.setMinHeight(50);
        three.setAlignment(Pos.CENTER);
        three.setStyle("-fx-background-color: #434343; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #000000");
        Label doorLabel = new Label(" Door: ");
        three.getChildren().addAll(doorLabel,this.door);

        HBox four = new HBox();
        four.setMinHeight(50);
        four.setAlignment(Pos.CENTER);
        four.setStyle("-fx-background-color: #434343; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #000000");
        Label lockLabel = new Label(" Lock: ");
        four.getChildren().addAll(lockLabel,door.getSensor());

        // setting the same style for all infoLabels
        infoLabels.add(pinLabel);
        infoLabels.add(pwdLabel);
        infoLabels.add(doorLabel);
        infoLabels.add(lockLabel);
        changeLabels.add(pin);
        changeLabels.add(pwd);

        setLabels(infoLabels,changeLabels);

        menu.getChildren().addAll(mechanicalKey,openDoor,closeDoor,one,two,three,four);
    }

    private void setLabels(ArrayList<Label> infoLabels,ArrayList<Label> changeLabels){
        for(Label l: infoLabels){
            l.setMinHeight(50);
            l.setMaxHeight(110);
            l.setMinWidth(110);
            l.setMaxWidth(110);
            l.setStyle(" -fx-font-size: 20px; -fx-text-fill: #ffffff;");
        }

        for(Label l: changeLabels){
            l.setMinHeight(50);
            l.setMaxHeight(110);
            l.setMinWidth(110);
            l.setMaxWidth(110);
        }
    }


    private void setupUI(VBox layout) throws IOException {
        layout.setStyle("-fx-background-color: #434343; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-border-color: #000000; -fx-border-width: 5px");
        layout.setMinHeight(628);
        layout.setMinWidth(375);
        this.display = new Notification(350.0,100.0,this);
        this.display.setAlignment(Pos.CENTER);
        this.keypad = new Keypad(4,3,this);
        this.keypad.setAlignment(Pos.CENTER);
        StackPane stack = new StackPane();
        stack.getChildren().addAll(display);
        stack.setAlignment(batteryIndicator, Pos.TOP_RIGHT); // This aligns the battery indicator to the top right
        stack.setMargin(batteryIndicator, new Insets(-80, 10, 0, 280)); // This gives it a little margin from the top right corner
        stack.getChildren().add(batteryIndicator);

        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(stack,keypad);

    }

    public void pressKey(Key k){
        controller.getKey(k);
    }
    public Key getKey(Key key){
        return key;
    }
    public Keypad getKeypad() {
        return keypad;
    }
    public Notification getDisplay() {
        return display;
    }

    public Code getCode() {
        return code;
    }

    public Door getDoor(){
        return this.door;
    }

    public Label getPin(){
        return this.pin;
    }

    public Label getPwd(){
        return this.pwd;
    }

    public Controller getController(){
        return this.controller;
    }

    public void openDoor(){
        if(!this.getDoor().getSensor().isLocked() && !this.getDoor().isOpenDoor() && !lockout){
            this.getDoor().unlock();
        }
    }

    public void closeDoor(){
        if(this.getDoor().isOpenDoor() && !lockout){
            this.getDoor().lock();
        }
    }

    public void insertMechanicalKey(){
        if(this.getDoor().getSensor().isLocked() && !lockout){
            this.display.unlocked();
            this.getDoor().getSensor().releaseMotor();
        }
    }

    public Timer getTimer(){
        return this.timer;
    }

    public boolean isLockout(){
        return this.lockout;
    }

    public void setLockout(Boolean value){
        this.lockout = value;
    }

    public void setupHeading(Label homeSafe){
        homeSafe.setStyle("-fx-font-size: 50px;-fx-fill: #818181");
    }


}
