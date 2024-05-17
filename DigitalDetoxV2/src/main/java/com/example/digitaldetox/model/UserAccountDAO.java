package com.example.digitaldetox.model;

import javax.sound.midi.SysexMessage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserAccountDAO implements IUserDAO {
    private Connection connection;
    public UserAccountDAO() {
        connection = DatabaseConnection.getInstance();
        createTable();
    }


    public void createTable() {
        try {
            Statement createTable = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "accountId INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL,"
                    + "email VARCHAR NOT NULL"
                    + ")";
            createTable.execute(query);
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }




    @Override
    public void addUser(User user) {
        try {
            String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.executeUpdate();
            // Set the id of the new contact
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setaccountId(generatedKeys.getInt(1));
            }
        } catch(SQLException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void updateUser(User user) {
        try {
            String query = "UPDATE users SET username = ?, password = ?, email = ? WHERE accountId = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getaccountId());
            statement.executeUpdate();
        } catch(SQLException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            String query = "DELETE FROM users WHERE id = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getaccountId());
            statement.executeUpdate();

        } catch(SQLException ex) {
            System.err.println(ex);
        }
    }

    // need to fix

    public User getUserByID(int accountId) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE accountId = ?");
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                User user = new User(username, password, email);
                user.setaccountId(accountId);
                return user;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public User getUserByUsername(String username) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int accountId = resultSet.getInt("accountId");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                User user = new User(accountId, username, password, email);
                return user;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return null;
    }




    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try {
            Statement getAll = connection.createStatement();
            String query = "SELECT * FROM users";
            ResultSet resultSet = getAll.executeQuery(query);
            while (resultSet.next()) {
                // Retrieve data from the result set
                int accountId = resultSet.getInt("accountId");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                // Create a new Contact object
                User user = new User(accountId, username, password, email);
                users.add(user);
            }
        } catch(SQLException ex) {
            System.err.println(ex);
        }
        return users;
    }

    /*
    //signup authentication
    public boolean isUsernameTaken(String username) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return count == 1;
    }




    //login authentication
    public boolean isPasswordCorrect(String username, String password) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
        return count == 1;
    }
*/


}
