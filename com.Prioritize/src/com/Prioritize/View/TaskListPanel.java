package com.Prioritize.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.Prioritize.Model.Task;

/** Presentation component for the selectable task list and its future controller actions. */
public class TaskListPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private final DefaultListModel<Task> taskModel = new DefaultListModel<>();
    private final JList<Task> taskList = new JList<>(taskModel);
    private final JButton addTaskButton = new JButton("Add");
    private final JButton editTaskButton = new JButton("Edit");
    private final JButton deleteTaskButton = new JButton("Delete");

    public TaskListPanel() 
    {
        super(new BorderLayout(0, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(210, 215, 221)),
                BorderFactory.createEmptyBorder(14, 12, 12, 12)));
        buildContent();
    }

    private void buildContent() 
    {
        javax.swing.JLabel heading = new javax.swing.JLabel("Tasks");
        heading.setFont(heading.getFont().deriveFont(Font.BOLD, 19f));
        heading.setLabelFor(taskList);
        add(heading, BorderLayout.NORTH);

        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        taskList.setFont(taskList.getFont().deriveFont(15f));
        taskList.setCellRenderer(new TaskTitleRenderer());
        taskList.setToolTipText("Select a task to edit or delete it.");
        taskList.getAccessibleContext().setAccessibleName("Tasks");
        add(new JScrollPane(taskList), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(1, 3, 8, 0));
        buttons.setOpaque(false);
        configureButton(addTaskButton, "Create a new task.");
        configureButton(editTaskButton, "Edit the selected task.");
        configureButton(deleteTaskButton, "Delete the selected task.");
        buttons.add(addTaskButton);
        buttons.add(editTaskButton);
        buttons.add(deleteTaskButton);
        add(buttons, BorderLayout.SOUTH);
    }

    private void configureButton(JButton button, String tooltip) 
    {
        button.setFont(button.getFont().deriveFont(Font.PLAIN, 14f));
        button.setToolTipText(tooltip);
    }

    // Replaces displayed content; mutation remains the responsibility of a controller/model. 
    public void setTasks(Collection<Task> tasks) 
    {
        taskModel.clear();
        if (tasks != null)
            tasks.forEach(taskModel::addElement);
        
    }

    public Task getSelectedTask() 
    {
        return taskList.getSelectedValue();
    }

    public JList<Task> getTaskList() { return taskList; }
    public JButton getAddTaskButton() { return addTaskButton; }
    public JButton getEditTaskButton() { return editTaskButton; }
    public JButton getDeleteTaskButton() { return deleteTaskButton; }

    private static class TaskTitleRenderer extends DefaultListCellRenderer 
    {
        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
        {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Task) 
            {
                setText(((Task) value).getTitle());
            }
            setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
            return this;
        }
    }
}
