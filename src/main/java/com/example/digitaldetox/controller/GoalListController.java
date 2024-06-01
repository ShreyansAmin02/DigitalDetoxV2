package com.example.digitaldetox.controller;

import com.example.digitaldetox.model.Goals.Task;
import com.example.digitaldetox.model.Goals.GoalsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.util.StringConverter;
import java.util.List;

public class GoalListController {
    @FXML
    private TextField taskInput;
    @FXML
    private ListView<Task> taskListView;
    private GoalsDAO goalsDAO;
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();



    @FXML
    private void initialize() {
        taskListView.setItems(tasks);
        goalsDAO = new GoalsDAO();
        List<Task> userGoals = goalsDAO.getGoals();
        tasks.addAll(userGoals);
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

        for (Task task : tasks) {
            task.completedProperty().addListener((observable, previousState, currentState) -> {
                goalsDAO.setCompleted(task);
            });
        }

    }

    @FXML
    private void handleAddTask() {
        String taskText = taskInput.getText();
        if (!taskText.isEmpty()) {
            Task newTask = new Task(taskText);
            tasks.add(newTask);
            goalsDAO.addGoal(newTask);
            taskInput.clear();

            newTask.completedProperty().addListener((obs, previousState, currentState) -> {
                goalsDAO.setCompleted(newTask);
            });

        }
    }


    @FXML
    private void handleClearCompleted()  {
        tasks.removeIf(task -> {
            if (task.isCompleted()) {
                goalsDAO.deleteGoal(task);
                return true;
            }
            return false;
        });
    }
}
