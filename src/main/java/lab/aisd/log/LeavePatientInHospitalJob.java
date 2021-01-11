package lab.aisd.log;

import javafx.util.Duration;
import lab.aisd.animation.FadeInTransition;
import lab.aisd.animation.FadeOutTransition;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.gui.util.Config;
import lab.aisd.model.Patient;

public class LeavePatientInHospitalJob extends Job {

    public LeavePatientInHospitalJob() {
        super();
    }

    public LeavePatientInHospitalJob(MapObjectIcon ambulance, Config config) {
        this();
        setAction(ambulance, config);
    }

    public void setAction(MapObjectIcon ambulance, Config config) {
        Action action = () -> {
            double speed = config.getSpeed();

            FadeInTransition fadeIn1 = new FadeInTransition(Duration.millis(speed), ambulance);
            FadeOutTransition fadeOut1 = new FadeOutTransition(Duration.millis(speed), ambulance);
            FadeInTransition fadeIn2 = new FadeInTransition(Duration.millis(speed), ambulance);
            FadeOutTransition fadeOut2 = new FadeOutTransition(Duration.millis(speed), ambulance);

            fadeIn1.setOnFinished(event -> fadeOut1.play());
            fadeOut1.setOnFinished(event -> fadeIn2.play());
            fadeIn2.setOnFinished(event -> fadeOut2.play());
            fadeOut2.setOnFinished(event -> setFinished(true));

            fadeIn1.play();
        };

        setAction(action);
    }

    public void setDescription(Patient patient) {
        String desc = "Leaving patient id: " + patient.getId() +
                " in that hospital";

        setDescription(desc);
    }
}
