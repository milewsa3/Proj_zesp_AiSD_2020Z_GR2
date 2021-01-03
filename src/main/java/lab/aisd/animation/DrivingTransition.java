package lab.aisd.animation;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.model.MapObject;

import java.util.Random;

public class DrivingTransition {
    private TranslateTransition tt;

    public DrivingTransition(Duration duration, Node node, MapObjectIcon from, MapObjectIcon to) {
        tt = new TranslateTransition(duration, node);
        setFromAndTo(from, to);
    }

    private void setFromAndTo(MapObjectIcon from, MapObjectIcon to) {
        tt.setByX(to.getLayoutX() - from.getLayoutX());
        tt.setByY(to.getLayoutY() - from.getLayoutY());
    }

    public DrivingTransition(Node node, MapObjectIcon from, MapObjectIcon to) {
        Random random = new Random();
        tt = new TranslateTransition(Duration.millis(2000), node);
        setFromAndTo(from, to);
    }

    public void play() {
        tt.play();
    }

    public final void setOnFinished(EventHandler<ActionEvent> value) {
        tt.setOnFinished(value);
    }
}
