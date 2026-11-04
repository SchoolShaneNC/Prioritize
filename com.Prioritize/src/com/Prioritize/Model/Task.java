package com.Prioritize.Model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Task {

    private UUID id;

    private String title;

    private String description;

    private double priority;

    private double difficulty;

    private double estimatedHours;

    private LocalDate dueDate;

    private boolean completed;

    private TaskType taskType;

    private RecurringPattern recurringPattern;

    public Task() 
    {
        this.id = UUID.randomUUID();
    }

    public Task(String title, String description, double priority, double difficulty, double estimatedHours, LocalDate dueDate, TaskType taskType) 
    {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.difficulty = difficulty;
        this.estimatedHours = estimatedHours;
        this.dueDate = dueDate;
        this.taskType = taskType;
    }

  //getters/setters
 
 //getters
    public UUID getId() { return id; }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

    public double getPriority() { return priority; }

    public double getDifficulty() { return difficulty; }

    public double getEstimatedHours() { return estimatedHours; }

    public LocalDate getDueDate() { return dueDate; }

    public boolean isCompleted() { return completed; }

    public TaskType getTaskType() { return taskType; }

    public RecurringPattern getRecurringPattern() { return recurringPattern; }


    //setters
    public void setTitle(String newTitle)
    {
        if (newTitle == null || newTitle.isBlank()) 
            throw new IllegalArgumentException("Title cannot be blank");

        title = newTitle;
    }

    public void setDescription(String newDescription)
    {
        if (newDescription == null || newDescription.isBlank()) 
            throw new IllegalArgumentException("Description cannot be blank");

        description = newDescription;
    }

    public void setPriority(int newPriority)
    {
        if (newPriority < 1 || newPriority > 10) 
            throw new IllegalArgumentException("Priority must be between 1-10");

        priority = newPriority;
    }

    public void setDifficulty(int newDifficulty)
    {
        if (newDifficulty < 1 || newDifficulty > 10) 
            throw new IllegalArgumentException("Difficulty must be at least 1");

        difficulty = newDifficulty;
    }

    public void setEstimatedHours(double newEstimatedHours)
    {
        if (newEstimatedHours < 0) {
            throw new IllegalArgumentException("Estimated hours cannot be negative");
        }

        estimatedHours = newEstimatedHours;
    }

    public void setDueDate(LocalDate newDueDate)
    {  
    	if (newDueDate == null)
        throw new IllegalArgumentException("Date cannot be null");
    	
    	//keep if we outlaw adding old behind tasks, could be idea adding something you forgot about but mainly would only work for school projects
    	if (newDueDate.isBefore(LocalDate.now()))
    		throw new IllegalArgumentException("Due date cannot be in the past");
    	
        dueDate = newDueDate;
    }

    public void setCompleted(boolean newCompleted)
    {
        completed = newCompleted;
    }

    public void setTaskType(TaskType newTaskType)
    {
        taskType = newTaskType;
    }

    public void setRecurringPattern(RecurringPattern newRecurringPattern)
    {
        recurringPattern = newRecurringPattern;
    }
    
    
//Helper methods
    
    public boolean isRecurring() 
    {
        return recurringPattern != null;
    }
    
    public double getWorkloadScore() 
    {
        double daysUntilDue = (double) ChronoUnit.DAYS.between(LocalDate.now(), dueDate);

        //maybe add extra score since it would be the same as dividing by 1, wait til testing to dial equation in 
        if (daysUntilDue <= 0) 
            return priority * difficulty;

        return (priority * difficulty) / daysUntilDue;
    }
    
    @Override
    public String toString() 
    {
        String completedText = completed ? "✓" : "○";
        return completedText + " " + title + " (P:" + priority + ", D:" + difficulty + ")";
    }
    
    
}
