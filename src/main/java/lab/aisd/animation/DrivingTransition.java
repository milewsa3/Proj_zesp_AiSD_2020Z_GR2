package lab.aisd.animation;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;
import lab.aisd.gui.model.MapObjectIcon;

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
        tt = new TranslateTransition(Duration.millis(2000), node);
        setFromAndTo(from, to);
    }

    public void play() {
        tt.play();
    }

    public final void setOnFinished(EventHandler<ActionEvent> value) {
        tt.setOnFinished(value);
    }

    public double getByX() {
        return tt.getByX();
    }

    public double getByY() {
        return tt.getByY();
    }
}
