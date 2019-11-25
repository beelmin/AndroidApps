package com.example.mychatapp;

public class ListItem {

    private String user;
    private String currentUser;
    private String active;

    public ListItem(String user, String currentUser, String active) {
        this.user = user;
        this.currentUser = currentUser;
        this.active = active;
    }


    public String getUser() {
        return user;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getActive() {
        return active;
    }
}
