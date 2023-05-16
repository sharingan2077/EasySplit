package com.example.easysplit.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.DebtInGroup;

import java.util.ArrayList;
import java.util.List;

public class DebtInGroupRepository {

    private static DebtInGroupRepository instance;
    private ArrayList<DebtInGroup> dataSet = new ArrayList<>();

    public static DebtInGroupRepository getInstance()
    {
        if (instance == null)
        {
            instance = new DebtInGroupRepository();
        }
        return instance;
    }
    public MutableLiveData<List<DebtInGroup>> getDebtsInGroup()
    {
        setDebtsInGroup();
        MutableLiveData<List<DebtInGroup>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setDebtsInGroup()
    {
        dataSet.clear();
        dataSet.add(new DebtInGroup("Misha", 2000));
        dataSet.add(new DebtInGroup("You", 3000));
        dataSet.add(new DebtInGroup("Misha", 4000));
        dataSet.add(new DebtInGroup("Misha", 5000));
    }

}
