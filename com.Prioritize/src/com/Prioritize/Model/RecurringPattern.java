package com.Prioritize.Model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecurringPattern {

    private List<DayOfWeek> recurrenceDays;

    private LocalTime startTime;

    private double durationHours;

    public RecurringPattern() { this.recurrenceDays = new ArrayList<>(); }

    public RecurringPattern(List<DayOfWeek> recurrenceDays, LocalTime startTime, double durationHours) 
    {
        setRecurrenceDays(recurrenceDays);
        setStartTime(startTime);
        setDurationHours(durationHours);
    }

    // Getters

    public List<DayOfWeek> getRecurrenceDays() { return Collections.unmodifiableList(recurrenceDays); }

    public LocalTime getStartTime() { return startTime; }

    public double getDurationHours() { return durationHours; }

    // Setters

    public void setRecurrenceDays(List<DayOfWeek> newRecurrenceDays) 
    {

        if (newRecurrenceDays == null || newRecurrenceDays.isEmpty())
            throw new IllegalArgumentException("Recurrence days cannot be empty");

        if (newRecurrenceDays.contains(null)) 
            throw new IllegalArgumentException("Recurrence days cannot contain null values");

        // Prevent duplicate days
        if (newRecurrenceDays.stream().distinct().count() != newRecurrenceDays.size()) 
            throw new IllegalArgumentException("Recurrence days cannot contain duplicates");

        recurrenceDays = new ArrayList<>(newRecurrenceDays);
    }

    public void setStartTime(LocalTime newStartTime) 
    {
        // Null is allowed because it represents a flexible schedule
        startTime = newStartTime;
    }

    public void setDurationHours(double newDurationHours) 
    {

        if (newDurationHours <= 0 || Double.isInfinite(newDurationHours) || Double.isNaN(newDurationHours)) 
            throw new IllegalArgumentException("Duration must be a valid number greater than zero");

        durationHours = newDurationHours;
    }

    // Other methods

    public boolean isFlexible() 
    { 
    	return startTime == null;
    }
}