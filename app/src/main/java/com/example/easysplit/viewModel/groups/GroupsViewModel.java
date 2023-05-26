package com.example.easysplit.viewModel.groups;



import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.easysplit.model.Group;
import com.example.easysplit.repository.groups.GroupRepository;
import com.example.easysplit.view.listeners.CompleteListener;

import java.util.List;

public class GroupsViewModel extends ViewModel {

    private MutableLiveData<List<Group>> groups;

    private GroupRepository mRepo;

    public void init(CompleteListener listener)
    {
        if (groups != null)
        {
            groups = mRepo.getGroups(listener);
            return;
        }
        mRepo = GroupRepository.getInstance();
        mRepo.clearExpensesInDataBase();
        groups = mRepo.getGroups(listener);
    }

    public void addNewValue(final Group group)
    {
        mRepo.addGroup(group);
        groups = mRepo.getGroups();
    }

    public LiveData<List<Group>> getGroups()
    {
        return groups;
    }


}
