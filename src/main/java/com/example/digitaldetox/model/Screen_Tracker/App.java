package com.example.digitaldetox.model.Screen_Tracker;

public class App {

    private String name;
    private long app_time;
    private int accountID;

    public App(int accountID, String name, long app_time) {
        this.name = name;
        this.app_time = app_time;
        this.accountID = accountID;
    }
    public int getAccountID() {
        return accountID;
    }

    public String getName() {
        return name;
    }

    public long getTime() { return app_time; }

    public void setName(String name) {
        this.name = name;
    }

}