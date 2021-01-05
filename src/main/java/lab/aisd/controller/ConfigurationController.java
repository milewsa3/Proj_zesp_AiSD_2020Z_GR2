package lab.aisd.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import lab.aisd.log.Job;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfigurationController implements Initializable {

    @FXML
    private RadioButton animationRb;

    @FXML
    private ToggleGroup displayOpt;

    @FXML
    private RadioButton logsRb;

    @FXML
    private Slider speedSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSpeedSlider();


    }

    private void initSpeedSlider() {
        speedSlider.setValue(Job.getSpeedScale());

        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Job.setSpeedScale(newValue.doubleValue());
            }
        });
    }
}