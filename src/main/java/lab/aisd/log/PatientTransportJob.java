package lab.aisd.log;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import lab.aisd.animation.DrivingTransition;
import lab.aisd.animation.FadeInTransition;
import lab.aisd.animation.FadeOutTransition;
import lab.aisd.gui.collection.Config;
import lab.aisd.gui.model.HospitalIcon;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.gui.util.OffsetManager;
import lab.aisd.model.Hospital;
import lab.aisd.model.MapObject;
import lab.aisd.model.Patient;

public class PatientTransportJob extends Job {

    public PatientTransportJob() {
        super();
    }

    public PatientTransportJob(MapObjectIcon ambulance, MapObjectIcon from, MapObjectIcon to) {
        this();
        setAction(ambulance, from, to);
    }

    public void setAction(MapObjectIcon ambulance, MapObjectIcon from, MapObjectIcon to) {
        Action action = () -> {
            double speed = Config.getInstance().getSpeed();

            FadeInTransition fadeIn = new FadeInTransition(Duration.millis(speed), ambulance);
            DrivingTransition drive = new DrivingTransition(Duration.millis(speed * 2), ambulance, from, to);
            FadeOutTransition fadeOut = new FadeOutTransition(Duration.millis(speed), ambulance);

            fadeIn.setOnFinished(event -> drive.play());
            drive.setOnFinished(event ->  {
                OffsetManager.offsetAmbulanceForTransition(ambulance);
                fadeOut.play();
            });
            fadeOut.setOnFinished(event -> setFinished(true));

            fadeIn.play();
        };

        setAction(action);
    }

    public void setDescription(Patient patient, Hospital from, MapObject toCrossing) {
        String desc = "Transporting patient id: " + patient.getId() +
                " from hospital id: " + from.getId() +
                " to crossing";

        setDescription(desc);
    }

    public void setDescription(Patient patient, MapObject fromCrossing, MapObject toCrossing) {
        String desc = "Transporting patient id: " + patient.getId() +
                " from crossing" +
                " to crossing";

        setDescription(desc);
    }

    public void setDescription(Patient patient, MapObject fromCrossing, Hospital to) {
        String desc = "Transporting patient id: " + patient.getId() +
                " from crossing" +
                " to hospital id: " + to.getId();

        setDescription(desc);
    }

    public void setDescription(Patient patient, Hospital from , Hospital to) {
        String desc = "Transporting patient id: " + patient.getId() +
                " from hospital id: " + from.getId() +
                " to hospital id: " + to.getId();

        setDescription(desc);
    }

    public void setDescription(Patient patient, Hospital to) {
        String desc = "Transporting patient id: " + patient.getId() +
                " to hospital id: " + to.getId();

        setDescription(desc);
    }
}
