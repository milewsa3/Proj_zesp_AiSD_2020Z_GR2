package lab.aisd.gui;

import lab.aisd.model.Building;
import lab.aisd.model.Coordinate;
import lab.aisd.model.Hospital;
import lab.aisd.util.input.InputData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapGenerator {
    private final Scaler scaler;

    public MapGenerator(int windowWidth, int windowHeight) {
        scaler = new Scaler(windowWidth, windowHeight);
    }

    public VisualInputData generate(InputData data) {
        VisualInputData result = new VisualInputData();
        scaler.calculateRatios(calculateCornerPoint(data));

        result.setHospitals(generateHospitals(data.getHospitals()));
        result.setBuildings(generateBuildings(data.getBuildings()));

        return result;
    }

    private Coordinate calculateCornerPoint(InputData data) {
        int maxHeight = 0;
        int maxWidth = 0;

        for (Building building : data.getBuildings()) {
            Coordinate coords = building.getPosition();

            if (coords.getX() > maxWidth)
                maxWidth = coords.getX();
            if (coords.getY() > maxHeight)
                maxHeight = coords.getY();
        }

        for (Hospital hospital : data.getHospitals()) {
            Coordinate coords = hospital.getPosition();

            if (coords.getX() > maxWidth)
                maxWidth = coords.getX();
            if (coords.getY() > maxHeight)
                maxHeight = coords.getY();
        }

        return new Coordinate(maxWidth, maxHeight);
    }

    private Map<Hospital, HospitalIcon> generateHospitals(List<Hospital> hospitals) {
        Map<Hospital, HospitalIcon> result = new HashMap<>();
        int iconHeight = scaler.getHospitalHeight(hospitals.size());

        for (Hospital h : hospitals) {
            HospitalIcon icon = createHospitalIcon(h, iconHeight);
            scaler.scale(icon);

            result.put(h, icon);
        }

        return result;
    }

    private HospitalIcon createHospitalIcon(Hospital hospital, int iconHeight) {
        Coordinate position = hospital.getPosition();
        HospitalIcon icon = new HospitalIcon(position.getX(), position.getY());
        icon.setPrefHeight(iconHeight);
        positionIconToBeInTheCenterOfPoint(icon);

        return icon;
    }

    private void positionIconToBeInTheCenterOfPoint(MapObjectIcon icon) {
        icon.setTranslateX(-icon.getPrefWidth()/2);
        icon.setTranslateY(-icon.getPrefHeight()/2);
    }

    private Map<Building, BuildingIcon> generateBuildings(List<Building> buildings) {
        Map<Building, BuildingIcon> result = new HashMap<>();

        int iconHeight = scaler.getBuildingHeight(buildings.size());

        for (Building b : buildings) {
            BuildingIcon icon = createBuildingIcon(b, iconHeight);
            scaler.scale(icon);

            result.put(b, icon);
        }

        return result;
    }

    private BuildingIcon createBuildingIcon(Building building, int iconHeight) {
        Coordinate position = building.getPosition();
        BuildingIcon icon = new BuildingIcon(position.getX(), position.getY());
        icon.setPrefHeight(iconHeight);
        positionIconToBeInTheCenterOfPoint(icon);

        return icon;
    }
}
