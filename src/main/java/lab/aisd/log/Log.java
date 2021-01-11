package lab.aisd.log;

public class Log {
    private String message;
    private int id;

    public Log(String message, int id) {
        this.message = message;
        this.id = id;
    }

    @Override
    public String toString() {
        return id + ". " + message;
    }
}
