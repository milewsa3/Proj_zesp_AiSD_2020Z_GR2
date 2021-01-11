package lab.aisd.log;

import javafx.util.Duration;
import lab.aisd.animation.DrivingTransition;
import lab.aisd.animation.FadeOutTransition;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.gui.util.Config;
import lab.aisd.gui.util.OffsetManager;
import lab.aisd.model.Patient;

public class PickUpPatientJob extends Job{

    public PickUpPatientJob() {
        super();
    }

    public PickUpPatientJob(MapObjectIcon ambulance, MapObjectIcon patient, Config config) {
        this();
        setAction(ambulance, patient, config);
    }

    public void setAction(MapObjectIcon ambulance, MapObjectIcon patient, Config config) {
        Action action = () -> {
            double speed = config.getSpeed();

            DrivingTransition drive = new DrivingTransition(Duration.millis(speed * 2), ambulance, ambulance, patient);
            FadeOutTransition fadeOutPatient = new FadeOutTransition(Duration.millis(speed), patient);
            FadeOutTransition fadeOutAmbulance = new FadeOutTransition(Duration.millis(speed), ambulance);

            drive.setOnFinished(event -> {
                OffsetManager.offsetAmbulanceForTransition(ambulance);
                fadeOutPatient.play();
                fadeOutAmbulance.play();
            });
            fadeOutPatient.setOnFinished(event -> setFinished(true));

            drive.play();
        };

        setAction(action);
    }

    public void setDescription(Patient patient) {
        String desc = "Picking up patient id: " + patient.getId();

        setDescription(desc);
    }
}
