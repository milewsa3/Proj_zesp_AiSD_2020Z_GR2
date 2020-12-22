package lab.aisd.gui;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class PathCreator {
    public static Line connect(Pane from, Pane to) {
        return new Line(
                from.getLayoutX(),
                from.getLayoutY(),
                to.getLayoutX(),
                to.getLayoutY()
        );
    }
}
