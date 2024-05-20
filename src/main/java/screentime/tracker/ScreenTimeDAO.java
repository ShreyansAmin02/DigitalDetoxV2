package screentime.tracker;

import com.example.digitaldetox.model.DatabaseConnection;
import com.example.digitaldetox.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScreenTimeDAO implements IScreenTimeDAO {
    private Connection connection;
    public ScreenTimeDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
        screentime_tracker screentimeTracker = new screentime_tracker();
        app_tracker appTracker = new app_tracker();
        screentimeTracker.startTracker();
        appTracker.startTracker();
    }



    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS screentime ("
                    + "accountID INTEGER NOT NULL,"
                    + "appName VARCHAR NOT NULL,"
                    + "screentime_duration INTEGER NOT NULL,"
                    + "overall_screentime INTEGER NOT NULL,"
                    + "FOREIGN KEY (accountID) REFERENCES users(accountId)"
                    + ")";
            createTable.execute(query);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void addApp(App app) {
        try {
            String query = "INSERT INTO screentime (accountID, appName, screentime_duration) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, app.getAccountID());
            statement.setString(2, app.getName());
            statement.setInt(3, app.getTime());
            statement.executeUpdate();
        } catch(SQLException ex) {
            System.err.println(ex);
        }
    }

    public App getOverallScreentime(int accountID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT overall_screentime" +
                    "FROM screentime" +
                    "WHERE accountId = ?");
            statement.setInt(1, accountID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("appName");
                int ID = resultSet.getInt("accountId");
                int time  = resultSet.getInt("screentime_duration");
                App app = new App(ID, name, time);
                return app;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }


    public App getAppScreentime(String appName, int accountID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT screentime_duration" +
                    "FROM screentime" +
                    "WHERE accountId = ? AND appName = ?");
            statement.setInt(1, accountID);
            statement.setString(2, appName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("appName");
                int ID = resultSet.getInt("accountId");
                int time  = resultSet.getInt("screentime_duration");
                App app = new App(ID, name, time);
                return app;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }


    public App addScreentimeToApp(String appName, int time, int accountID) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE screentime" +
                    "SET screentime_duration = screen_duration + ?" +
                    "WHERE accountID = ? AND appName = ?");
            statement.setInt(1, time);
            statement.setInt(2, accountID);
            statement.setString(3, appName);
            statement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }


}
