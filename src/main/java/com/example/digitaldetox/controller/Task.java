package com.example.digitaldetox.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Task {
    private final StringProperty name = new SimpleStringProperty();
    private final BooleanProperty completed = new SimpleBooleanProperty(false);

    public Task(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public BooleanProperty completedProperty() {
        return completed;
    }

    public boolean isCompleted() {
        return completed.get();
    }

    @Override
    public String toString() {
        return name.get();
    }
}