package lab.aisd.log;

public class Job {
    private Action action;
    private String description;

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

