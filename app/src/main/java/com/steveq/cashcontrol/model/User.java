package com.steveq.cashcontrol.model;

public class User {

    private int mId;
    private String mUsername;
    private String mPassword;

    public User(int id, String username, String password) {
        mId = id;
        mUsername = username;
        mPassword = password;
    }

    public int getId() {
        return mId;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }
}
