package com.example.digitaldetox.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection instance = null;

    private DatabaseConnection() {
        String url = "jdbc:sqlite:testDatabase.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            new DatabaseConnection();
        }
        return instance;
    }

}
