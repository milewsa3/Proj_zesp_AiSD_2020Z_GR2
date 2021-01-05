package lab.aisd.log;

public class Log {
    private static int numOfLogs = 0;
    private String message;
    private int id;

    public Log(String message) {
        this.message = message;
        id = ++numOfLogs;
    }

    @Override
    public String toString() {
        return id + ". " + message;
    }
}
