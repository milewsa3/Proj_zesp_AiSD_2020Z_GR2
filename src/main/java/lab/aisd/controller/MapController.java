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
import lab.aisd.animation.FadeInTransition;
import lab.aisd.gui.converter.BoarderMarkerImpl;
import lab.aisd.gui.converter.BorderMarker;
import lab.aisd.gui.collection.PatientIconsCollection;
import lab.aisd.gui.collection.VisualInputData;
import lab.aisd.gui.generator.MapGenerator;
import lab.aisd.gui.generator.PathCreator;
import lab.aisd.gui.generator.PatientGenerator;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.gui.util.OffsetManager;
import lab.aisd.log.*;
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
    private PatientIconsCollection patientIconsData;
    private BorderMarker borderMarker;

    private OffsetManager offsetManager;
    private MapGenerator mapGenerator;


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

            offsetManager.offset(mapData);

            visualData = mapGenerator.generate(mapData);

            for (MapObjectIcon icon : visualData)
                addObjectToTheMap(icon);

            draw_paths();

            draw_border();

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

    private void draw_border() {
        borderMarker = new BoarderMarkerImpl(mapData);

        List<MapObject> borderPoints = borderMarker.calculateBorderPoints();

        if (borderPoints.size() == 0)
            return;

        for (int i=1;i<borderPoints.size();i++) {
            createAndAddBorder(borderPoints.get(i-1), borderPoints.get(i));
        }

        createAndAddBorder(borderPoints.get(borderPoints.size()-1), borderPoints.get(0));
    }

    private void createAndAddBorder(MapObject from, MapObject to) {
        Line border = PathCreator.connect(
                getProperIcon(from),
                getProperIcon(to),
                Color.RED,
                8
        );

        addObjectToTheMap(border);
        border.toBack();
    }

    private MapObjectIcon getProperIcon(MapObject obj) {
        if (obj instanceof Hospital) {
            return visualData.getHospital((Hospital) obj);
        } else if (obj instanceof Building){
            return visualData.getBuilding((Building) obj);
        } else {
            throw new IllegalArgumentException(obj + " is not an instance of Hospital or Building");
        }
    }

    @FXML
    void loadPatients(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(StageManager.getInstance().getStage());
        if (selectedFile == null)
            return;

        try {
            if (mapData == null)
                throw new Exception("Map data must be loaded first");

            patientsData = new InputFileReader().readPatientsFile(selectedFile.getPath());

            offsetManager.offset(patientsData);

            patientIconsData = mapGenerator.generate(patientsData);

            for (MapObjectIcon icon : patientIconsData)
                addObjectToTheMap(icon);

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
        FadeInTransition ft = new FadeInTransition(node);

        mainArea.getChildren().add(node);
        ft.play();
    }

    @FXML
    void newMap(ActionEvent event) {
        StageManager.getInstance().switchScene(FxmlView.MAP);
    }

    @FXML
    void showAbout(ActionEvent event) {
        StageManager.getInstance().openNewFocusedWindow(FxmlView.ABOUT);
    }

    @FXML
    void close(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void openConfig(ActionEvent event) {
        StageManager.getInstance().openNewFocusedWindow(FxmlView.CONFIG);
    }

    @FXML
    void openSettings(ActionEvent event) {
        StageManager.getInstance().openNewFocusedWindow(FxmlView.SETTINGS);
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

        Employer employer = new Employer();

        AmbulanceFactory ambulanceFactory = new AmbulanceFactory(mainArea, mainAreaBox, patientIconsData);
        JobFactory jobFactory = new JobFactory(visualData, patientIconsData);

        MapObjectIcon ambulance = ambulanceFactory.createAmbulanceForPatient(patientsData.get(0));
        addObjectToTheMap(ambulance);
        Job pickUp = jobFactory.createPickUpJob(ambulance, patientsData.get(0));
        Job driveToHospital = jobFactory.createPatientTransportJob(ambulance,
                patientsData.get(0), mapData.getHospitals().get(0));
        Job driveFromHospitalToHospital = jobFactory.createPatientTransportJob(ambulance,
                patientsData.get(0), mapData.getHospitals().get(0), mapData.getHospitals().get(1));
        employer.add(pickUp, driveToHospital, driveFromHospitalToHospital);

        //new Thread(employer::startJobs).start();

        StageManager.getInstance().openNewNotFocusedWindow(FxmlView.LOG);
        new Thread(() -> {
            Logger.getInstance().add(employer.getAllLogs());
        }).start();

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
        offsetManager = new OffsetManager();

        mapGenerator = new MapGenerator((int)mainArea.getPrefWidth(), (int)mainArea.getPrefHeight());
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
                            if (patientIconsData == null)
                                return;

                            PatientGenerator pg = new PatientGenerator(
                                    (int)mouseEvent.getX(),
                                    (int)mouseEvent.getY(),
                                    mapGenerator.getScaler(),
                                    patientsData.size(),
                                    patientIconsData.size() + 1
                            );

                            pg.generate();

                            if (!borderMarker.isWithinBorder(pg.getPatient())) {
                                StageManager.getInstance().showAlertScene(
                                        Alert.AlertType.ERROR,
                                        "Error",
                                        "Patient is not within the border",
                                        "You must locate patient within the border!"
                                );

                                return;
                            }

                            patientsData.add(pg.getPatient());
                            patientIconsData.addPatient(pg.getPatient(), pg.getPatientIcon());
                            addObjectToTheMap(pg.getPatientIcon());
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
