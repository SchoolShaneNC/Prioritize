package com.Prioritize.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class WorkSession {

    private UUID id;

    private Task task;

    private LocalDate date;

    private LocalTime startTime;

    private double durationHours;

    public WorkSession() {
        this.id = UUID.randomUUID();
    }

    public WorkSession(Task task, LocalDate date, LocalTime startTime, double durationHours)
    {
        this.id = UUID.randomUUID();
        setTask(task);
        setDate(date);
        setStartTime(startTime);
        setDurationHours(durationHours);
    }

    //getters

    public UUID getId() { return id; }

    public Task getTask() { return task; }

    public LocalDate getDate() { return date; }

    public LocalTime getStartTime() { return startTime; }

    public double getDurationHours() { return durationHours; }

    //setters

    public void setTask(Task newTask)
    {
        if (newTask == null)
            throw new IllegalArgumentException("Task cannot be null");

        task = newTask;
    }

    public void setDate(LocalDate newDate)
    {
        if (newDate == null)
            throw new IllegalArgumentException("Date cannot be null");
        
    	if (newDate.isBefore(LocalDate.now()))
    		throw new IllegalArgumentException("Due date cannot be in the past");

        date = newDate;
    }

    public void setStartTime(LocalTime newStartTime)
    {
        if (newStartTime == null)
            throw new IllegalArgumentException("Start time cannot be null");

        startTime = newStartTime;
    }

    public void setDurationHours(double newDurationHours)
    {
        if (newDurationHours <= 0.5)
            throw new IllegalArgumentException("Duration must be greater than zero");

        durationHours = newDurationHours;
    }

    //helper methods

    public LocalTime getEndTime()
    {
        return startTime.plusMinutes((long)(durationHours * 60));
    }

    public boolean overlaps(WorkSession other)
    {
        if (other == null)
            throw new IllegalArgumentException("Work session cannot be null");

        // Sessions on different dates cannot overlap
        if (!date.equals(other.getDate()))
            return false;

        return startTime.isBefore(other.getEndTime()) && getEndTime().isAfter(other.getStartTime());
    }
}