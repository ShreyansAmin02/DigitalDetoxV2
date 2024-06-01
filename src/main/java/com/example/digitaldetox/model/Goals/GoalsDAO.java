package com.example.digitaldetox.model.Goals;

import com.example.digitaldetox.model.DatabaseConnection;
import com.example.digitaldetox.model.UserSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GoalsDAO {

    private Connection connection;
    private int userID = UserSession.getInstance().getLoggedInUser().getaccountId();
    public GoalsDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }



    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS goals ("
                    + "accountID INTEGER NOT NULL,"
                    + "goalID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "IsComplete INTEGER NOT NULL,"
                    + "goalDescription VARCHAR NOT NULL,"
                    + "FOREIGN KEY (accountID) REFERENCES users(accountId)"
                    + ")";
            createTable.execute(query);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void addGoal(Task task) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO goals (accountID, IsComplete, goalDescription) VALUES (?, ?, ?)"
            );
            statement.setInt(1, userID);
            statement.setInt(2, 0);
            statement.setString(3, task.getName());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                task.setTaskID(generatedKeys.getInt(1));
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void deleteGoal(Task task) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM goals WHERE goalID = ?"
            );
            statement.setInt(1, task.getTaskID());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void setCompleted (Task task) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE goals " +
                    "SET IsComplete = ? " +
                    "WHERE goalID = ?");
            statement.setInt(1, task.isCompleted() ? 1 : 0);
            statement.setInt(2, task.getTaskID());
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }


    public void dropTable() {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DROP TABLE goals"
            );
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public List<Task> getGoals() {
        List<Task> goals = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM goals WHERE accountID = ?"
            );
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int goalID = resultSet.getInt("goalID");
                String goalDescription = resultSet.getString("goalDescription");
                int isCompleted = resultSet.getInt("IsComplete");
                Task goal = new Task(goalDescription, goalID, isCompleted);
                goals.add(goal);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return goals;
    }




}
