package lab.aisd.log;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Employer {
    private Deque<Job> jobs;

    public Employer() {
        jobs = new LinkedList<>();
    }

    public void add(Job job) {
        if (!jobs.isEmpty()) {
            jobs.getLast().setOnFinished(job);
        }

        jobs.addLast(job);
    }

    public void addAll(Job ... jobs) {
        for (Job j : jobs) {
            add(j);
        }
    }

    public void clear() {
        jobs.clear();
    }

    public void startJobs() {
        Job firstJob = jobs.getFirst();
        firstJob.commit();
    }

    public List<String> getAllLogs() {
        List<String> logs = new ArrayList<>();

        for (Job job : jobs) {
            String description = job.getDescription();

            if (description.compareTo("Not described") != 0) {
                logs.add(description);
            }
        }

        return logs;
    }
}
