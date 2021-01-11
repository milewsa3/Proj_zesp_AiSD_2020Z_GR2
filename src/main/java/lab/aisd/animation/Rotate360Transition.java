package lab.aisd.animation;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Random;

public class Rotate360Transition {
    private RotateTransition rt;

    public Rotate360Transition(Duration duration, Node node) {
        rt = new RotateTransition(duration, node);
        rt.setByAngle(360);
    }

    public Rotate360Transition(Node node) {
        Random random = new Random();
        rt = new RotateTransition(Duration.millis(250 + random.nextInt(1000)), node);
        rt.setByAngle(360);
    }

    public void play() {
        rt.play();
    }

    public final void setOnFinished(EventHandler<ActionEvent> value) {
        rt.setOnFinished(value);
    }
}
