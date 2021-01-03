package lab.aisd.log;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import lab.aisd.animation.DrivingTransition;
import lab.aisd.animation.FadeInTransition;
import lab.aisd.animation.FadeOutTransition;
import lab.aisd.gui.model.HospitalIcon;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.model.Hospital;
import lab.aisd.model.Patient;

public class PatientTransportJob extends Job {

    public PatientTransportJob() {
        super();
    }

    public PatientTransportJob(MapObjectIcon ambulance, HospitalIcon from, HospitalIcon to) {
        this();

        setAction(ambulance, from, to);
    }

    public void setAction(MapObjectIcon ambulance, HospitalIcon from, HospitalIcon to) {
        Action action = () -> {
            FadeInTransition fadeIn = new FadeInTransition(Duration.millis(500), ambulance);
            DrivingTransition drive = new DrivingTransition(ambulance, from, to);
            FadeOutTransition fadeOut = new FadeOutTransition(Duration.millis(500), ambulance);

            fadeIn.setOnFinished(event -> drive.play());
            drive.setOnFinished(event -> fadeOut.play());

            fadeIn.play();
        };

        setAction(action);
    }

    public void setDescription(Patient patient, Hospital from , Hospital to) {
        String desc = "Transporting patient id: " + patient.getId() +
                " from hospital id: " + from.getId() +
                " to hospital id: " + to.getId();

        setDescription(desc);
    }
}
