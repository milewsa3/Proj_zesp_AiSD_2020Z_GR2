package lab.aisd.gui.collection;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lab.aisd.log.Job;

public class Config {
    private static Config instance;

    private final double DEFAULT_SPEED = 500;
    private double speedScale = 1.0;

    private String displayOption;

    private Config() {
        displayOption = "Animation";
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();

        return instance;
    }

    public void setSpeedScale(double speedScale) {
        if (speedScale > 0 && speedScale < 2)
            this.speedScale = speedScale;
    }

    public double getSpeedScale() {
        return speedScale;
    }

    public double getSpeed() {
        return DEFAULT_SPEED / speedScale;
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
}
