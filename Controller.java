package homesafe.digital;

import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;

public class Controller extends Application {
    private AccessSafe safe;

    @Override
    public void start(Stage stage) throws IOException {
        this.safe = new AccessSafe(new VBox(50),1200,800,this);
        stage.setTitle("Home Safe");
        stage.setScene(safe);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void getKey(Key key){
        Boolean openDoor = safe.getDoor().isOpenDoor();
        Boolean setLock = safe.getDoor().getSensor().isLocked();
        Boolean lockout = safe.isLockout();

        if(!openDoor && !lockout) {
            this.safe.getTimer().keyTimer();
            char k = key.getValue();

            String pin = this.safe.getCode().getPin();
            ArrayList<Boolean> reset = this.safe.getCode().getReset();
            ArrayList<Boolean> override = this.safe.getCode().getOverride();

            if (k == '*' && pin.isEmpty()) {
                this.safe.getCode().getReset().set(0, true);
                this.safe.getDisplay().oldPassword();
            } else if (k == '#' && pin.isEmpty() && !reset.get(0)) {
                if (!safe.getDoor().getSensor().isLocked()) {
                    this.safe.getCode().getOverride().set(0, true);
                    this.safe.getDisplay().newPassword();
                } else safe.getDisplay().doorIsClosed();
            } else if (pin.length() < 4 && reset.get(0)) {
                safe.getCode().reset(k);
            } else if (pin.length() < 4 && override.get(0)) {
                safe.getCode().override(k);
            } else if (pin.length() < 4 && !reset.get(0) && !override.get(0) && setLock) {
                safe.getCode().regularEntry(k);
            }
        }
    }

}
