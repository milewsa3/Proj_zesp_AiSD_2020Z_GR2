package lab.aisd.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import lab.aisd.util.MusicPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    MusicPlayer musicPlayer = MusicPlayer.getInstance();

    @FXML
    private Slider volumeSlider;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initSlider();
    }

    private void initSlider() {
        volumeSlider.setValue(musicPlayer.getVolume());

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                musicPlayer.setVolume(newValue.doubleValue());
            }
        });
    }
}
