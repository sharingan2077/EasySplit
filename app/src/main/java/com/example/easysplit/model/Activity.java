package com.example.easysplit.model;

public class Activity {

    private String userName;
    private String nameOfExpense;
    private String nameOfGroup;
    private String date;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setNameOfExpense(String nameOfExpense) {
        this.nameOfExpense = nameOfExpense;
    }

    public void setNameOfGroup(String nameOfGroup) {
        this.nameOfGroup = nameOfGroup;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public String getNameOfExpense() {
        return nameOfExpense;
    }

    public String getNameOfGroup() {
        return nameOfGroup;
    }

    public String getDate() {
        return date;
    }

    public Activity(String userName, String nameOfExpense, String nameOfGroup, String date) {
        this.userName = userName;
        this.nameOfExpense = nameOfExpense;
        this.nameOfGroup = nameOfGroup;
        this.date = date;
    }
    public Activity() {
    }
}
