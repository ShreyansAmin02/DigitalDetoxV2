package com.example.digitaldetox.model;

public class UserAuthentication {
    private IUserDAO userAccountDAO;
    public UserAuthentication(IUserDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    //signup authentication
    public boolean isUsernameTaken(String username) {
        if (userAccountDAO.getUserByUsername(username) == null) {
            return false;
        }
        else {
            return true;
        }

    }

    //need to redo lol
    public boolean isEmailValid(String email) {
        if (email.contains("@")) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isPasswordValid(String password) {
        char currentChar;
        int uppercase = 0;
        int lowercase = 0;
        int digit = 0;
        int passwordLength = password.length();

        if (password.length() < 8) {
            return false;
        }

        else {
            for (int i = 0; i < passwordLength; i++) {
                currentChar = password.charAt(i);
                if (Character.isDigit(currentChar)){
                    digit++;
                }
                else if (Character.isUpperCase(currentChar)) {
                    uppercase++;
                }
                else {
                    lowercase++;
                }
            }
            if ((uppercase > 0) && (lowercase > 0) && (digit > 0)) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    public boolean doesPasswordMatchConfirmPassword(String password, String confirmPassword) {
        if (password.equals(confirmPassword)) {
            return true;
        }
        else {
            return false;
        }
    }


    //login authentication

    public boolean doesPasswordMatchUsername(String username, String password) {

        if (userAccountDAO.getUserByUsername(username) == null) {
            return false;
        }
        else {
            User user = userAccountDAO.getUserByUsername(username);
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;

    }




}
