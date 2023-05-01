package com.example.easysplit.viewModel.groups;



import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.Group;
import com.example.easysplit.repository.GroupRepository;
import com.example.easysplit.view.listeners.DataLoadListener;

import java.util.List;

public class GroupsViewModel extends ViewModel {
    private static final String TAG = "GroupsViewModel";

    private MutableLiveData<List<Group>> groups;

    private MutableLiveData<Boolean> dataLoaded;
    private GroupRepository mRepo;

    public void init()
    {
        Log.d(TAG, "init groupsViewModel");

        if (groups != null)
        {
            return;
        }
        mRepo = GroupRepository.getInstance();

        dataLoaded = new MutableLiveData<>();
        dataLoaded.setValue(false);
        groups = mRepo.getGroups(new DataLoadListener() {
            @Override
            public void dataLoaded() {
                Log.d(TAG, String.valueOf(groups.getValue().size()));
                dataLoaded.setValue(true);
            }
        });

    }

    public void addNewValue(final Group group)
    {
        mRepo.addGroup(group);
        groups = mRepo.getGroups(new DataLoadListener() {
            @Override
            public void dataLoaded() {
            }
        });
    }

    public void deleteGroup(String id)
    {
        mRepo.deleteGroup(id);
    }

    public LiveData<Boolean> getDataLoaded() {
        return dataLoaded;
    }

    public LiveData<List<Group>> getGroups()
    {
        return groups;
    }

}
