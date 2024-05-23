package com.example.digitaldetox.controller;

import com.example.digitaldetox.controller.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class GoalListController {
    @FXML
    private TextField taskInput;
    @FXML
    private ListView<Task> taskListView;

    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        taskListView.setItems(tasks);
        taskListView.setCellFactory(CheckBoxListCell.forListView(Task::completedProperty, new StringConverter<Task>() {
            @Override
            public String toString (Task task){
                return task.getName();
            }
            @Override
            public Task fromString (String string){
                return null;
            }
        }));
    }

    @FXML
    private void handleAddTask() {
        String taskText = taskInput.getText();
        if (!taskText.isEmpty()) {
            Task newTask = new Task(taskText);
            tasks.add(newTask);
            taskInput.clear();
        }
    }

    @FXML
    private void handleClearCompleted() {
        tasks.removeIf(Task::isCompleted);
    }
}
