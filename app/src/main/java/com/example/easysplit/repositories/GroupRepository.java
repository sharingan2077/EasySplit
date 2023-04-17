package com.example.easysplit.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupRepository {


    private static GroupRepository instance;
    private ArrayList<Group> dataSet = new ArrayList<>();

    public static GroupRepository getInstance()
    {
        if (instance == null)
        {
            instance = new GroupRepository();
        }
        return instance;
    }
    public MutableLiveData<List<Group>> getNicePlaces()
    {
        setNicePlaces();
        MutableLiveData<List<Group>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setNicePlaces()
    {
        dataSet.add(new Group("Example", 2));
        dataSet.add(new Group("Example2", 3));
    }


}
