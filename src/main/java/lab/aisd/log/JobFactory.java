package lab.aisd.log;

import lab.aisd.gui.collection.PatientIconsCollection;
import lab.aisd.gui.collection.VisualInputData;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.gui.util.Config;
import lab.aisd.model.Hospital;
import lab.aisd.model.MapObject;
import lab.aisd.model.Patient;

public class JobFactory {
    private VisualInputData visualData;
    private PatientIconsCollection patientIconsData;
    private Config config;

    public JobFactory(VisualInputData visualData, PatientIconsCollection patientIconsData, Config config) {
        this.visualData = visualData;
        this.patientIconsData = patientIconsData;
        this.config = config;
    }

    public Job createPickUpJob(MapObjectIcon ambulance, Patient patient) {
        MapObjectIcon patientIcon = patientIconsData.getPatient(patient);

        PickUpPatientJob result = new PickUpPatientJob(ambulance, patientIcon, config);
        result.setDescription(patient);

        return result;
    }

    public Job createPatientTransportJob (
            MapObjectIcon ambulance, Patient patient,
            Hospital from, Hospital to)
    {
        MapObjectIcon fromIcon = visualData.getHospital(from);
        MapObjectIcon toIcon = visualData.getHospital(to);

        PatientTransportJob result = new PatientTransportJob(ambulance, fromIcon, toIcon, config);
        result.setDescription(patient, from, to);

        return result;
    }

    public Job createPatientTransportJob(MapObjectIcon ambulance, Patient patient, Hospital to) {
        MapObjectIcon toIcon = visualData.getHospital(to);

        PatientTransportJob result = new PatientTransportJob(ambulance, ambulance, toIcon, config);
        result.setDescription(patient, to);

        return result;
    }

    public Job createPatientTransportJob (
            MapObjectIcon ambulance, Patient patient,
            Hospital from, MapObject toCrossing, MapObjectIcon toCrossingIcon)
    {
        MapObjectIcon fromIcon = visualData.getHospital(from);
        MapObjectIcon toIcon = toCrossingIcon;

        PatientTransportJob result = new PatientTransportJob(ambulance, fromIcon, toIcon, config);
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

        PatientTransportJob result = new PatientTransportJob(ambulance, fromIcon, toIcon, config);
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

        PatientTransportJob result = new PatientTransportJob(ambulance, fromIcon, toIcon, config);
        result.setDescription(patient, fromCrossing, to);

        return result;
    }

    public Job createLeavePatientInHospitalJob(MapObjectIcon ambulance, Patient patient) {
        LeavePatientInHospitalJob result = new LeavePatientInHospitalJob(ambulance, config);
        result.setDescription(patient);

        return result;
    }

    public Job createLeavePatientOutsideJob(MapObjectIcon ambulance, Patient patient) {
        LeavePatientOutsideJob result = new LeavePatientOutsideJob(ambulance, config);
        result.setDescription(patient);

        return result;
    }
}
