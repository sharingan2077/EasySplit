package com.example.easysplit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private String userName;

    private String id;

    private String UID;

    public User(String userName, String id, String UID) {
        this.userName = userName;
        this.id = id;
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    private List<String> userGroups = new ArrayList<>();
    private List<String> userFriends = new ArrayList<>();

//    private HashMap<String, String> userGroups;
//    private HashMap<String, String> userFriends;

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public User(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

//    public HashMap<String, String> getUserGroups() {
//        return userGroups;
//    }
//
//    public HashMap<String, String> getUserFriends() {
//        return userFriends;
//    }
//
//    public User(String userName, String id, HashMap<String, String> userGroups, HashMap<String, String> userFriends) {
//        this.userName = userName;
//        this.id = id;
//        this.userGroups = userGroups;
//        this.userFriends = userFriends;
//    }

    public User(String userName, String id) {
        this.userName = userName;
        this.id = id;
    }
}
