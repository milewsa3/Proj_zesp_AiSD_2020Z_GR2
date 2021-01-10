package lab.aisd.log;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Logger {
    private final ObservableList<Log> logs = FXCollections.observableArrayList();
    private static Logger instance;

    private Logger () {
    }

    public void add(String message) {
        logs.add(new Log(message));
    }

    public void add(Log log) {
        logs.add(log);
    }

    public void add(String ... messages) {
        for (String message : messages) {
            add(message);
        }
    }

    public void add(Log ... logs) {
        for (Log log : logs) {
            add(log);
        }
    }

    public void add(List<String> messages) {
        for (String message : messages) {
            add(message);
        }
    }

    public ObservableList<Log> getListOfLogs() {
        Log.setNumOfLogs(0);
        return logs;
    }

    public static Logger getInstance() {
        if (instance == null)
            instance = new Logger();

        return instance;
    }
}
