package com.example.digitaldetox.model.Goals;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private final StringProperty name;
    private final BooleanProperty completed;
    private int taskID;

    public Task(String name) {
        this.name = new SimpleStringProperty(name);
        this.completed = new SimpleBooleanProperty(false);
    }

    public Task(String name, int taskID) {
        this.name = new SimpleStringProperty(name);
        this.completed = new SimpleBooleanProperty(false);
        this.taskID = taskID;
    }

    public Task(String name, int taskID, int completed) {
        this.name = new SimpleStringProperty(name);
        if (completed == 1) {
            this.completed = new SimpleBooleanProperty(true);
        }
        else {
            this.completed = new SimpleBooleanProperty(false);
        }

        this.taskID = taskID;
    }

    public String getName() {
        return name.get();  }

    public void setName(String name) {
        this.name.set(name);
    }


    public boolean isCompleted() {
        return completed.get();
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }
    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getTaskID() {
        return taskID;
    }

}
