package com.example.easysplit.model;

public class DebtInGroup {

    private String user;
    private long sum;

    private Boolean youOwn;

    public String getUser() {
        return user;
    }

    public void setYouOwn(Boolean youOwn) {
        this.youOwn = youOwn;
    }

    public Boolean getYouOwn() {
        return youOwn;
    }

    public DebtInGroup(String user, long sum, Boolean youOwn) {
        this.user = user;
        this.sum = sum;
        this.youOwn = youOwn;
    }

    public long getSum() {
        return sum;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setSum(long sum) {
        this.sum = sum;
    }

    public DebtInGroup()
    {

    }



}
