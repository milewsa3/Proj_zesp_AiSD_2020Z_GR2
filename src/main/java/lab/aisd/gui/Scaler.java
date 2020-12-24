package lab.aisd.gui;

import javafx.scene.Node;
import lab.aisd.model.Coordinate;
import lab.aisd.model.MapObject;

public class Scaler {
    public final int WINDOW_WIDTH;
    public final int WINDOW_HEIGHT;

    private double widthRatio = 0.0;
    private double heightRatio = 0.0;
    private boolean ratiosCalculated = false;

    public Scaler(int windowWidth, int windowHeight) {
        WINDOW_WIDTH = windowWidth;
        WINDOW_HEIGHT = windowHeight;
    }

    public void calculateRatios(Coordinate cornerPoint) {
        widthRatio = (double)WINDOW_WIDTH / (double)cornerPoint.getX();
        heightRatio = (double)WINDOW_HEIGHT / (double)cornerPoint.getY();

        ratiosCalculated = true;
    }

    public void scale(Node node) {
        checkIfRatiosCalculated();

        double oldX = node.getLayoutX();
        double oldY = node.getLayoutY();

        node.setLayoutX(oldX * widthRatio);
        node.setLayoutY(oldY * heightRatio);
    }

    private void checkIfRatiosCalculated() {
        if (!ratiosCalculated)
            throw new RuntimeException("Ratios must be calculated first");
    }

    public boolean areRatiosCalculated() {
        return ratiosCalculated;
    }

    public void scale(Node ... nodes) {
        for (Node n : nodes)
            scale(n);
    }

    public void unscale(MapObject obj) {
        checkIfRatiosCalculated();

        Coordinate pos = obj.getPosition();
        pos.setX((int)(pos.getX() / widthRatio));
        pos.setY((int)(pos.getY() / heightRatio));
    }

    public void unscale(Node node) {
        checkIfRatiosCalculated();

        double oldX = node.getLayoutX();
        double oldY = node.getLayoutY();

        node.setLayoutX(oldX / widthRatio);
        node.setLayoutY(oldY / heightRatio);
    }

    public void unscale(Node ... nodes) {
        for (Node n : nodes)
            unscale(n);
    }

    public int getHospitalHeight(int numOfObjs) {
        if (numOfObjs < 10)
            return 80;
        else if (numOfObjs < 100)
            return 50;
        else if (numOfObjs < 500)
            return 10;
        else
            return 5;
    }

    public int getBuildingHeight(int numOfObjs) {
        return getHospitalHeight(numOfObjs) -20;
    }

    public int getPatientHeight(int numOfObjs) {
        return getHospitalHeight(numOfObjs);
    }

    public double getWidthRatio() {
        return widthRatio;
    }

    public double getHeightRatio() {
        return heightRatio;
    }
}
