package lab.aisd.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import javafx.stage.FileChooser;
import lab.aisd.gui.BuildingIcon;
import lab.aisd.gui.HospitalIcon;
import lab.aisd.gui.MapObjectIcon;
import lab.aisd.gui.PatientIcon;
import lab.aisd.model.Building;
import lab.aisd.model.Coordinate;
import lab.aisd.model.Hospital;
import lab.aisd.model.Patient;
import lab.aisd.util.FxmlView;
import lab.aisd.util.MusicPlayer;
import lab.aisd.util.StageManager;
import lab.aisd.util.input.InputData;
import lab.aisd.util.input.InputFileReader;

import java.io.File;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MapController implements Initializable {

    private MusicPlayer musicPlayer = MusicPlayer.getInstance();
    private InputFileReader fileReader = new InputFileReader();
    private InputData mapData;
    private List<Patient> patientsData;

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
    private AnchorPane mainArea;

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void loadMap(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(StageManager.getInstance().getStage());
        if (selectedFile == null)
            return;

        try {
            mapData = fileReader.readMainFile(selectedFile.getPath());
            generateMap();
            showDataLoadedSuccessfullyAlert();

        } catch (Exception e) {
            StageManager.getInstance().showAlertScene(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Reading map file error",
                    e.getMessage());
        }
    }

    private void generateMap() {
        Coordinate endpoint = calculateEndPoint();
        int maxWidth = endpoint.getX();
        int maxHeight = endpoint.getY();
        double widthScaleRatio = (mainArea.getPrefWidth() - MapObjectIcon.ICON_WIDTH)/ maxWidth;
        double heightScaleRatio = (mainArea.getPrefHeight() - MapObjectIcon.ICON_WIDTH) / maxHeight;

        for (Building building : mapData.getObjectsOnMap()) {
            Coordinate coords = building.getCoordinate();
            int x = (int)(coords.getX() * widthScaleRatio);
            int y = (int)(coords.getY() * heightScaleRatio);

            addObjectToTheMap(new BuildingIcon(x, y));
        }

        for (Hospital hospital : mapData.getHospitals()) {
            Coordinate coords = hospital.getCoordinate();
            int x = (int)(coords.getX() * widthScaleRatio);
            int y = (int)(coords.getY() * heightScaleRatio);

            addObjectToTheMap(new HospitalIcon(x, y));
        }
    }

    private Coordinate calculateEndPoint() {
        int maxHeight = 0;
        int maxWidth = 0;

        for (Building building : mapData.getObjectsOnMap()) {
            Coordinate coords = building.getCoordinate();

            if (coords.getX() > maxWidth)
                maxWidth = coords.getX();
            if (coords.getY() > maxHeight)
                maxHeight = coords.getY();
        }

        for (Hospital hospital : mapData.getHospitals()) {
            Coordinate coords = hospital.getCoordinate();

            if (coords.getX() > maxWidth)
                maxWidth = coords.getX();
            if (coords.getY() > maxHeight)
                maxHeight = coords.getY();
        }

        return new Coordinate(maxWidth, maxHeight);
    }

    @FXML
    void loadPatients(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(StageManager.getInstance().getStage());
        if (selectedFile == null)
            return;

        try {
            patientsData = fileReader.readPatientsFile(selectedFile.getPath());
            if (mapData == null)
                throw new Exception("Map data must be loaded first");
            generatePatientsOnMap();
            showDataLoadedSuccessfullyAlert();

        } catch (Exception e) {
            StageManager.getInstance().showAlertScene(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Reading patients file error",
                    e.getMessage());
        }
    }

    private void showDataLoadedSuccessfullyAlert() {
        StageManager.getInstance().showAlertScene(
                Alert.AlertType.INFORMATION,
                "Information",
                "Data loaded successfully",
                "Great. Your data is valid and it was loaded properly."
        );
    }

    private void generatePatientsOnMap() {
        Coordinate endpoint = calculateEndPoint();
        int maxWidth = endpoint.getX();
        int maxHeight = endpoint.getY();
        double widthScaleRatio = (mainArea.getPrefWidth() - MapObjectIcon.ICON_WIDTH)/ maxWidth;
        double heightScaleRatio = (mainArea.getPrefHeight() - MapObjectIcon.ICON_WIDTH) / maxHeight;

        for (Patient patient : patientsData) {
            Coordinate coords = patient.getCoordinate();
            int x = (int)(coords.getX() * widthScaleRatio);
            int y = (int)(coords.getY() * heightScaleRatio);

            addObjectToTheMap(new PatientIcon(x, y));
        }
    }

    private void addObjectToTheMap(Node node) {
        mainArea.getChildren().add(node);
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
        if (!userWantsToStartCalc() || !isDataValid())
            return;

        System.out.println("Calc started");
    }

    private boolean userWantsToStartCalc() {
        Optional<ButtonType> result = StageManager.getInstance().showAlertScene(
                Alert.AlertType.CONFIRMATION,
                "Confirmation Dialog",
                "Confirmation",
                "Are you sure you want to start calculations?");

        return result.get() == ButtonType.OK;
    }

    private boolean isDataValid() {
        if (mapData != null && patientsData != null) {
            return true;
        }
        else {
            StageManager.getInstance().showAlertScene(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Data issue",
                    "Data is not loaded"
            );

            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageManager.getInstance().setResizable(true);
        initAddingPatientsOnDoubleClick();
    }

    private void initAddingPatientsOnDoubleClick() {

    }
}
