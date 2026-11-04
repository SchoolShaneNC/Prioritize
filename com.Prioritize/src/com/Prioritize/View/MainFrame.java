package com.Prioritize.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

/**
 * The top-level presentation shell for Priority Planner. Controllers can attach
 * actions to the exposed buttons without placing application behavior in this view.
 */
public class MainFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Color BACKGROUND = new Color(246, 248, 250);

    private final TaskListPanel taskListPanel;
    private final WeekCalendarPanel weekCalendarPanel;

    public MainFrame() {
        super("Prioritize");

        taskListPanel = new TaskListPanel();
        weekCalendarPanel = new WeekCalendarPanel();

        configureWindow();
        buildContent();
    }

    private void configureWindow() 
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMinimumSize(new Dimension(800, 600));
        setSize(1440, 900);
        setLocationByPlatform(true);

        taskListPanel.setMinimumSize(new Dimension(140, 0));
        weekCalendarPanel.setMinimumSize(new Dimension(500, 0));
    }

    private void buildContent() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BACKGROUND);

        JLabel title = new JLabel("Prioritize");
        title.setHorizontalAlignment(SwingConstants.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        title.setBorder(BorderFactory.createEmptyBorder(14, 18, 12, 18));
        title.getAccessibleContext().setAccessibleName("Prioritize");
        root.add(title, BorderLayout.NORTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, taskListPanel, weekCalendarPanel);
        splitPane.setResizeWeight(0.20);
        splitPane.setDividerLocation(285);
        splitPane.setContinuousLayout(true);
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        root.add(splitPane, BorderLayout.CENTER);

        setContentPane(root);
    }

    public TaskListPanel getTaskListPanel() 
    {
        return taskListPanel;
    }

    public WeekCalendarPanel getWeekCalendarPanel() 
    {
        return weekCalendarPanel;
    }

    /** Convenience hook for a future controller. */
    public JButton getAddTaskButton() {
        return taskListPanel.getAddTaskButton();
    }

    /** Convenience hook for a future controller. */
    public JButton getEditTaskButton() {
        return taskListPanel.getEditTaskButton();
    }

    /** Convenience hook for a future controller. */
    public JButton getDeleteTaskButton() {
        return taskListPanel.getDeleteTaskButton();
    }

    /** Convenience hook for a future controller. */
    public JButton getCreateWorkSessionButton() {
        return weekCalendarPanel.getCreateWorkSessionButton();
    }
}
