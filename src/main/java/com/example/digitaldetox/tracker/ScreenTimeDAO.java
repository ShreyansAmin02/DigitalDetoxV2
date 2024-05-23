package com.example.digitaldetox.tracker;

import com.example.digitaldetox.model.DatabaseConnection;
import com.example.digitaldetox.model.UserSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScreenTimeDAO implements IScreenTimeDAO {
    private Connection connection;
    public app_tracker tracker;
    public screentime_tracker overall_tracker;
    public ScreenTimeDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
        tracker = new app_tracker();
        overall_tracker = new screentime_tracker();
    }



    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS screentime ("
                    + "accountID INTEGER NOT NULL,"
                    + "appName VARCHAR NOT NULL,"
                    + "screentime_duration BIGINT NOT NULL,"
                    + "overall_screentime BIGINT NOT NULL,"
                    + "FOREIGN KEY (accountID) REFERENCES users(accountId),"
                    + "PRIMARY KEY (accountID, appName)"
                    + ")";
            createTable.execute(query);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void dropRow() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM screentime " +
                    "WHERE appName = 'Miscellaneous'");
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void setColumn() {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE screentime " +
                    "SET overall_screentime AND screentime_duration = 0");
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }


    public void addApp(int accountID) {
        List<App> preSetApps = Arrays.asList(
                new App(accountID, "Facebook", 0),
                new App(accountID, "Instagram", 0),
                new App(accountID, "YouTube", 0),
                new App(accountID, "Reddit", 0),
                new App(accountID, "TikTok", 0)
        );

        try {
            String query = "INSERT INTO screentime (accountID, appName, screentime_duration, overall_screentime) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            for (App app : preSetApps) {
                statement.setInt(1, app.getAccountID());
                statement.setString(2, app.getName());
                statement.setLong(3, app.getTime());
                statement.setLong(4, app.getTime());
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public long getOverallScreentime(int accountID) {
        long overallScreentime = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT overall_screentime " +
                    "FROM screentime " +
                    "WHERE accountID = ?");
            statement.setInt(1, accountID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                overallScreentime = resultSet.getLong("overall_screentime");
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return overallScreentime;
    }

    public void addOverallScreentime(int accountID) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE screentime " +
                    "SET overall_screentime = overall_screentime + ? " +
                    "WHERE accountID = ?");
            statement.setLong(1, tracker.overall_tracker.getTotalTime());
            statement.setInt(2, accountID);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }


    public List<App> getAllAppsScreentime(int accountID) {
        List<App> apps = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT appName, screentime_duration FROM screentime WHERE accountID = ?");
            statement.setInt(1, accountID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String appName = resultSet.getString("appName");
                long screentime = resultSet.getLong("screentime_duration");
                App app = new App(accountID, appName, screentime);
                apps.add(app);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return apps;
    }


    public void addAppScreentime(int accountID) {
        List<App> preSetApps = Arrays.asList(
                new App(accountID, "Facebook", tracker.getFacebookTime()),
                new App(accountID, "Instagram", tracker.getInstagramTime()),
                new App(accountID, "YouTube", tracker.getYoutubeTime()),
                new App(accountID, "Reddit", tracker.getRedditTime()),
                new App(accountID, "TikTok", tracker.getTikTokTime())
        );


        try {
            String query = ("UPDATE screentime " +
                    "SET screentime_duration = screentime_duration + ?" +
                    "WHERE accountID = ? AND appName = ?");
            PreparedStatement statement = connection.prepareStatement(query);

            for (App app : preSetApps) {
                statement.setLong(1, app.getTime());
                statement.setInt(2, app.getAccountID());
                statement.setString(3, app.getName());
                statement.executeUpdate();
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public String[] getAppNames(int accountID) {
        String[] appNames = new String[0];

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT appName FROM screentime WHERE accountID = ?");
            statement.setInt(1, accountID);
            ResultSet resultSet = statement.executeQuery();

            List<String> tempList = new ArrayList<>();

            while (resultSet.next()) {
                String appName = resultSet.getString("appName");
                tempList.add(appName);
            }

            appNames = tempList.toArray(new String[0]);
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return appNames;
    }


    public long[] getAppDurations(int accountID) {
        long[] appDurations = new long[0];

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT screentime_duration FROM screentime WHERE accountID = ?");
            statement.setInt(1, accountID);
            ResultSet resultSet = statement.executeQuery();

            List<Long> tempList = new ArrayList<>();

            while (resultSet.next()) {
                long screentime = resultSet.getLong("screentime_duration");
                tempList.add(screentime);
            }

            appDurations = tempList.stream().mapToLong(Long::longValue).toArray();
        } catch (SQLException ex) {
            System.err.println(ex);
        }

        return appDurations;
    }



}




