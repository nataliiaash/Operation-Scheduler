package com.example.operationschedularproject;

public class Task {

    private String description;

    private TaskPriority priority;

    public Task(String description, TaskPriority priority) {
        this.description = description;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Task{" +
                "description='" + description + '\'' +
                ", priority=" + priority +
                '}';
    }
}
