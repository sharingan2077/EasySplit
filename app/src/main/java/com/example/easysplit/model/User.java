package com.example.easysplit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {

    private String userName;

    private String id;

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserImage() {
        return userImage;
    }

    private String UID;
    private long sumOwn;
    private Boolean youOwn;
    private String userImage;
    private List<String> userGroups = new ArrayList<>();
    private List<String> userFriends = new ArrayList<>();

    public void setSumOwn(long sumOwn) {
        this.sumOwn = sumOwn;
    }

    public void setYouOwn(Boolean youOwn) {
        this.youOwn = youOwn;
    }

    public long getSumOwn() {
        return sumOwn;
    }

    public Boolean getYouOwn() {
        return youOwn;
    }

    public User(String userName, String id, String UID, long sumOwn, Boolean youOwn) {
        this.userName = userName;
        this.id = id;
        this.UID = UID;
        this.sumOwn = sumOwn;
        this.youOwn = youOwn;
    }

    public User(String userName, String id, String UID) {
        this.userName = userName;
        this.id = id;
        this.UID = UID;
        this.sumOwn = 0;
    }

    public String getUID() {
        return UID;
    }

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
    public User(String userName, String id, String userImage, String UID) {
        this.userName = userName;
        this.id = id;
        this.userImage = userImage;
        this.UID = UID;
    }
}
