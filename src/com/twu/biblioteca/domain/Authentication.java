package com.twu.biblioteca.domain;

import java.util.ArrayList;

public class Authentication {
    private ArrayList<User> users;
    private User loggedUser;
    private boolean loggedIn;
    private String inputUsername;
    private String inputPassword;

    public Authentication(ArrayList<User> users) {
        this.users = users;
        this.loggedIn = false;
    }

    public void login(String username, String password) {
        inputUsername = username;
        inputPassword = password;

        for(User user : this.users) {
            if(user.getEmail().equals(inputUsername) && user.getPassword().equals(inputPassword)) {
                loggedUser = user;
                loggedIn = true;
            }
        }
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    ArrayList<User> getUsers() {
        return users;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
