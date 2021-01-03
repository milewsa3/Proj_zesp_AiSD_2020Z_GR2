package lab.aisd.gui.util;

import lab.aisd.animation.DrivingTransition;
import lab.aisd.gui.model.AmbulanceIcon;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.model.*;
import lab.aisd.util.input.InputData;

import java.util.List;

public class OffsetManager {
    private Integer offsetX;
    private Integer offsetY;

    public void offset(InputData data) {
        if (offsetX == null || offsetY == null)
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

    private void offset(MapObject object) {
        object.getPosition().offsetBy(offsetX, offsetY);
    }

    public void offset(List<Patient> patients) {
        if (offsetX == null || offsetY == null)
            throw new RuntimeException("Offset should be already calculated using map file");

        for (Patient p : patients) {
            offset(p);
        }
    }

    public static void offsetAmbulanceForTransition(MapObjectIcon ambulance) {
        double w = -ambulance.getPrefWidth()/2;
        double h = -ambulance.getPrefHeight()/2;

        ambulance.setLayoutX(ambulance.getLayoutX() + ambulance.getTranslateX() - w);
        ambulance.setLayoutY(ambulance.getLayoutY() + ambulance.getTranslateY() - h);

        ambulance.setTranslateX(w);
        ambulance.setTranslateY(h);
    }
}
