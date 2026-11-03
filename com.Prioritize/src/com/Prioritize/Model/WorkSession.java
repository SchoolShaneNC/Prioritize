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

    public WorkSession(
            Task task,
            LocalDate date,
            LocalTime startTime,
            double durationHours) {

        this.id = UUID.randomUUID();

        this.task = task;
        this.date = date;
        this.startTime = startTime;
        this.durationHours = durationHours;
    }

    // getters/setters
    
    
    
    public LocalTime getEndTime() 
    {
        return startTime.plusMinutes((long)(durationHours * 60));
    }
    
   /* public boolean overlaps(WorkSession other) 
    {
        return startTime.isBefore(other.getEndTime()) && getEndTime().isAfter(other.getStartTime());
    } */
}
