package lab.aisd.gui.collection;

import lab.aisd.gui.model.PatientIcon;
import lab.aisd.model.Patient;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PatientIconsCollection implements Iterable<PatientIcon>{
    private Map<Patient, PatientIcon> patients;

    public PatientIconsCollection() {
        patients = new HashMap<>();
    }

    @Override
    public Iterator<PatientIcon> iterator() {
        return patients.values().iterator();
    }

    public void addPatient(Patient patient, PatientIcon patientIcon) {
        patients.put(patient, patientIcon);
    }

    public PatientIcon getPatient(Patient patient) {
        return patients.get(patient);
    }

    public void setPatients(Map<Patient, PatientIcon> patients) {
        this.patients = patients;
    }

    public int size() {
        return patients.size();
    }

    public Map<Patient, PatientIcon> getPatients() {
        return patients;
    }
}
