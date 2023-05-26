package com.example.easysplit.viewModel.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.Activity;
import com.example.easysplit.repository.activity.ActivityRepository;
import com.example.easysplit.view.listeners.CompleteListener;

import java.util.List;

public class ActivityViewModel extends ViewModel {

    private MutableLiveData<List<Activity>> activities;
    private ActivityRepository mRepo;

    public void init(CompleteListener listener)
    {

        if (activities != null)
        {
            activities = mRepo.getActivities(listener);
            return;
        }
        mRepo = ActivityRepository.getInstance();
        activities = mRepo.getActivities(listener);
    }

    public LiveData<List<Activity>> getActivities()
    {
        return activities;
    }


}
