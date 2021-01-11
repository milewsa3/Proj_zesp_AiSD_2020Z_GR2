package lab.aisd.log;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private List<Log> logs;
    private int numberOfLogs = 0;

    public Logger () {
        logs = new ArrayList<>();
    }

    public void add(String message) {
        logs.add(new Log(message, ++numberOfLogs));
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

    public List<Log> getListOfLogs() {
        return logs;
    }
}
