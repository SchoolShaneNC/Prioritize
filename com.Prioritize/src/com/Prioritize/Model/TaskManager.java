package com.Prioritize.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskManager {

    private List<Task> tasks;
    private List<WorkSession> workSessions;

    public TaskManager()
    {
        tasks = new ArrayList<>();
        workSessions = new ArrayList<>();
    }

    public void addTask(Task task)
    {
        if (task == null)
            throw new IllegalArgumentException("Task cannot be null");

        if (tasks.contains(task))
            throw new IllegalArgumentException("Task already exists");

        tasks.add(task);
    }

    public void removeTask(Task task)
    {
        if (task == null)
            throw new IllegalArgumentException("Task cannot be null");

        if (!tasks.contains(task))
            throw new IllegalArgumentException("Task does not exist");

        tasks.remove(task);
    }

    public void addWorkSession(WorkSession session)
    {
        if (session == null)
            throw new IllegalArgumentException("Work session cannot be null");

        if (workSessions.contains(session))
            throw new IllegalArgumentException("Work session already exists");

        workSessions.add(session);
    }

    public void removeWorkSession(WorkSession session)
    {
        if (session == null)
            throw new IllegalArgumentException("Work session cannot be null");

        if (!workSessions.contains(session))
            throw new IllegalArgumentException("Work session does not exist");

        workSessions.remove(session);
    }

    public List<Task> getTasks()
    {
        return Collections.unmodifiableList(tasks);
    }

    public List<WorkSession> getWorkSessions()
    {
        return Collections.unmodifiableList(workSessions);
    }
}