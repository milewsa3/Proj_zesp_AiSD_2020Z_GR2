package lab.aisd.log;


import javafx.scene.layout.Pane;
import lab.aisd.gui.collection.PatientIconsCollection;
import lab.aisd.gui.model.AmbulanceIcon;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.gui.model.PatientIcon;
import lab.aisd.model.Patient;

public class AmbulanceFactory {
    private Pane mainArea;
    private Pane mainAreaBox;
    private PatientIconsCollection patientIconsData;

    public AmbulanceFactory(Pane mainArea, Pane mainAreaBox) {
        this.mainArea = mainArea;
        this.mainAreaBox = mainAreaBox;
    }

    public AmbulanceFactory(Pane mainArea, Pane mainAreaBox, PatientIconsCollection patientIconsData) {
        this(mainArea, mainAreaBox);
        this.patientIconsData = patientIconsData;
    }

    public MapObjectIcon createAmbulanceForPatient(Patient patient) {
        if (patientIconsData == null)
            throw new IllegalArgumentException("Patients Icons Data is not loaded");

        MapObjectIcon result = new AmbulanceIcon(
                (int)(-(mainAreaBox.getWidth() - mainArea.getWidth())/2),
                (int)patientIconsData.getPatient(patient).getLayoutY());

        positionIconToBeInTheCenterOfPoint(result);

        result.setLayoutX(result.getLayoutX() + result.getTranslateX());

        return result;
    }

    private void positionIconToBeInTheCenterOfPoint(MapObjectIcon icon) {
        icon.setTranslateX(-icon.getPrefWidth()/2);
        icon.setTranslateY(-icon.getPrefHeight()/2);
    }

    public MapObjectIcon createAmbulanceForPatient(PatientIcon patientIcon) {
        MapObjectIcon result = new AmbulanceIcon(
                (int)(-(mainAreaBox.getWidth() - mainArea.getWidth())/2),
                (int)patientIcon.getLayoutY());

        positionIconToBeInTheCenterOfPoint(result);

        result.setLayoutX(result.getLayoutX() + result.getTranslateX());

        return result;
    }

    public Pane getMainArea() {
        return mainArea;
    }

    public void setMainArea(Pane mainArea) {
        this.mainArea = mainArea;
    }

    public Pane getMainAreaBox() {
        return mainAreaBox;
    }

    public void setMainAreaBox(Pane mainAreaBox) {
        this.mainAreaBox = mainAreaBox;
    }

    public void setPatientIconsData(PatientIconsCollection patientIconsData) {
        this.patientIconsData = patientIconsData;
    }
}
