package com.example.digitaldetox.model;

public class User {
    private int account_id;
    private String email;
    private String username;
    private String password;


    public User(int account_id, String username, String password, String email) {
        this.account_id = account_id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getaccountId() {
        return account_id;
    }

    public void setaccountId(int id) {
        this.account_id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountDetails() {
        return "Username: " + username + "\n" + "Password: " + password + "Email: " + email + "\n";
    }
}
