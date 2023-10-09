package homesafe.digital;

import javafx.scene.control.Label;

public class LockMotor extends Label {
    private boolean isLocked;
    private Door door;

    public LockMotor(Door door){
        super(" Locked");
        this.isLocked = true;
        this.door = door;
        setup();
    }

    public void setMotor(){
        this.door.getSafe().getTimer().getTimeline().stop();
        this.isLocked = true;
        this.setText(" Locked");
        this.setStyle("-fx-background-color: #E0000F; " +
                "-fx-text-fill: #000000; " +
                "-fx-font-size: 20px;" +
                " -fx-background-radius: 10px;" +
                " -fx-border-color: #000000; " +
                "-fx-border-radius: 10px");
    }

    public void releaseMotor(){
        this.door.getSafe().getTimer().lockTimer();
        this.setText(" Unlocked");
        this.setStyle("-fx-background-color: #00E016; -fx-text-fill: #000000; -fx-font-size: 20px; -fx-background-radius: 10px; -fx-border-color: #000000; -fx-border-radius: 10px");
        this.isLocked = false;
    }

    public void setup(){
        this.setMinHeight(50);
        this.setMinWidth(110);
        this.setStyle("-fx-background-color: #E0000F; -fx-text-fill: #000000; -fx-font-size: 20px; -fx-background-radius: 10px; -fx-border-color: #000000; -fx-border-radius: 10px");
    }

    public Boolean isLocked(){
        return this.isLocked;
    }
}
