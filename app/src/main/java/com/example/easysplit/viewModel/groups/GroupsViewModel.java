package com.example.easysplit.viewModel.groups;



import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.Group;
import com.example.easysplit.repository.GroupRepository;
import com.example.easysplit.view.listeners.DataLoadFirstListener;
import com.example.easysplit.view.listeners.DataLoadListener;

import java.util.List;

public class GroupsViewModel extends ViewModel {
    private static final String TAG = "GroupsViewModel";

    private MutableLiveData<List<Group>> groups;

    private MutableLiveData<Boolean> dataLoaded;
    private GroupRepository mRepo;

    public void init(DataLoadFirstListener listener)
    {
        Log.d(TAG, "init groupsViewModel");

//        if (groups != null)
//        {
//            listener.dataLoaded(false);
//            return;
//        }
        mRepo = GroupRepository.getInstance(listener);
        dataLoaded = new MutableLiveData<>();
        groups = new MutableLiveData<>();
        dataLoaded.setValue(false);
        groups = mRepo.getGroups();
    }

    public void addNewValue(final Group group)
    {
        mRepo.addGroup(group);
        groups = mRepo.getGroups();
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
