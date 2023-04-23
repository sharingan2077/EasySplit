package com.example.easysplit.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public MutableLiveData<List<Group>> getGroups()
    {
        setGroups();
        MutableLiveData<List<Group>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setGroups()
    {
        dataSet.add(new Group("Example", 2, UUID.randomUUID().toString()));
        dataSet.add(new Group("Example2", 3, UUID.randomUUID().toString()));
    }


}
