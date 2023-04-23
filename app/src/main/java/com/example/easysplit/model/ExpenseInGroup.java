package com.example.easysplit.model;

public class ExpenseInGroup {

    private String expenseName;
    private String date;
    private String userName;
    private int expenseSum;

    public ExpenseInGroup(String expenseName, String date, String userName, int expenseSum) {
        this.expenseName = expenseName;
        this.date = date;
        this.userName = userName;
        this.expenseSum = expenseSum;
    }

    public ExpenseInGroup()
    {

    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public int getExpenseSum() {
        return expenseSum;
    }
}
