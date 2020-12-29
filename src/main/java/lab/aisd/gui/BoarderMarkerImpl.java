package lab.aisd.gui;

import lab.aisd.model.MapObject;
import lab.aisd.util.input.InputData;

import java.util.ArrayList;
import java.util.List;

public class BoarderMarkerImpl implements BorderMarker {
    private InputData data;
    private List<MapObject> borderPoints;

    public BoarderMarkerImpl(InputData data) {
        this.data = data;
        borderPoints = new ArrayList<>();
    }

    @Override
    public List<MapObject> calculateBorderPoints() {
        return null;
    }

    @Override
    public boolean isWithinBorder(MapObject obj) {
        return false;
    }
}
