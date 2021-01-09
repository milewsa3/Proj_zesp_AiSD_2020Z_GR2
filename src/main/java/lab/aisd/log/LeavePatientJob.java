package lab.aisd.log;

import javafx.util.Duration;
import lab.aisd.animation.FadeInTransition;
import lab.aisd.animation.FadeOutTransition;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.model.Patient;

public class LeavePatientJob extends Job {

    public LeavePatientJob() {
        super();
    }

    public LeavePatientJob(MapObjectIcon ambulance) {
        this();
        setAction(ambulance);
    }

    public void setAction(MapObjectIcon ambulance) {
        Action action = () -> {
            double speed = Job.getSpeed();

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
                " because of the lack of empty beds";

        setDescription(desc);
    }
}
