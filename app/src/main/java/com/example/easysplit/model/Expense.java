package com.example.easysplit.model;

import java.util.HashMap;
import java.util.List;

public class Expense {


    private String id;
    private String expenseName;
    private String expenseDate;
    private String expenseGroup;
    private Long expenseSum;
    private String expenseOwner;

    private HashMap<String, Long> usersWaste;

    public void setExpenseGroup(String expenseGroup) {
        this.expenseGroup = expenseGroup;
    }

    public String getExpenseGroup() {
        return expenseGroup;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setExpenseSum(Long expenseSum) {
        this.expenseSum = expenseSum;
    }

    public void setExpenseOwner(String expenseOwner) {
        this.expenseOwner = expenseOwner;
    }


    public String getId() {
        return id;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public Long getExpenseSum() {
        return expenseSum;
    }

    public String getExpenseOwner() {
        return expenseOwner;
    }

    public void setUsersWaste(HashMap<String, Long> usersWaste) {
        this.usersWaste = usersWaste;
    }

    public HashMap<String, Long> getUsersWaste() {
        return usersWaste;
    }

    public Expense(String expenseName, String expenseDate, Long expenseSum, String expenseGroup, String expenseOwner, HashMap<String, Long> usersWaste) {
        this.expenseName = expenseName;
        this.expenseDate = expenseDate;
        this.expenseSum = expenseSum;
        this.expenseGroup = expenseGroup;
        this.expenseOwner = expenseOwner;
        this.usersWaste = usersWaste;
    }



}
