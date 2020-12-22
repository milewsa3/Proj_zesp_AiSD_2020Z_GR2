package lab.aisd.gui;

import lab.aisd.model.Building;
import lab.aisd.model.Coordinate;
import lab.aisd.model.Hospital;
import lab.aisd.util.input.InputData;

public class OffsetManager {
    private Integer offsetX;
    private Integer offsetY;

    public void offset(InputData data) {
        calculateOffset(data);

        for (Hospital h : data.getHospitals())
            offset(h);

        for (Building b : data.getBuildings())
            offset(b);
    }

    private void calculateOffset(InputData data) {
        int smallestX = 0;
        int smallestY = 0;

        for (Hospital h : data.getHospitals()) {
            Coordinate position = h.getPosition();

            if (position.getX() < smallestX)
                smallestX = position.getX();

            if (position.getY() < smallestY)
                smallestY = position.getY();
        }

        for (Building b : data.getBuildings()) {
            Coordinate position = b.getPosition();

            if (position.getX() < smallestX)
                smallestX = position.getX();

            if (position.getY() < smallestY)
                smallestY = position.getY();
        }

        offsetX = -smallestX;
        offsetY = -smallestY;
    }

    private void offset(Hospital h) {
        h.getPosition().offsetBy(offsetX, offsetY);
    }

    private void offset(Building b) {
        b.getPosition().offsetBy(offsetX, offsetY);
    }
}
