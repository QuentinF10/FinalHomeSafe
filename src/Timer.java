package homesafe.digital;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Timer{
    private AccessSafe safe;
    private static final Integer keyPress = 5;
    private static final Integer lockReverse = 10;
    private static final Integer lockoutTime = 15;

    private final Timeline timeline;
    private final static IntegerProperty keyTime = new SimpleIntegerProperty(keyPress);
    private final static IntegerProperty lockTime = new SimpleIntegerProperty(lockReverse);
    private final static IntegerProperty lockOut = new SimpleIntegerProperty(lockoutTime);

    public Timer(AccessSafe safe){
        timeline = new Timeline();
        this.safe = safe;
    }

    public void keyTimer(){
        keyTime.set(keyPress);
        timeline.getKeyFrames().removeAll();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(keyPress), new KeyValue(keyTime,0)));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public synchronized void handle(ActionEvent actionEvent) {
                safe.getDisplay().timeout();
                safe.getPin().setText("");
                safe.getCode().resetVariables();
            }
        });
        timeline.playFromStart();
    }

    public void lockTimer(){
        System.out.println("Hello there");
        lockTime.set(lockReverse);
        timeline.getKeyFrames().removeAll();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(lockReverse), new KeyValue(lockTime,0)));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                safe.getDisplay().safeLock();
                safe.getDoor().getSensor().setMotor();
                safe.getPin().setText("");
                safe.getCode().resetVariables();
            }
        });
        timeline.playFromStart();
    }

    public void lockout(){
        System.out.println("Hello there");
        lockOut.set(lockoutTime);
        timeline.getKeyFrames().removeAll();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(lockoutTime), new KeyValue(lockOut,0)));
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                safe.getDisplay().enterPin();
                safe.getPin().setText("");
                safe.getCode().resetVariables();
                safe.setLockout(false);
            }
        });
        timeline.playFromStart();
    }
    public Timeline getTimeline() {
        return timeline;
    }
}
