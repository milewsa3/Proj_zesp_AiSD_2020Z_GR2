package lab.aisd.gui.generator;

import lab.aisd.gui.model.PatientIcon;
import lab.aisd.gui.util.Scaler;
import lab.aisd.model.Coordinate;
import lab.aisd.model.Patient;

public class PatientGenerator {
    private int x;
    private int y;
    private Scaler scaler;
    private int numberOfPatients;
    private int id;

    private boolean generated = false;

    private Patient patient;
    private PatientIcon patientIcon;

    public PatientGenerator(int x, int y, Scaler scaler, int numberOfPatients, int id) {
        this.x = x;
        this.y = y;
        this.scaler = scaler;
        this.numberOfPatients = numberOfPatients;
        this.id = id;
    }

    public void generate() {
        createPatient();
        createPatientIcon();

        generated = true;
    }

    private void createPatient() {
        patient = new Patient(id, new Coordinate(x, y));
        scaler.unscale(patient);
    }

    private void createPatientIcon() {
        patientIcon = new PatientIcon(x, y);

        patientIcon.setPrefHeight(scaler.getPatientHeight(numberOfPatients));

        patientIcon.setTranslateX(-patientIcon.getPrefWidth()/2);
        patientIcon.setTranslateY(-patientIcon.getPrefHeight()/2);
    }

    private void checkIfDataGenerated() {
        if(!generated)
            throw new RuntimeException("Call generate func first to init values");
    }

    public Patient getPatient() {
        checkIfDataGenerated();

        return patient;
    }

    public PatientIcon getPatientIcon() {
        checkIfDataGenerated();

        return patientIcon;
    }
}
