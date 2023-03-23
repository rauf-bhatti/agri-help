package com.example.agri_help.models;

public class User {
    private String mUsername;
    private String mFullname;

    public User (String Username, String Fullname) {
        this.mFullname = Fullname;
        this.mUsername = Username;
    }

    public String GetUsername () {
        return mUsername;
    }

    public String GetFullname () {
        return mFullname;
    }

    public boolean AddNewUser () {
        return true;
    }
}
