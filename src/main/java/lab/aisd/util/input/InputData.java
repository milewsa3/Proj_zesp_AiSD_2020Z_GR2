package lab.aisd.util.input;

import lab.aisd.model.Hospital;
import lab.aisd.model.ObjectOnMap;
import lab.aisd.model.Path;

import java.util.ArrayList;
import java.util.List;

public class InputData {
    private List<Hospital> hospitals;
    private List<ObjectOnMap> objectsOnMap;
    private List<Path> paths;

    public InputData() {
        hospitals = new ArrayList<>();
        objectsOnMap = new ArrayList<>();
        paths = new ArrayList<>();
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public List<ObjectOnMap> getObjectsOnMap() {
        return objectsOnMap;
    }

    public void setObjectsOnMap(List<ObjectOnMap> objectsOnMap) {
        this.objectsOnMap = objectsOnMap;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }

}
