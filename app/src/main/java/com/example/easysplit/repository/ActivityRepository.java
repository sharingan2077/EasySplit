package com.example.easysplit.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.easysplit.model.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityRepository {

    private static ActivityRepository instance;
    private ArrayList<Activity> dataSet = new ArrayList<>();

    public static ActivityRepository getInstance()
    {
        if (instance == null)
        {
            instance = new ActivityRepository();
        }
        return instance;
    }
    public MutableLiveData<List<Activity>> getActivities()
    {
        setActivities();
        MutableLiveData<List<Activity>> data = new MutableLiveData<>();
        data.setValue(dataSet);
        return data;
    }

    private void setActivities()
    {
        dataSet.add(new Activity("Misha", "Бананы", "Example", "Сегодня, 18:10"));
        dataSet.add(new Activity("Misha", "Чебуреки", "Example2", "Сегодня, 18:10"));
        dataSet.add(new Activity("Misha", "Чебупели", "Example3", "Сегодня, 18:10"));
        dataSet.add(new Activity("Misha", "Картошка", "Example4", "Сегодня, 18:10"));
        dataSet.add(new Activity("Misha", "Блинчики с мясом", "Example5", "Сегодня, 18:10"));
    }

}
