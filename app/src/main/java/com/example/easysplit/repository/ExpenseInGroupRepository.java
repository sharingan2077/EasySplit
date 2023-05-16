package com.example.easysplit.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.ExpenseInGroup;
import com.example.easysplit.model.Group;

import java.util.ArrayList;
import java.util.List;

public class ExpenseInGroupRepository {

    private static ExpenseInGroupRepository instance;
    private ArrayList<ExpenseInGroup> dataSet = new ArrayList<>();

    public static ExpenseInGroupRepository getInstance()
    {
        if (instance == null)
        {
            instance = new ExpenseInGroupRepository();
        }
        return instance;
    }
    public MutableLiveData<List<ExpenseInGroup>> getExpensesInGroup()
    {
        setExpensesInGroup();
        MutableLiveData<List<ExpenseInGroup>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setExpensesInGroup()
    {
        dataSet.clear();
        dataSet.add(new ExpenseInGroup("Potato", "20.09", "Misha", 1000));
        dataSet.add(new ExpenseInGroup("Banana", "21.09", "You", 2000));
    }

}
