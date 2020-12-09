package lab.aisd.util.input;

import lab.aisd.model.Hospital;
import lab.aisd.model.ObjectOnMap;
import lab.aisd.model.Path;

import java.util.ArrayList;
import java.util.List;

public class InputData {
    private final List<Hospital> hospitals;
    private final List<ObjectOnMap> objectsOnMap;
    private final List<Path> paths;

    public InputData() {
        hospitals = new ArrayList<>();
        objectsOnMap = new ArrayList<>();
        paths = new ArrayList<>();
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public List<ObjectOnMap> getObjectsOnMap() {
        return objectsOnMap;
    }

    public List<Path> getPaths() {
        return paths;
    }
}
