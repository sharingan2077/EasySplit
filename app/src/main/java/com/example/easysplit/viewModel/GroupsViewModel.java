package com.example.easysplit.viewModel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.Group;
import com.example.easysplit.repositories.GroupRepository;

import java.util.List;

public class GroupsViewModel extends ViewModel {
    private static final String TAG = "GroupsViewModel";

    private MutableLiveData<List<Group>> groups;
    private GroupRepository mRepo;


    public void init()
    {
        if (groups != null)
        {
            return;
        }
        mRepo = GroupRepository.getInstance();
        groups = mRepo.getNicePlaces();
    }

    public void addNewValue(final Group group)
    {
        Log.d(TAG, "Adding new Element");
        List<Group> currentGroups = groups.getValue();
        currentGroups.add(group);
        groups.postValue(currentGroups);
    }

    public LiveData<List<Group>> getGroups()
    {
        return groups;
    }

}
