package homesafe.digital;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Door - represents the door of the Home Safe
 */
public class Door extends Label {
    private boolean openDoor;
    private LockMotor sensor;
    private AccessSafe safe;

    /**
     * Door - constructor for the door class
     */
    public Door(AccessSafe safe){
        super(" Closed");
        this.safe = safe;
        this.sensor = new LockMotor(this);
        setup();
        // the door is closed intitally
        this.openDoor = false;
    }

    /**
     * lock() - locks the door
     */
    public void lock(){
        this.safe.getTimer().lockTimer();
        this.openDoor = false;
        this.setText("Closed");
        this.setStyle("-fx-background-color: #E0000F; " +
                "-fx-text-fill: #000000; " +
                "-fx-font-size: 20px; " +
                "-fx-background-radius: 10px;" +
                " -fx-border-color: #000000;" +
                " -fx-border-radius: 10px");
    }


    /**
     * unlock() - unlocks the door
     */
    public void unlock(){
        this.safe.getTimer().getTimeline().stop();
        this.openDoor = true;
        this.setText("Open");
        this.setStyle("-fx-background-color: #00E016; " +
                "-fx-text-fill: #000000; " +
                "-fx-font-size: 20px; " +
                "-fx-background-radius: 10px;" +
                " -fx-border-color: #000000;" +
                " -fx-border-radius: 10px");
    }


    /**
     * isOpenDoor() - checks the status of the door
     * @return - true if open and false otherwise
     */
    public boolean isOpenDoor(){
        return this.openDoor;
    }

    public void setup(){
        this.setMinHeight(50);
        this.setMinWidth(110);
        this.setStyle("-fx-background-color: #E0000F;" +
                " -fx-text-fill: #000000; " +
                "-fx-font-size: 20px; " +
                "-fx-background-radius: 10px;" +
                " -fx-border-color: #000000;" +
                " -fx-border-radius: 10px");
    }

    public LockMotor getSensor(){
        return this.sensor;
    }

    public AccessSafe getSafe(){
        return this.safe;
    }
}
