package lab.aisd.util.input;

import lab.aisd.model.Building;
import lab.aisd.model.Hospital;
import lab.aisd.model.Path;

import java.util.ArrayList;
import java.util.List;

public class InputData {
    private final List<Hospital> hospitals;
    private final List<Building> buildings;
    private final List<Path> paths;

    public InputData() {
        hospitals = new ArrayList<>();
        buildings = new ArrayList<>();
        paths = new ArrayList<>();
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public List<Path> getPaths() {
        return paths;
    }
}
