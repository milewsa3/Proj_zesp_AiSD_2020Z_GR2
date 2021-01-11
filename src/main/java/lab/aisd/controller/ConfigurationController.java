package lab.aisd.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import lab.aisd.gui.util.Config;
import lab.aisd.gui.util.DisplayOption;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationController implements Initializable {
    private Config config;

    @FXML
    private RadioButton animationRb;

    @FXML
    private ToggleGroup displayOpt;

    @FXML
    private RadioButton logsRb;

    @FXML
    private Slider speedSlider;

    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSpeedSlider();
        initConfig();
        initDisplayOpt();
    }

    private boolean isConfigInitialized() {
        return config != null;
    }

    private void initDisplayOpt() {
        if (!isConfigInitialized())
            return;

        if (config.getDisplayOption() == DisplayOption.ANIMATION)
            animationRb.setSelected(true);
        else {
            logsRb.setSelected(true);
        }
    }

    private void initConfig() {
        if (!isConfigInitialized())
            return;

        displayOpt.selectedToggleProperty().addListener((observable, oldValue, newValue) ->
                config.setDisplayOption(((RadioButton) newValue).getText()));
    }

    private void initSpeedSlider() {
        if (!isConfigInitialized())
            return;

        speedSlider.setValue(config.getSpeedScale());

        config.speedScaleProperty().bind(speedSlider.valueProperty());
    }
}