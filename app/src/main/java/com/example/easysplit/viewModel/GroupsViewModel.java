package com.example.easysplit.viewModel;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.Group;
import com.example.easysplit.repositories.GroupRepository;

import java.util.ArrayList;
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
        groups = mRepo.getGroups();
    }

    public void addNewValue(final Group group)
    {
        Log.d(TAG, "Adding new Element");
        List<Group> currentGroups;
        if (groups != null)
        {
            currentGroups = groups.getValue();
        }
        else
        {
            groups = new MutableLiveData<>();
            currentGroups = new ArrayList<>();
        }
        currentGroups.add(group);
        groups.setValue(currentGroups);

    }

    public void deleteValue(int id)
    {
        List<Group> currentGroups;
        groups = new MutableLiveData<>();
        currentGroups = new ArrayList<>();
        currentGroups.remove(id);
        groups.setValue(currentGroups);
    }

    public LiveData<List<Group>> getGroups()
    {
        return groups;
    }

}
