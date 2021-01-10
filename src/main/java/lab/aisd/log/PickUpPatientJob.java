package lab.aisd.log;

import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lab.aisd.animation.DrivingTransition;
import lab.aisd.animation.FadeInTransition;
import lab.aisd.animation.FadeOutTransition;
import lab.aisd.gui.collection.Config;
import lab.aisd.gui.model.HospitalIcon;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.gui.util.OffsetManager;
import lab.aisd.model.Hospital;
import lab.aisd.model.Patient;

public class PickUpPatientJob extends Job{

    public PickUpPatientJob() {
        super();
    }

    public PickUpPatientJob(MapObjectIcon ambulance, MapObjectIcon patient) {
        this();
        setAction(ambulance, patient);
    }

    public void setAction(MapObjectIcon ambulance, MapObjectIcon patient) {
        Action action = () -> {
            double speed = Config.getInstance().getSpeed();

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
