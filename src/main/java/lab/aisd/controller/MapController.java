package lab.aisd.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.stage.FileChooser;
import lab.aisd.Main;
import lab.aisd.util.FxmlView;
import lab.aisd.util.MusicPlayer;
import lab.aisd.util.StageManager;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    private MusicPlayer musicPlayer = MusicPlayer.getInstance();

    @FXML
    private MenuItem newMenuIt;

    @FXML
    private MenuItem loadMapMenuIt;

    @FXML
    private MenuItem loadPatientsMenuIt;

    @FXML
    private MenuItem deleteMenuIt;

    @FXML
    private MenuItem aboutMenuIt;

    @FXML
    private MenuItem closeMenuIt;

    @FXML
    private Button startBt;

    @FXML
    private Button configBt;

    @FXML
    private Button settingsBt;

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void loadMap(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(StageManager.getInstance().getStage());
    }

    @FXML
    void loadPatients(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(StageManager.getInstance().getStage());
    }

    @FXML
    void newMap(ActionEvent event) {
        StageManager.getInstance().switchScene(FxmlView.MAP);
    }

    @FXML
    void showAbout(ActionEvent event) {
        StageManager.getInstance().openNewScene(FxmlView.ABOUT);
    }

    @FXML
    void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void openConfig(ActionEvent event) {
        StageManager.getInstance().openNewScene(FxmlView.CONFIG);
    }

    @FXML
    void openSettings(ActionEvent event) {
        StageManager.getInstance().openNewScene(FxmlView.SETTINGS);
    }

    @FXML
    void startCalc(ActionEvent event) {
        // Main calculations of the program (Business logic)
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageManager.getInstance().setResizable(true);
    }
}
