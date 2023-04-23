package com.example.easysplit.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.Activity;
import com.example.easysplit.model.User;
import com.example.easysplit.repository.ActivityRepository;
import com.example.easysplit.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class ActivityViewModel extends ViewModel {

    private MutableLiveData<List<Activity>> activities;
    private ActivityRepository mRepo;

    public void init()
    {

        if (activities != null)
        {
            return;
        }
        mRepo = ActivityRepository.getInstance();
        activities = mRepo.getActivities();
    }

    public void addNewValue(final Activity activity)
    {
        List<Activity> currentActivities;
        if (activities != null)
        {
            currentActivities = activities.getValue();
        }
        else
        {
            activities = new MutableLiveData<>();
            currentActivities = new ArrayList<>();
        }
        currentActivities.add(activity);
        activities.setValue(currentActivities);
    }

    public LiveData<List<Activity>> getActivities()
    {
        return activities;
    }


}
