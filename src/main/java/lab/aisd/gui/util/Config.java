package lab.aisd.gui.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Config {
    private final double DEFAULT_SPEED = 500;
    private DoubleProperty speedScale;

    private String displayOption;

    public Config() {
        displayOption = "Animation";
        speedScale = new SimpleDoubleProperty(1.0);
    }

    public void setSpeedScale(double speedScale) {
        if (speedScale > 0 && speedScale < 2)
            this.speedScale.set(speedScale);
    }

    public double getSpeedScale() {
        return speedScale.get();
    }

    public DoubleProperty speedScaleProperty() {
        return speedScale;
    }

    public double getSpeed() {
        return DEFAULT_SPEED / speedScale.getValue();
    }

    public String getDisplayOptionString() {
        return displayOption;
    }

    public DisplayOption getDisplayOption() {
        DisplayOption result;

        try {
            result = DisplayOption.valueOf(displayOption.toUpperCase());
        } catch (IllegalArgumentException exc) {
            result = DisplayOption.ANIMATION;
        }

        return result;
    }

    public void setDisplayOption(String displayOption) {
        this.displayOption = displayOption;
    }

    @Override
    public String toString() {
        return "Config{" +
                "DEFAULT_SPEED=" + DEFAULT_SPEED +
                ", speedScale=" + speedScale +
                ", displayOption='" + displayOption + '\'' +
                '}';
    }
}
