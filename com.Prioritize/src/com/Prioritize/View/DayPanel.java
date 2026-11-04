package com.Prioritize.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import com.Prioritize.Model.Task;
import com.Prioritize.Model.WorkSession;

/** One reusable day column: heading, compact due-task area, and a 30-minute schedule grid. */
public class DayPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final LocalTime FIRST_SLOT = LocalTime.of(7, 30);
    private static final LocalTime LAST_SLOT = LocalTime.of(23, 0);
    private static final int SLOT_MINUTES = 30;
    private static final int SLOT_COUNT = 32;
    private static final DateTimeFormatter DAY_FORMAT = DateTimeFormatter.ofPattern("EEEE");
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MMM d");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a");

    private final JLabel dayNameLabel = new JLabel("Monday", SwingConstants.CENTER);
    private final JLabel dateLabel = new JLabel("", SwingConstants.CENTER);
    private final JPanel dueTasksPanel = new JPanel();
    private final JPanel schedulePanel = new JPanel(new GridBagLayout());
    private LocalDate date;

    public DayPanel() 
    {
        super(new BorderLayout(0, 6));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(new Color(210, 215, 221)));
        buildContent();
        buildScheduleGrid();
    }

    private void buildContent() 
    {
        JPanel header = new JPanel(new BorderLayout(0, 2));
        header.setBackground(new Color(238, 242, 247));
        header.setBorder(BorderFactory.createEmptyBorder(8, 3, 7, 3));
        dayNameLabel.setFont(dayNameLabel.getFont().deriveFont(Font.BOLD, 14f));
        dateLabel.setFont(dateLabel.getFont().deriveFont(Font.PLAIN, 13f));
        header.add(dayNameLabel, BorderLayout.NORTH);
        header.add(dateLabel, BorderLayout.SOUTH);
        add(header, BorderLayout.NORTH);

        dueTasksPanel.setLayout(new javax.swing.BoxLayout(dueTasksPanel, javax.swing.BoxLayout.Y_AXIS));
        dueTasksPanel.setBackground(Color.WHITE);
        dueTasksPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Due tasks"), BorderFactory.createEmptyBorder(2, 4, 4, 4)));

        schedulePanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(schedulePanel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Schedule"));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getAccessibleContext().setAccessibleName("Schedule time slots");

        // Keep due tasks compact while giving the scrollable schedule all remaining height.
        JPanel content = new JPanel(new BorderLayout(0, 6));
        content.setOpaque(false);
        content.add(dueTasksPanel, BorderLayout.NORTH);
        content.add(scrollPane, BorderLayout.CENTER);
        add(content, BorderLayout.CENTER);
    }

    public void setDate(LocalDate newDate) 
    {
        date = newDate;
        dayNameLabel.setText(DAY_FORMAT.format(newDate));
        dateLabel.setText(DATE_FORMAT.format(newDate));
        getAccessibleContext().setAccessibleName(DAY_FORMAT.format(newDate) + " " + DATE_FORMAT.format(newDate));
    }

    public void setDueTasks(Collection<Task> tasks) {
        dueTasksPanel.removeAll();
        if (tasks == null || tasks.isEmpty()) 
        {
            JLabel empty = new JLabel("No due tasks");
            empty.setFont(empty.getFont().deriveFont(Font.ITALIC, 12f));
            empty.setForeground(new Color(80, 85, 90));
            dueTasksPanel.add(empty);
        } else 
        {
            for (Task task : tasks) 
            {
                dueTasksPanel.add(createDueTaskLabel(task));
            }
        }
        dueTasksPanel.revalidate();
        dueTasksPanel.repaint();
    }

    /** Rebuilds the layout with work-session blocks that can span multiple time rows. */
    public void setWorkSessions(Collection<WorkSession> sessions) {
        buildScheduleGrid();
        if (sessions != null) 
        {
            for (WorkSession session : sessions) 
            {
                if (session != null && session.getStartTime() != null && session.getTask() != null) {
                    addWorkSessionBlock(session);
                }
            }
        }
        schedulePanel.revalidate();
        schedulePanel.repaint();
    }

    private JLabel createDueTaskLabel(Task task) 
    {
        String urgency = UrgencyColor.labelForScore(task.getPriority());
        JLabel label = new JLabel(urgency + "  " + task.getTitle());
        label.setOpaque(true);
        label.setBackground(UrgencyColor.forScore(task.getPriority()));
        label.setForeground(Color.BLACK);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 12f));
        label.setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));
        label.setToolTipText("" + urgency + " urgency. Priority " + task.getPriority() + ", difficulty " + task.getDifficulty() + ", estimated hours " + task.getEstimatedHours());
        return label;
    }

    private void buildScheduleGrid() 
    {
        schedulePanel.removeAll();

        for (int row = 0; row < SLOT_COUNT; row++) 
        {

            LocalTime time = FIRST_SLOT.plusMinutes((long) row * SLOT_MINUTES);

            // Only display labels on the hour
            if (time.getMinute() == 0) 
            {
                JLabel timeLabel = new JLabel(TIME_FORMAT.format(time));
                timeLabel.setFont(timeLabel.getFont().deriveFont(11f));
                timeLabel.setForeground(new Color(65, 70, 75));
                timeLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(225, 228, 232)));

                GridBagConstraints constraints = baseConstraints(row);
                constraints.weightx = 1.0;
                constraints.weighty = 1.0;
                constraints.fill = GridBagConstraints.BOTH;

                schedulePanel.add(timeLabel, constraints);
            }
        }
    }

    private void addWorkSessionBlock(WorkSession session) 
    {
        int row = (int) java.time.Duration.between(FIRST_SLOT, session.getStartTime()).toMinutes() / SLOT_MINUTES;
        if (row < 0 || row >= SLOT_COUNT) 
            return;
        
        int rowSpan = Math.max(1, (int) Math.ceil(session.getDurationHours() * 60 / SLOT_MINUTES));
        rowSpan = Math.min(rowSpan, SLOT_COUNT - row);
        String title = session.getTask().getTitle();
        JLabel block = new JLabel("<html><b>" + title + "</b><br>" + TIME_FORMAT.format(session.getStartTime()) + " - " + TIME_FORMAT.format(session.getEndTime()) + "</html>");
        block.setOpaque(true);
        block.setBackground(UrgencyColor.forScore(session.getTask().getPriority()));
        block.setForeground(Color.BLACK);
        block.setVerticalAlignment(SwingConstants.TOP);
        block.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(75, 75, 75)), BorderFactory.createEmptyBorder(3, 4, 3, 4)));
        block.setToolTipText(title + ": " + TIME_FORMAT.format(session.getStartTime()) + " to " + TIME_FORMAT.format(session.getEndTime()));
        GridBagConstraints constraints = baseConstraints(row);
        constraints.gridheight = rowSpan;
        constraints.insets = new Insets(1, 1, 1, 1);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        schedulePanel.add(block, constraints);
    }

    private GridBagConstraints baseConstraints(int row) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        return constraints;
    }

    public LocalDate getDate() { return date; }
}
