package lab.aisd.log;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import static com.sun.javafx.animation.TickCalculation.fromDuration;

public class Job {
    private Action action;
    private String description;

    private BooleanProperty finished = new SimpleBooleanProperty(false);

    public Job() {
        action = () -> System.out.println("Not implemented");
        description = "Not described";
    }

    public Job(Action action) {
        this.action = action;
    }

    public Job(Action action, String description) {
        this.action = action;
        this.description = description;
    }

    public void commit() {
        action.commit();
    }

    public void setOnFinished(EventHandler<ActionEvent> handler) {
        finished.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && handler != null) {
                    handler.handle(new ActionEvent(this, null));
                }
            }
        });
    }

    public void setOnFinished(Job job) {
        finished.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue)
                    job.commit();
            }
        });
    }

    public void setFinished(boolean finished) {
        this.finished.set(finished);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

