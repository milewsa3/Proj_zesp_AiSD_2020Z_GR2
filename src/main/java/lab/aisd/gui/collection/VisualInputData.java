package lab.aisd.gui.collection;

import lab.aisd.gui.model.BuildingIcon;
import lab.aisd.gui.model.HospitalIcon;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.model.Building;
import lab.aisd.model.Hospital;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VisualInputData implements Iterable<MapObjectIcon>{
    private Map<Hospital, HospitalIcon> hospitals;
    private Map<Building, BuildingIcon> buildings;

    public VisualInputData() {
        hospitals = new HashMap<>();
        buildings = new HashMap<>();
    }

    @Override
    public Iterator<MapObjectIcon> iterator() {
        return new Iterator<MapObjectIcon>() {
            Iterator<HospitalIcon> it1 = hospitals.values().iterator();
            Iterator<BuildingIcon> it2 = buildings.values().iterator();

            @Override
            public boolean hasNext() {
                return it1.hasNext() || it2.hasNext();
            }

            @Override
            public MapObjectIcon next() {
                if (it1.hasNext())
                    return it1.next();
                else return it2.next();
            }
        };
    }

    public void addHospital(Hospital hospital, HospitalIcon hospitalIcon) {
        hospitals.put(hospital, hospitalIcon);
    }

    public void addBuilding(Building building, BuildingIcon buildingIcon) {
        buildings.put(building, buildingIcon);
    }

    public HospitalIcon getHospital(Hospital hospital) {
        return hospitals.get(hospital);
    }

    public BuildingIcon getBuilding (Building building) {
        return buildings.get(building);
    }

    public void setHospitals(Map<Hospital, HospitalIcon> hospitals) {
        this.hospitals = hospitals;
    }

    public void setBuildings(Map<Building, BuildingIcon> buildings) {
        this.buildings = buildings;
    }

    public Map<Hospital, HospitalIcon> getHospitals() {
        return hospitals;
    }
}
