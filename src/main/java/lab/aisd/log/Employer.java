package lab.aisd.log;

import java.util.*;

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

    public void add(Job ... jobs) {
        for (Job j : jobs) {
            add(j);
        }
    }

    public void startJobs() {
        Job firstJob = jobs.getFirst();
        firstJob.commit();
    }

    public List<String> getAllLogs() {
        List<String> logs = new ArrayList<>();

        for (Job job : jobs) {
            logs.add(job.getDescription());
        }

        return logs;
    }
}