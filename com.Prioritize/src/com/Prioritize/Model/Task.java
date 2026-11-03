package com.Prioritize.Model;

import java.time.LocalDate;
import java.util.UUID;

public class Task {

    private UUID id;

    private String title;

    private String description;

    private int priority;

    private int difficulty;

    private double estimatedHours;

    private LocalDate dueDate;

    private boolean completed;

    private TaskType taskType;

    private RecurringPattern recurringPattern;

    public Task() {
        this.id = UUID.randomUUID();
    }

    public Task(
            String title,
            String description,
            int priority,
            int difficulty,
            double estimatedHours,
            LocalDate dueDate,
            TaskType taskType) {

        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.difficulty = difficulty;
        this.estimatedHours = estimatedHours;
        this.dueDate = dueDate;
        this.taskType = taskType;
    }

    // getters/setters
    
    public boolean isRecurring() 
    {
        return recurringPattern != null;
    }
    
    public int getWorkloadScore() 
    {
        return priority * difficulty;
    }
    
    @Override
    public String toString() {

        String completedText = completed ? "✓" : "○";

        return completedText + " " + title + " (P:" + priority + ", D:" + difficulty + ")";
    }
    
    
}
