package com.Prioritize.Model;

import java.util.ArrayList;
import java.util.List;

public class TaskManager {

    private List<Task> tasks;

    private List<WorkSession> workSessions;

    public TaskManager() {

        tasks = new ArrayList<>();
        workSessions = new ArrayList<>();
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public void addWorkSession(WorkSession session) {
        workSessions.add(session);
    }

    public void removeWorkSession(WorkSession session) {
        workSessions.remove(session);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<WorkSession> getWorkSessions() {
        return workSessions;
    }
}