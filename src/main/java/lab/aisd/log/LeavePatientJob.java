package lab.aisd.log;

import javafx.util.Duration;
import lab.aisd.animation.DrivingTransition;
import lab.aisd.animation.FadeOutTransition;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.model.Patient;

public class LeavePatientJob extends Job {
    public LeavePatientJob() {
        super();
    }

    public LeavePatientJob(MapObjectIcon ambulance, MapObjectIcon patient) {
        this();

        setAction(ambulance);
    }

    public void setAction(MapObjectIcon ambulance) {
        Action action = () -> {
            FadeOutTransition fadeOut = new FadeOutTransition(Duration.millis(500), ambulance);
            fadeOut.setOnFinished(event -> setFinished(true));

            fadeOut.play();
        };

        setAction(action);
    }

    public void setDescription(Patient patient) {
        String desc = "Leaving patient id: " + patient.getId() +
                " because of the lack of empty beds";

        setDescription(desc);
    }
}
