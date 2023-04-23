package com.example.easysplit.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Group;
import com.example.easysplit.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static UserRepository instance;
    private ArrayList<User> dataSet = new ArrayList<>();

    public static UserRepository getInstance()
    {
        if (instance == null)
        {
            instance = new UserRepository();
        }
        return instance;
    }
    public MutableLiveData<List<User>> getUsers()
    {
        setUsers();
        MutableLiveData<List<User>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setUsers()
    {
        dataSet.add(new User("Maksim"));
    }
}
