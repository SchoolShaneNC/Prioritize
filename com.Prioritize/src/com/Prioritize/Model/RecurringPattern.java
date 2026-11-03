package com.Prioritize.Model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class RecurringPattern {

    private List<DayOfWeek> recurrenceDays;

    private LocalTime startTime;

    private double durationHours;

    public RecurringPattern() {
    }

    public RecurringPattern(
            List<DayOfWeek> recurrenceDays,
            LocalTime startTime,
            double durationHours) {

        this.recurrenceDays = recurrenceDays;
        this.startTime = startTime;
        this.durationHours = durationHours;
    }

    public List<DayOfWeek> getRecurrenceDays() {
        return recurrenceDays;
    }

    public void setRecurrenceDays(List<DayOfWeek> recurrenceDays) {
        this.recurrenceDays = recurrenceDays;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public double getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(double durationHours) {
        this.durationHours = durationHours;
    }

    public boolean isFlexible() {
        return startTime == null;
    }
}