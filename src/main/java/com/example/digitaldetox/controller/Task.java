package com.example.digitaldetox.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private final StringProperty name;
    private final BooleanProperty completed;

    public Task(String name) {
        this.name = new SimpleStringProperty(name);
        this.completed = new SimpleBooleanProperty(false);
    }

    public String getName() {
        return name.get();  }

    public void setName(String name) {
        this.name.set(name);
    }

   public StringProperty nameProperty() {     return name; }

    public boolean isCompleted() {
        return completed.get();
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }
}
