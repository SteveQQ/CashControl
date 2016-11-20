package com.steveq.cashcontrol.model;

public class User {

    private String mUsername;
    private String mPassword;

    public User(String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }
}
