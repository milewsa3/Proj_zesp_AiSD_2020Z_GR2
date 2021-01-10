package lab.aisd.log;

import javafx.animation.RotateTransition;
import javafx.util.Duration;
import lab.aisd.animation.FadeInTransition;
import lab.aisd.animation.FadeOutTransition;
import lab.aisd.animation.Rotate360Transition;
import lab.aisd.gui.collection.Config;
import lab.aisd.gui.model.MapObjectIcon;
import lab.aisd.model.Patient;

public class LeavePatientOutsideJob extends Job {
    public LeavePatientOutsideJob() {
        super();
    }

    public LeavePatientOutsideJob(MapObjectIcon ambulance) {
        this();
        setAction(ambulance);
    }

    public void setAction(MapObjectIcon ambulance) {
        Action action = () -> {
            double speed = Config.getInstance().getSpeed();

            FadeInTransition fadeIn = new FadeInTransition(Duration.millis(speed), ambulance);
            Rotate360Transition rotate = new Rotate360Transition(Duration.millis(speed), ambulance);
            FadeOutTransition fadeOut = new FadeOutTransition(Duration.millis(speed), ambulance);

            fadeIn.setOnFinished(event -> rotate.play());
            rotate.setOnFinished(event -> fadeOut.play());
            fadeOut.setOnFinished(event -> setFinished(true));

            fadeIn.play();
        };

        setAction(action);
    }

    public void setDescription(Patient patient) {
        String desc = "Leaving patient id: " + patient.getId() +
                " because of the lack of empty beds";

        setDescription(desc);
    }
}
