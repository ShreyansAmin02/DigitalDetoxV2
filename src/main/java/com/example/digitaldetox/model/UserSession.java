package com.example.digitaldetox.model;

public class UserSession {
    protected User loggedInUser;
    protected UserSession() { }

    private static class UserSessionHolder {
        private final static UserSession instance = new UserSession();
    }
    public static UserSession getInstance() {
        return UserSessionHolder.instance;
    }
    public User getLoggedInUser() {
        return loggedInUser;
    }
    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }
    public void clearSession() {
        this.loggedInUser = null;
    }
}

