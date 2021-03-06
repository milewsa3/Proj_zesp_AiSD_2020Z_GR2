package lab.aisd.animation;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Random;

public class FadeInTransition {
    private FadeTransition ft;

    public FadeInTransition(Duration duration, Node node) {
        ft = new FadeTransition(duration, node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
    }

    public FadeInTransition(Node node) {
        Random random = new Random();
        ft = new FadeTransition(Duration.millis(250 + random.nextInt(1000)), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
    }

    public void play() {
        ft.play();
    }

    public final void setOnFinished(EventHandler<ActionEvent> value) {
        ft.setOnFinished(value);
    }
}
