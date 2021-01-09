package lab.aisd.log;

import lab.aisd.gui.collection.PatientIconsCollection;
import lab.aisd.gui.collection.VisualInputData;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.model.Hospital;
import lab.aisd.model.MapObject;
import lab.aisd.model.Patient;
import lab.aisd.util.input.InputData;

import java.util.List;

public class JobFactory {
    private VisualInputData visualData;
    private PatientIconsCollection patientIconsData;

    public JobFactory(VisualInputData visualData, PatientIconsCollection patientIconsData) {
        this.visualData = visualData;
        this.patientIconsData = patientIconsData;
    }

    public Job createPickUpJob(MapObjectIcon ambulance, Patient patient) {
        MapObjectIcon patientIcon = patientIconsData.getPatient(patient);

        PickUpPatientJob result = new PickUpPatientJob(ambulance, patientIcon);
        result.setDescription(patient);

        return result;
    }

    public Job createPatientTransportJob (
            MapObjectIcon ambulance, Patient patient,
            Hospital from, Hospital to)
    {
        MapObjectIcon fromIcon = visualData.getHospital(from);
        MapObjectIcon toIcon = visualData.getHospital(to);

        PatientTransportJob result = new PatientTransportJob(ambulance, fromIcon, toIcon);
        result.setDescription(patient, from, to);

        return result;
    }

    public Job createPatientTransportJob(MapObjectIcon ambulance, Patient patient, Hospital to) {
        MapObjectIcon toIcon = visualData.getHospital(to);

        PatientTransportJob result = new PatientTransportJob(ambulance, ambulance, toIcon);
        result.setDescription(patient, to);

        return result;
    }

    public Job createPatientTransportJob (
            MapObjectIcon ambulance, Patient patient,
            Hospital from, MapObject toCrossing, MapObjectIcon toCrossingIcon)
    {
        MapObjectIcon fromIcon = visualData.getHospital(from);
        MapObjectIcon toIcon = toCrossingIcon;

        PatientTransportJob result = new PatientTransportJob(ambulance, fromIcon, toIcon);
        result.setDescription(patient, from, toCrossing);

        return result;
    }

    public Job createPatientTransportJob (
            MapObjectIcon ambulance, Patient patient
            ,MapObject fromCrossing, MapObjectIcon fromCrossingIcon
            ,MapObject toCrossing, MapObjectIcon toCrossingIcon
    )
    {
        MapObjectIcon fromIcon = fromCrossingIcon;
        MapObjectIcon toIcon = toCrossingIcon;

        PatientTransportJob result = new PatientTransportJob(ambulance, fromIcon, toIcon);
        result.setDescription(patient, fromCrossing, toCrossing);

        return result;
    }

    public Job createPatientTransportJob (
            MapObjectIcon ambulance, Patient patient
            ,MapObject fromCrossing, MapObjectIcon fromCrossingIcon
            ,Hospital to
    )
    {
        MapObjectIcon fromIcon = fromCrossingIcon;
        MapObjectIcon toIcon = visualData.getHospital(to);

        PatientTransportJob result = new PatientTransportJob(ambulance, fromIcon, toIcon);
        result.setDescription(patient, fromCrossing, to);

        return result;
    }

    public Job createLeavePatientJob(MapObjectIcon ambulance, Patient patient) {
        LeavePatientJob result = new LeavePatientJob(ambulance);
        result.setDescription(patient);

        return result;
    }
}
