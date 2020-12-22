package lab.aisd.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import lab.aisd.gui.*;
import lab.aisd.model.*;
import lab.aisd.util.FxmlView;
import lab.aisd.util.StageManager;
import lab.aisd.util.input.InputData;
import lab.aisd.util.input.InputFileReader;

import java.io.File;
import java.net.URL;
import java.util.*;

public class MapController implements Initializable {

    private InputData mapData;
    private List<Patient> patientsData;

    private VisualInputData visualData;
    private OffsetManager mapOffset;


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
    private Label mousePosLb;

    @FXML
    private AnchorPane mainArea;

    @FXML
    private HBox mainAreaBox;

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
            mapData = new InputFileReader().readMainFile(selectedFile.getPath());

            mapOffset.offset(mapData);

            visualData = new MapGenerator((int)mainArea.getPrefWidth(), (int)mainArea.getPrefHeight())
                    .generate(mapData);

            for (MapObjectIcon icon : visualData)
                addObjectToTheMap(icon);

            draw_paths();

            showDataLoadedSuccessfullyAlert();

        } catch (Exception e) {
            StageManager.getInstance().showAlertScene(
                    Alert.AlertType.ERROR,
                    "Error",
                    "Reading map file error",
                    e.getMessage());
        }
    }

    private void draw_paths() {
        for (Path path : mapData.getPaths()) {
            Hospital from = null;
            Hospital to = null;

            for (Hospital h : mapData.getHospitals()) {
                if (h.getId() == path.getFirstHospitalID()) {
                    from = h;
                    continue;
                }
                if (h.getId() == path.getSecondHospitalID())
                    to = h;
            }

            Line pathLine = PathCreator.connect(
                    visualData.getHospital(from),
                    visualData.getHospital(to)
            );

            addObjectToTheMap(pathLine);
            pathLine.toBack();
        }
    }

    @FXML
    void loadPatients(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(StageManager.getInstance().getStage());
        if (selectedFile == null)
            return;

        try {
            patientsData = new InputFileReader().readPatientsFile(selectedFile.getPath());
            if (mapData == null)
                throw new Exception("Map data must be loaded first");
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
        if (!userWantsToStartCalc())
            return;

        if (!isDataValid()) {
            showDataNotValidError();
            return;
        }

        System.out.println("Calc started");
    }

    private void showDataNotValidError() {
        StageManager.getInstance().showAlertScene(
                Alert.AlertType.ERROR,
                "Error",
                "Data issue",
                "Data is not loaded"
        );
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
        return isMapLoaded() && isPatientsLoaded();
    }

    private boolean isMapLoaded() {
        return mapData != null;
    }

    private boolean isPatientsLoaded() {
        return patientsData != null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        StageManager.getInstance().setResizable(true);
        initAddingPatientsOnDoubleClick();
        initMousePositionLabel();
        mapOffset = new OffsetManager();

        /*mainArea.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));*/
    }

    private void initAddingPatientsOnDoubleClick() {
        mainArea.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2) {
                        if (!isMapLoaded()) {
                            StageManager.getInstance().showAlertScene(
                                    Alert.AlertType.ERROR,
                                    "Error",
                                    "Map not loaded",
                                    "To load patient, you have to load map first"
                            );
                        } else {
                            addObjectToTheMap(new PatientIcon(
                                    (int)mouseEvent.getX() - MapObjectIcon.DEFAULT_ICON_WIDTH / 2,
                                    (int)mouseEvent.getY() - MapObjectIcon.DEFAULT_ICON_WIDTH / 2
                            ));
                        }
                    }
                }
            }
        });
    }

    private void initMousePositionLabel() {
        mainArea.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePosLb.setText("x: " + event.getX() + ", y: " + event.getY());
            }
        });

        mainArea.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mousePosLb.setText("Exited");
            }
        });
    }
}
