package com.Prioritize.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.Prioritize.Model.Task;
import com.Prioritize.Model.WorkSession;

/**
 * The week-view presentation component. It exposes navigation controls for a
 * controller and redraws supplied model data without modifying that data.
 */
public class WeekCalendarPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter RANGE_FORMAT = DateTimeFormatter.ofPattern("MMM d");

    private final JButton previousWeekButton = new JButton("< Previous Week");
    private final JButton nextWeekButton = new JButton("Next Week >");
    private final JButton createWorkSessionButton = new JButton("Create Work Session");
    private final JLabel rangeLabel = new JLabel();
    private final DayPanel[] dayPanels = new DayPanel[7];
    private LocalDate weekStart;

    public WeekCalendarPanel() {
        super(new BorderLayout(0, 10));
        weekStart = LocalDate.now().with(DayOfWeek.MONDAY);
        setBackground(new Color(246, 248, 250));
        setBorder(BorderFactory.createEmptyBorder(2, 4, 0, 0));
        buildContent();
        setWeekStart(weekStart);
    }

    private void buildContent() {
        JPanel navigation = new JPanel(new BorderLayout(10, 0));
        navigation.setOpaque(false);
        configureButton(previousWeekButton, "Show the previous week.");
        configureButton(nextWeekButton, "Show the next week.");
        navigation.add(previousWeekButton, BorderLayout.WEST);
        rangeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rangeLabel.setFont(rangeLabel.getFont().deriveFont(Font.BOLD, 18f));
        rangeLabel.getAccessibleContext().setAccessibleName("Displayed week date range");
        navigation.add(rangeLabel, BorderLayout.CENTER);
        navigation.add(nextWeekButton, BorderLayout.EAST);
        add(navigation, BorderLayout.NORTH);

        JPanel days = new JPanel(new GridLayout(1, 7, 6, 0));
        days.setOpaque(false);
        for (int i = 0; i < dayPanels.length; i++) {
            dayPanels[i] = new DayPanel();
            days.add(dayPanels[i]);
        }
        add(days, BorderLayout.CENTER);

        JPanel actions = new JPanel(new BorderLayout());
        actions.setOpaque(false);
        configureButton(createWorkSessionButton, "Create a work session for a task and time.");
        actions.add(createWorkSessionButton, BorderLayout.EAST);
        add(actions, BorderLayout.SOUTH);
    }

    private void configureButton(JButton button, String tooltip) {
        button.setFont(button.getFont().deriveFont(Font.PLAIN, 14f));
        button.setToolTipText(tooltip);
    }

    public void setWeekStart(LocalDate monday) {
        if (monday == null) {
            throw new IllegalArgumentException("Week start cannot be null.");
        }
        weekStart = monday.with(DayOfWeek.MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);
        rangeLabel.setText(RANGE_FORMAT.format(weekStart) + " - " + RANGE_FORMAT.format(weekEnd));
        for (int i = 0; i < dayPanels.length; i++) {
            dayPanels[i].setDate(weekStart.plusDays(i));
        }
    }

    /** Displays due tasks grouped and sorted for the currently shown week. */
    public void setTasks(Collection<Task> tasks) {
        for (int i = 0; i < dayPanels.length; i++) {
            LocalDate date = weekStart.plusDays(i);
            List<Task> dueToday = new ArrayList<>();
            if (tasks != null) {
                for (Task task : tasks) {
                    if (task != null && date.equals(task.getDueDate())) {
                        dueToday.add(task);
                    }
                }
            }
            dueToday.sort(Comparator.comparingDouble(Task::getPriority).reversed());
            dayPanels[i].setDueTasks(dueToday);
        }
    }

    /** Supplies visual work-session blocks; scheduling decisions remain outside this view. */
    public void setWorkSessions(Collection<WorkSession> sessions) {
        for (int i = 0; i < dayPanels.length; i++) {
            LocalDate date = weekStart.plusDays(i);
            List<WorkSession> sessionsToday = new ArrayList<>();
            if (sessions != null) {
                for (WorkSession session : sessions) {
                    if (session != null && date.equals(session.getDate())) {
                        sessionsToday.add(session);
                    }
                }
            }
            dayPanels[i].setWorkSessions(sessionsToday);
        }
    }

    public LocalDate getWeekStart() { return weekStart; }
    public DayPanel getDayPanel(int dayIndex) { return dayPanels[dayIndex]; }
    public JButton getPreviousWeekButton() { return previousWeekButton; }
    public JButton getNextWeekButton() { return nextWeekButton; }
    public JButton getCreateWorkSessionButton() { return createWorkSessionButton; }
}
