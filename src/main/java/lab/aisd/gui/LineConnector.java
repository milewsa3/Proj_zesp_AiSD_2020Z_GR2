package lab.aisd.gui;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class LineConnector {
    public static Line connect(Pane obj1, Pane obj2) {
        Center c1 = new Center(obj1);
        Center c2 = new Center(obj2);

        return new Line(
                c1.getOrCalcX(),
                c1.getOrCalcY(),
                c2.getOrCalcX(),
                c2.getOrCalcY()
        );
    }

    private static class Center {
        private final Pane pane;
        private Double x;
        private Double y;

        public Center(Pane pane) {
            this.pane = pane;
        }

        public double getOrCalcX() {
            if (x == null)
                calculateCenter();

            return x;
        }

        public double getOrCalcY() {
            if (y == null)
                calculateCenter();

            return y;
        }

        private void calculateCenter() {
            x = pane.getTranslateX() + pane.getBoundsInParent().getWidth() / 2;
            y = pane.getTranslateY() + pane.getBoundsInParent().getHeight() / 2;
        }

        @Override
        public String toString() {
            return "Center{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
