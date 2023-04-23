package com.example.easysplit.model;

public class Group {

    private String groupName;
    //private String imageUrl;
    private String id;

    public String getId() {
        return id;
    }

    public Group(String groupName, int countMember, String id) {
        this.groupName = groupName;
        this.id = id;
        this.countMember = countMember;
    }

    private int countMember;

    public Group(String groupName, int countMember) {
        this.groupName = groupName;
        //this.imageUrl = imageUrl;
        this.countMember = countMember;
    }

    public Group()
    {

    }

    public String getGroupName() {
        return groupName;
    }

//    public String getImageUrl() {
//        return imageUrl;
//    }

    public int getCountMember() {
        return countMember;
    }
}
