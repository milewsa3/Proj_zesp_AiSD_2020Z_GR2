package lab.aisd.gui.generator;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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

    public static Line connect(Pane from, Pane to, Color color) {
        Line result = connect(from, to);
        result.setStroke(color);

        return result;
    }

    public static Line connect(Pane from, Pane to, Color color, double strokeWidth) {
        Line result = connect(from, to, color);
        result.setStrokeWidth(strokeWidth);

        return result;
    }
}
