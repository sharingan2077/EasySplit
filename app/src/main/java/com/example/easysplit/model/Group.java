package com.example.easysplit.model;

public class Group {

    private String groupName;
    private String id;

    private int countMember;

    private String groupType;
    private String groupImage;

    public String getGroupType() {
        return groupType;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public Group(String groupName, int countMember, String groupType, String groupImage) {
        this.groupName = groupName;
        this.countMember = countMember;
        this.groupType = groupType;
        this.groupImage = groupImage;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public Group(String groupName, int countMember, String id) {
        this.groupName = groupName;
        this.id = id;
        this.countMember = countMember;
    }


    public Group(String groupName, int countMember) {
        this.groupName = groupName;
        this.countMember = countMember;
    }

    public Group()
    {

    }

    public String getGroupName() {
        return groupName;
    }

    public int getCountMember() {
        return countMember;
    }

    public String getId() {
        return id;
    }
}
