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
import lab.aisd.algorithm.model.Vertex;
import lab.aisd.animation.FadeInTransition;
import lab.aisd.gui.converter.BoarderMarkerImpl;
import lab.aisd.gui.converter.BorderMarker;
import lab.aisd.gui.collection.PatientIconsCollection;
import lab.aisd.gui.collection.VisualInputData;
import lab.aisd.gui.generator.MapGenerator;
import lab.aisd.gui.generator.PathCreator;
import lab.aisd.gui.generator.PatientGenerator;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.gui.util.ConfirmationAlerter;
import lab.aisd.gui.util.ErrorAlerter;
import lab.aisd.gui.util.InfoAlerter;
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
import java.util.function.Consumer;

import lab.aisd.algorithm.intersections.IntersectionFinder;
import lab.aisd.algorithm.model.Graph;
import lab.aisd.algorithm.shortest_path.*;

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

            InfoAlerter.showDataLoadedSuccessfullyInfo();

        } catch (Exception e) {
            ErrorAlerter.showReadingMapFileError(e.getMessage());
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

            InfoAlerter.showDataLoadedSuccessfullyInfo();

        } catch (Exception e) {
            ErrorAlerter.showReadingPatientFileError(e.getMessage());
        }
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
        if (!ConfirmationAlerter.showUserWantsToStartCalcConfirmation())
            return;

        if (!isDataValid()) {
            ErrorAlerter.showDataNotValidError();
            return;
        }
        
        try {
            new IntersectionFinder().intersectionFinder(mapData);

        } catch (IndexOutOfBoundsException | OutOfMemoryError | NullPointerException e) {
            ErrorAlerter.showIntersectionsError();
        }

        Graph graph = new GraphCreator().createGraph(mapData);
        NearestHospitalFinder finder = new NearestHospitalFinder(graph);
        FloydWarshall fw = new FloydWarshall(graph);
        fw.computeShortestPaths();

        Employer employer = new Employer();
        AmbulanceFactory ambulanceFactory = new AmbulanceFactory(mainArea, mainAreaBox, patientIconsData);
        JobFactory jobFactory = new JobFactory(visualData, patientIconsData);

        Map<Vertex, Hospital> vertexToHospitalMap = new HashMap<>();
        for (int i = 0; i<graph.getNodesNumber(); i++) {
            vertexToHospitalMap.put(graph.getAllNodes().get(i), mapData.getHospitals().get(i));
        }

        for (Patient patient : patientsData) {
            fw.resetVisitedNodes();

            MapObjectIcon ambulance = ambulanceFactory.createAmbulanceForPatient(patient);
            Job createAmbulance = new Job();
            createAmbulance.setAction(() -> {
                addObjectToTheMap(ambulance);
                createAmbulance.setFinished(true);
            });
            createAmbulance.setDescription("Creating ambulance for patient id: " + patient.getId());

            Job pickUpPatient = jobFactory.createPickUpJob(ambulance, patient);
            employer.add(createAmbulance,pickUpPatient);

            Vertex nearestHospitalVertex = finder.findNearestHospitalByCoordinate(patient.getPosition());
            Hospital nearestHospital = vertexToHospitalMap.get(nearestHospitalVertex);

            Job driveToFirstHospital = jobFactory.createPatientTransportJob(ambulance, patient, nearestHospital);
            employer.add(driveToFirstHospital);

            if (nearestHospital.areThereFreeBeds()) {
                Job leavePatient = jobFactory.createLeavePatientJob(ambulance, patient);
                employer.add(leavePatient);
                nearestHospital.setFreeBedsCount(nearestHospital.getFreeBedsCount()-1);

                continue;
            }

            Vertex lastVisitedHospital = nearestHospitalVertex;
            List<Vertex> pathOfVertices;
            List<Hospital> path;
            boolean patientFoundHospital = false;

            while ((pathOfVertices = fw.getPathToNearestNotVisitedHospital(lastVisitedHospital.getOrderedId())) != null) {
                path = new ArrayList<>();
                for (Vertex v : pathOfVertices) {
                    path.add(vertexToHospitalMap.get(v));
                }

                if (path.size() == 2) {
                    Job drivingFromHospitalToHospital = jobFactory
                            .createPatientTransportJob(ambulance, patient ,path.get(0), path.get(1));
                    employer.add(drivingFromHospitalToHospital);

                } else if (path.size() > 2) {
                    Job driveToCrossing = jobFactory
                            .createPatientTransportJob(
                                    ambulance,
                                    patient,
                                    path.get(0),
                                    path.get(1),
                                    mapGenerator.createScaledCrossingIcon(path.get(1))
                            );
                    employer.add(driveToCrossing);

                    for (int i = 2; i < path.size() - 1; i++) {
                        Job driving = jobFactory.createPatientTransportJob(
                                ambulance,
                                patient,
                                path.get(i-1), mapGenerator.createScaledCrossingIcon(path.get(i-1)),
                                path.get(i), mapGenerator.createScaledCrossingIcon(path.get(i))
                        );
                        employer.add(driving);
                    }


                    Job driveToHospital = jobFactory
                            .createPatientTransportJob(
                                    ambulance,
                                    patient,
                                    path.get(path.size() - 2),
                                    mapGenerator.createScaledCrossingIcon(path.get(path.size() - 2)),
                                    path.get(path.size() - 1)
                                    );
                    employer.add(driveToHospital);
                }

                lastVisitedHospital = pathOfVertices.get(pathOfVertices.size() - 1);

                if (lastVisitedHospital.areThereFreeBeds()) {
                    Job leavePatient = jobFactory.createLeavePatientJob(ambulance, patient);
                    employer.add(leavePatient);
                    lastVisitedHospital.setFreeBedsCount(lastVisitedHospital.getFreeBedsCount() - 1);
                    patientFoundHospital = true;
                    break;
                }
            }

            if (!patientFoundHospital) {
                Job leavePatient = jobFactory.createLeavePatientJob(ambulance, patient);
                employer.add(leavePatient);
                //dodać coś żeby pokazać że zostawiam bo nie ma żadnych wolnych łóżek
                System.out.println("Nie ma wolnych szpitali sadge");
            }

        }

        employer.startJobs();
        System.out.println(employer.getAllLogs());
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
                            ErrorAlerter.showMapNotLoadedError();
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
                                ErrorAlerter.showPatientNotWithinBorderError();

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
                if (mapGenerator == null ||
                        mapGenerator.getScaler() == null ||
                        !mapGenerator.getScaler().areRatiosCalculated())
                    return;

                double x = event.getX();
                double y = event.getY();
                Coordinate coords = new Coordinate((int)x,(int)y);

                mapGenerator.getScaler().unscale(coords);

                mousePosLb.setText("x: " + coords.getX() + ", y: " + coords.getY());
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
