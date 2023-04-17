package com.example.easysplit.model;

public class Group {

    private String groupName;
    //private String imageUrl;

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
