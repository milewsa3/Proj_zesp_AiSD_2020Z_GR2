package lab.aisd.gui.converter;

import lab.aisd.algorithm.border.ConvexHull;
import lab.aisd.model.Building;
import lab.aisd.model.Coordinate;
import lab.aisd.model.Hospital;
import lab.aisd.model.MapObject;
import lab.aisd.util.input.InputData;

import java.util.ArrayList;
import java.util.List;

public class BoarderMarkerImpl implements BorderMarker {
    private InputData data;
    private List<MapObject> borderPoints;
    private ConvexHull convexHull;

    public BoarderMarkerImpl(InputData data) {
        this.data = data;
        borderPoints = new ArrayList<>();
        convexHull = new ConvexHull();
    }

    @Override
    public List<MapObject> calculateBorderPoints() {
        addAllMapObjsToConvexHull();
        convexHull.prepare();

        List<Coordinate> borderCoordinates = convexHull.getBorderPoints();
        borderPoints.addAll(convertCoordinatesToMapObjs(borderCoordinates));

        return borderPoints;
    }

    private void addAllMapObjsToConvexHull() {
        for (Hospital h : data.getHospitals())
            convexHull.add(h.getPosition());

        for (Building b : data.getBuildings())
            convexHull.add(b.getPosition());
    }

    private List<MapObject> convertCoordinatesToMapObjs(List<Coordinate> coords) {
        List<MapObject> result = new ArrayList<>();

        for (Coordinate c : coords) {
            result.add(findCorrespondingMapObj(c));
        }

        return result;
    }

    private MapObject findCorrespondingMapObj (Coordinate c) {
        for (Hospital h : data.getHospitals()) {
            if (h.getPosition().equals(c))
                return h;
        }

        for (Building b : data.getBuildings()) {
            if (b.getPosition().equals(c))
                return b;
        }

        return null;
    }

    @Override
    public boolean isWithinBorder(MapObject obj) {
        return convexHull.isPointWithinTheBorder(obj.getPosition());
    }
}
