package com.example.easysplit.model;

public class ExpenseInGroup {

    private String expenseName;
    private String date;
    private String userName;
    private long expenseSum;

    private Boolean yourExpense;
    private long ownSum;

    public ExpenseInGroup(String expenseName, String date, String userName, long expenseSum, Boolean yourExpense, long ownSum) {
        this.expenseName = expenseName;
        this.date = date;
        this.userName = userName;
        this.expenseSum = expenseSum;
        this.yourExpense = yourExpense;
        this.ownSum = ownSum;
    }

    public ExpenseInGroup(String expenseName, String date, String userName, long expenseSum) {
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

    public long getExpenseSum() {
        return expenseSum;
    }
    public Boolean getYourExpense() {
        return yourExpense;
    }

    public long getOwnSum() {
        return ownSum;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setExpenseSum(long expenseSum) {
        this.expenseSum = expenseSum;
    }

    public void setYourExpense(Boolean yourExpense) {
        this.yourExpense = yourExpense;
    }

    public void setOwnSum(long ownSum) {
        this.ownSum = ownSum;
    }
}
