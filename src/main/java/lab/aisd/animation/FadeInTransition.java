package lab.aisd.animation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Random;

public class FadeInTransition {
    private FadeTransition ft;

    public FadeInTransition(Node node) {
        Random random = new Random();
        ft = new FadeTransition(Duration.millis(250 + random.nextInt(1000)), node);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
    }

    public void play() {
        ft.play();
    }
}
