package lab.aisd.gui;

import lab.aisd.model.MapObject;
import lab.aisd.util.input.InputData;

import java.util.List;

public interface BorderMarker {
    List<MapObject> calculateBorderPoints ();
    boolean isWithinBorder(MapObject obj);
}
